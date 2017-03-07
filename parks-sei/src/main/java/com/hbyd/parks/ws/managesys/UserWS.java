package com.hbyd.parks.ws.managesys;

import com.hbyd.parks.common.base.BaseWS;
import com.hbyd.parks.common.base.RecoverableWS;
import com.hbyd.parks.dto.managesys.UserDTO;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.util.List;

/**定义用户的增删改查
 */
@WebService
@BindingType(SOAPBinding.SOAP12HTTP_BINDING)
@XmlSeeAlso({UserDTO.class})
public interface UserWS extends BaseWS<UserDTO>, RecoverableWS {
    UserDTO getByName(@WebParam(name = "name") String name);

    /**更新用户密码
     * @param userID 用户ID
     * @param newPwd 新的密码
     */
    void updatePwd(@WebParam(name = "userID") String userID,
                   @WebParam(name = "newPWD") String newPwd);

    /**
     * 根据部门名称查询用户
     * @param deptName 部门名称
     * @return 该部门下的所有用户
     */
    public List<UserDTO> getUserByDeptName(String deptName);
}
