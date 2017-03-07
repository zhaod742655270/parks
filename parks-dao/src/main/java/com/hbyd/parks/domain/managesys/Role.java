package com.hbyd.parks.domain.managesys;

// Generated 2014-6-18 13:23:23 by Hibernate Tools 3.4.0.CR1

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * 目前的角色不支持继承
 */
@Entity
@Table(name = "base_role")
public class Role extends BaseEntity {
	private String roleName;
	private String roleDesc;

	@ManyToMany(mappedBy = "roles")
	private List<User> users;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
}
