package com.hbyd.parks.managesys.wsImpl;

import com.hbyd.parks.common.constant.TypeConstant;
import com.hbyd.parks.common.model.PageBeanEasyUI;
import com.hbyd.parks.common.model.QueryBeanEasyUI;
import com.hbyd.parks.dto.managesys.PriviledgeDTO;
import com.hbyd.parks.dto.managesys.RoleDTO;
import com.hbyd.parks.dto.managesys.UserDTO;
import com.hbyd.parks.ws.managesys.PriviledgeWS;
import com.hbyd.parks.ws.managesys.RoleWS;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/managesys/applicationContext_CXF_Client.xml"})
public class RoleWSImplTest {
	
	@Resource
	private RoleWS roleWS;

	@Resource
	private PriviledgeWS priviledgeWS;

	private int rowCntBefore;
	private int rowCntAfter;

	@Before
	public void init(){
		rowCntBefore = roleWS.getRowCount();
	}

	@After
	public void cleanUp(){
		rowCntAfter = roleWS.getRowCount();
		System.out.println("rows affected：" + (rowCntAfter - rowCntBefore));
	}

	@Test//ok
	public void testCrud(){
		RoleDTO role = new RoleDTO();
		role.setRoleName("管理员");
		role.setRoleDesc("管理所有事务");
		role = roleWS.save(role);

//		断言保存
		String id = role.getId();
		Assert.assertNotNull(roleWS.getByID(id));
		
		role.setRoleName("admin");
		role.setRoleDesc("manage everything");
		roleWS.update(role);

//		断言更新
		Assert.assertEquals("admin", roleWS.getByID(id).getRoleName());
		Assert.assertEquals("manage everything", roleWS.getByID(id).getRoleDesc());

//		添加角色关联的权限
		PriviledgeDTO pri = new PriviledgeDTO();
		pri.setPriOwnerId(id);
		pri.setPriOwnerType(TypeConstant.ROLE);
		pri.setPriResId("测试用的资源ID");
		pri.setPriResType(TypeConstant.SYS_APP);

		priviledgeWS.save(pri);

//		添加角色和用户的关联
		UserDTO user = new UserDTO();

		roleWS.delByID(role.getId());

//		断言：角色关联的权限已删除

//		断言：角色和用户的关联已解除

//		断言：角色本身已删除
		Assert.assertNull(roleWS.getByID(id));
	}
	
	@Test//OK
	public void testFindRoles(){
		List<RoleDTO> roleList = roleWS.findAll();
		Assert.assertTrue(roleList.size() > 0);
	}
	
	@Test//OK
	public void testGetPageBean(){
		QueryBeanEasyUI queryBean = new QueryBeanEasyUI();
		queryBean.setPage(2);
		queryBean.setRows(3);
		queryBean.setSorts(new String[]{"roleName"});
		queryBean.setOrders(new String[]{"asc"});
		
		String hql_where = "where roleName like ? and roleDesc like ?";
		List params = new ArrayList();
		params.add("tom_%");
        params.add("tom_desc%");
		
		PageBeanEasyUI pageBean = roleWS.getPageBean(queryBean, hql_where, params.toArray());
		
		Assert.assertEquals(3, pageBean.getRows().size());
		
		for(Object o : pageBean.getRows()){
			RoleDTO role = (RoleDTO)o;
			System.out.println(role.getRoleName());
			System.out.println(role.getRoleDesc());
		}
        System.out.println(pageBean.getTotal());
        System.out.println(pageBean.getQueryBean().getSorts());
    }
}
