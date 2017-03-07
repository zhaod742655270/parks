package com.hbyd.parks.domain.managesys;

// Generated 2014-6-18 13:23:23 by Hibernate Tools 3.4.0.CR1

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

/**
 * 子系统
 */
@Entity
@Table(name = "base_res_app")
public class ResApp extends BaseEntity {

    /**
     * 是否可见，有些功能只有开发和部署人员才能看到，即使是客户的管理员也不能看到
     */
    private Boolean isVisible;
	private String appName;
	private String appDesc;
	private Integer appOrder;
	private String appPageUrl;

    @OneToMany(mappedBy = "resApp")
	private Set<ResMenu> resMenus = new HashSet<ResMenu>(0);

	public ResApp() {
	}

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}

	public Integer getAppOrder() {
		return appOrder;
	}

	public void setAppOrder(Integer appOrder) {
		this.appOrder = appOrder;
	}

	public String getAppPageUrl() {
		return appPageUrl;
	}

	public void setAppPageUrl(String appPageUrl) {
		this.appPageUrl = appPageUrl;
	}

	public Set<ResMenu> getResMenus() {
		return resMenus;
	}

	public void setResMenus(Set<ResMenu> resMenus) {
		this.resMenus = resMenus;
	}


}
