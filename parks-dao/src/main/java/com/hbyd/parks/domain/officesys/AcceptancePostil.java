package com.hbyd.parks.domain.officesys;

import com.hbyd.parks.common.base.BaseEntity;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;

/**
 * Created by Len on 2016/3/2.
 */
@Entity
@Table(name = "oa_procuremen_acceptance_postil")
@Audited
public class AcceptancePostil extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentFK")
    @NotAudited
    private Acceptance acceptance;

    private String brandPostil;

    private String specificationPostil;

    public Acceptance getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Acceptance acceptance) {
        this.acceptance = acceptance;
    }

    public String getBrandPostil() {
        return brandPostil;
    }

    public void setBrandPostil(String brandPostil) {
        this.brandPostil = brandPostil;
    }

    public String getSpecificationPostil() {
        return specificationPostil;
    }

    public void setSpecificationPostil(String specificationPostil) {
        this.specificationPostil = specificationPostil;
    }
}
