package com.hbyd.parks.domain.managesys;

// Generated 2014-6-18 13:23:23 by Hibernate Tools 3.4.0.CR1

import com.hbyd.parks.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 按钮
 */
@Entity
@Table(name = "base_res_btn")
public class ResBtn extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "resMenuFK", referencedColumnName = "id")
	private ResMenu resMenu;

    /**
     * 是否可见，有些功能只有开发和部署人员才能看到，即使是客户的管理员也不能看到
     */
    private Boolean isVisible;
	private String btnName;
	private String btnIconUrl;
	private Integer btnOrder;
	private String btnScript;
	private String btnUrl;

	public ResBtn() {
	}

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public ResMenu getResMenu() {
		return this.resMenu;
	}

	public void setResMenu(ResMenu resMenu) {
		this.resMenu = resMenu;
	}

	public String getBtnName() {
		return this.btnName;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnIconUrl() {
		return this.btnIconUrl;
	}

	public void setBtnIconUrl(String btnIconUrl) {
		this.btnIconUrl = btnIconUrl;
	}

    public Integer getBtnOrder() {
        return btnOrder;
    }

    public void setBtnOrder(Integer btnOrder) {
        this.btnOrder = btnOrder;
    }

    public String getBtnScript() {
		return this.btnScript;
	}

	public void setBtnScript(String btnScript) {
		this.btnScript = btnScript;
	}

	public String getBtnUrl() {
		return btnUrl;
	}

	public void setBtnUrl(String btnUrl) {
		this.btnUrl = btnUrl;
	}
}
