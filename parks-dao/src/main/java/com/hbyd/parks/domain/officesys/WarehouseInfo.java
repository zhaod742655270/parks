package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.RecoverableEntity;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * 仓库类型信息
 * Created by Zhao_d on 2017/1/22.
 */
@Entity
@Table(name = "oa_warehouse_info")
@Audited
public class WarehouseInfo extends RecoverableEntity{
    private String name;
    private String note;

    @OneToMany(mappedBy = "warehouse")
    private Set<WarehouseInput> warehouseInputs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<WarehouseInput> getWarehouseInputs() {
        return warehouseInputs;
    }

    public void setWarehouseInputs(Set<WarehouseInput> warehouseInputs) {
        this.warehouseInputs = warehouseInputs;
    }
}
