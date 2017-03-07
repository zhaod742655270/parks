package com.hbyd.parks.domain.managesys;

// Generated 2014-6-18 13:23:23 by Hibernate Tools 3.4.0.CR1

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 菜单
 */
@Entity
@Table(name = "base_res_menu")
public class ResMenu extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "parentFK", referencedColumnName = "id")
	private ResMenu parent;

    @OneToMany(mappedBy = "parent")
	private Set<ResMenu> childMenus = new HashSet<ResMenu>(0);

    @ManyToOne
    @JoinColumn(name = "resAppFK", referencedColumnName = "id")
	private ResApp resApp;

    @OneToMany(mappedBy = "resMenu")
	private Set<ResBtn> resBtns = new HashSet<ResBtn>(0);

    /**
     * 是否可见，有些功能只有开发和部署人员才能看到，即使是客户的管理员也不能看到
     */
    private Boolean isVisible;
	private Integer menuOrder;
	private String menuName;
	private String menuPageUrl;
	private String menuIconUrl;

	public ResMenu() {
	}

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public ResMenu getParent() {
		return parent;
	}

	public void setParent(ResMenu parent) {
		this.parent = parent;
	}

	public ResApp getResApp() {
		return resApp;
	}

	public void setResApp(ResApp resApp) {
		this.resApp = resApp;
	}

	public Integer getMenuOrder() {
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuPageUrl() {
		return menuPageUrl;
	}

	public void setMenuPageUrl(String menuPageUrl) {
		this.menuPageUrl = menuPageUrl;
	}

	public String getMenuIconUrl() {
		return menuIconUrl;
	}

	public void setMenuIconUrl(String menuIconUrl) {
		this.menuIconUrl = menuIconUrl;
	}

	public Set<ResBtn> getResBtns() {
		return resBtns;
	}

	public void setResBtns(Set<ResBtn> resBtns) {
		this.resBtns = resBtns;
	}

	public Set<ResMenu> getChildMenus() {
		return childMenus;
	}

	public void setChildMenus(Set<ResMenu> childMenus) {
		this.childMenus = childMenus;
	}
	
}
