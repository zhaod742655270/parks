package com.hbyd.parks.ws.managesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.dto.managesys.PriviledgeDTO;
import com.hbyd.parks.dto.managesys.ResAppDTO;
import com.hbyd.parks.dto.managesys.ResBtnDTO;
import com.hbyd.parks.dto.managesys.ResMenuDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.Collection;
import java.util.List;

/**
 * Created by allbutone on 14-7-14.
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({PriviledgeDTO.class})//将服务所用的传输类添加到服务所用的 JAXB 环境中
public interface PriviledgeWS extends BaseWS<PriviledgeDTO>{

    /**查询当前用户可以有权操作的子系统页面
     *
     * @param userId    user id
     * @return 子系统集合，如果没有权限，返回长度为 0 的集合
     */
    Collection<ResAppDTO> getApps(@WebParam(name="userId") String userId);

    /**查询用户在子系统下的菜单权限
     * @param userId user id
     * @param appId app id
     * @return 菜单集合，如果没有权限，返回长度为 0 的集合
     */
    Collection<ResMenuDTO> getMenus(@WebParam(name = "userId") String userId, @WebParam(name = "appId") String appId);


    /**查询用户在菜单下的按钮权限
     * @param userId user id
     * @param menuId menu id
     * @return 按钮集合，如果没有权限，返回长度为 0 的集合
     */
    Collection<ResBtnDTO> getButtons(@WebParam(name = "userId") String userId, @WebParam(name = "menuId") String menuId);

    /** 检查用户是否合法
     * @param userName 用户名
     * @param pwd 密码
     * @return 如果合法，返回 true, 否则 false;
     */
    boolean checkUser(@WebParam(name = "userName") String userName, @WebParam(name = "pwd") String pwd);


    /**
     * 给主体授权
     *
     * @param priOwnerId      主体ID
     * @param priOwnerType    主体类型
     * @param priResId        资源ID
     * @param priResType      资源类型
     * @param url             url地址
     * @see com.hbyd.parks.common.constant.TypeConstant
     * @return Priviledge 实体
     */
    public PriviledgeDTO authorize(
            @WebParam(name = "priOwnerId") String priOwnerId,
            @WebParam(name = "priOwnerType") String priOwnerType,
            @WebParam(name = "priResId") String priResId,
            @WebParam(name = "priResType") String priResType,
            @WebParam(name = "url") String url);


    /**获取角色在某个子系统下的权限
     *
     * @param roleId    角色ID
     * @param appId     子系统ID
     * @return 权限列表
     */
    List<PriviledgeDTO> getResPriDTOs(String roleId, String appId);

    /**更新角色在子系统下的权限
     * @param roleId 角色ID
     * @param appId  子系统ID
     * @param pris   权限列表，如果没有任何权限，传递 null 即可
     */
    void updateBatch(String roleId, String appId, List<PriviledgeDTO> pris);


    /**
     *
     * @param userId 用户ID
     * @param url    查询的URL
     * @return  如果用户有权限返回true，否则返回false
     */
    boolean validatePriviledge(String userId,String url);

}
