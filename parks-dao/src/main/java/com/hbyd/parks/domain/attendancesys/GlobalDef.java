package com.hbyd.parks.domain.attendancesys;


import com.hbyd.parks.common.base.BaseEntity;
import com.hbyd.parks.common.enums.AssertType;
import com.hbyd.parks.common.enums.RestDay;

import javax.persistence.*;
import java.util.List;

/**
 * 默认定义，如：全局、部门、个人的默认班次定义
 */
@Entity
@Table(name = "attend_global_def")
public class GlobalDef extends BaseEntity {
    /**
     * 应上班次(默认班次)
     */
    @ManyToOne
    @JoinColumn(name = "shiftFK", referencedColumnName = "id")
    private Shift shift;

    /**
     * 固定休息日，如：星期一，星期四； 需要单独的表来存储这些枚举值
     */
    @ElementCollection(targetClass = RestDay.class)
    @CollectionTable(name = "attend_global_def_rest_days", joinColumns = {
            @JoinColumn(name = "globalDefFK", referencedColumnName = "id")
    })
//该表用于存储集合属性，即：一对多关联表，DefaultDefinition(one) <- RestDay (Many)
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private List<RestDay> restDays;

    /**
     * 无效考勤认定，如：休息，旷工
     */
    @Enumerated(EnumType.STRING)
    private AssertType invalidAssert;

    public List<RestDay> getRestDays() {
        return restDays;
    }

    public void setRestDays(List<RestDay> restDays) {
        this.restDays = restDays;
    }

    public Shift getShift() {
        return shift;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public AssertType getInvalidAssert() {
        return invalidAssert;
    }

    public void setInvalidAssert(AssertType invalidAssert) {
        this.invalidAssert = invalidAssert;
    }
}
