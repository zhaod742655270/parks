package com.hbyd.parks.domain.attendancesys;

import com.hbyd.parks.common.base.BaseEntity;
import com.hbyd.parks.common.constant.Unit;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

/**
 * 规律班次，规律班内中的班次绑定可能很多，这里不做关联
 */
@Entity
@Table(name = "attend_regular_shift")
public class RegularShift extends BaseEntity{
    /**
     * 规律班次的名称
     */
    private String name;

    /**
     * 单位数，之前叫做"周期数"
     */
    private int unitCnt;

    /**
     * 单位
     */
    @Enumerated(EnumType.STRING)
    private Unit unit;

    @OneToMany(mappedBy = "regularShift", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)//集合属性为懒加载，加载规律班次列表只需普通属性即可，使用 SUBSELECT 在查询规律班次列表时，只需查询 attend_regular_shift, 无需连接查询, 速度更快。
    private List<ShiftBinding> bindings;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnitCnt() {
        return unitCnt;
    }

    public void setUnitCnt(int unitCnt) {
        this.unitCnt = unitCnt;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public List<ShiftBinding> getBindings() {
        return bindings;
    }

    public void setBindings(List<ShiftBinding> bindings) {
        this.bindings = bindings;
    }
}
