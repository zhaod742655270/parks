package com.hbyd.parks.managesys.wsImpl;

import com.google.gson.Gson;
import com.hbyd.parks.dto.managesys.DepartmentDTO;
import com.hbyd.parks.ws.managesys.DeptWS;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring/managesys/applicationContext_CXF_Client.xml"})
public class DeptWSImplTest {
	
	@Resource
	private DeptWS deptWS;
	
	private Gson gson;
	private DepartmentDTO d;

	@Before
	public void init(){
		gson = new Gson();//默认适配
		
//		插入初始化数据
		d = new DepartmentDTO();
		d.setDeptName("财务部");
		d.setDeptDesc("管理财务");
		d.setAbbrName("财务部缩写");
		
		d = deptWS.save(d);
		
//		插入测试数据
		for(int i = 0; i < 3; i++){
			DepartmentDTO dept = new DepartmentDTO();
			dept.setDeptName("dept_" + i);
			dept.setDeptDesc("dept_desc_" + i);
			dept.setAbbrName("dept_abbr_name_" + i);
			dept.setParentId(d.getId());
			deptWS.save(dept);
		}

		d = deptWS.getByID(d.getId());
		System.out.println(gson.toJson(d));
	}
	
	@Test
	public void testCrud(){
//		创建综合部门（顶级部门）
		DepartmentDTO generalDept = new DepartmentDTO();
		generalDept.setDeptName("综合部");
		generalDept.setDeptDesc("管理琐碎的事情");
		generalDept = deptWS.save(generalDept);
		
//		创建后勤部门（子部门）
		DepartmentDTO supportDept = new DepartmentDTO();
			supportDept.setDeptName("后勤部");
			supportDept.setDeptDesc("管理后勤事务");
			supportDept.setParentId(generalDept.getId());
			supportDept = deptWS.save(supportDept);
			
//		创建环卫部门（子部门）
		DepartmentDTO envDept = new DepartmentDTO();
			envDept.setDeptName("环卫部");
			envDept.setDeptDesc("管理环卫事务");
			envDept.setParentId(generalDept.getId());
			envDept = deptWS.save(envDept);
		
//		修改综合部
		generalDept.setDeptName("General Department");
		generalDept.setDeptDesc("manage trivial things");
		deptWS.update(generalDept);

//		断言综合部修改成功
		Assert.assertEquals("General Department",deptWS.getByID(generalDept.getId()).getDeptName());
		
//		删除环卫部门
		deptWS.delByID(envDept.getId());
//		删除综合部门
		deptWS.delByID(generalDept.getId());
		
//		断言综合部门已经删除
		Assert.assertNull(deptWS.getByID(generalDept.getId()));
		Assert.assertNull(deptWS.getByID(envDept.getId()));
	}
	
	@Test//OK
	public void testFindDepts(){
		List<DepartmentDTO> deptList = deptWS.findAll();
		Assert.assertTrue(deptList.size() > 0);
		
		for(DepartmentDTO d : deptList){
			System.out.println(gson.toJson(d));
			System.out.println("--------------");
		}
	}

    @Test
    public void testDelFake(){
        String id = "62e0f97a-8208-47e9-9974-b1658e94ff66";
        deptWS.delFake(id);
    }

    @Test
    public void testFindAllValid(){
        List<DepartmentDTO> list = deptWS.findAllValid();

        if(list == null){
            System.out.println("未查询到匹配结果");
        }else {
//            for (DepartmentDTO dto : list) {
//                System.out.println(gson.toJson(dto));
//            }
            System.out.printf("查询到%d个匹配记录%n", list.size());
        }
    }
}
