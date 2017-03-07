package com.hbyd.parks.dto.managesys;

import com.hbyd.parks.common.base.BaseDTO;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

// Generated 2014-6-26 13:14:36 by Hibernate Tools 3.4.0.CR1

/**
 * 起始客户端除了基本的用户信息外，还需要 roleIds, roleNames, deptId, deptName, 即：对已有角色和部门的关联
 * 现在设计的不太好，客户端还得使用 datagrid 的 loadFilter 滤出数据中的这些关联属性
 */
@XmlRootElement(name="user")
public class UserDTO extends BaseDTO {
	private String userName;
	private String password;
	private String nickname;

    /**
     * 所属部门（一个）
     */
    private DepartmentDTO department;

    /**
     * 拥有的角色（多个）
     */
    private Set<RoleDTO> roles = new HashSet<>();


	public UserDTO() {
	}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }
}
