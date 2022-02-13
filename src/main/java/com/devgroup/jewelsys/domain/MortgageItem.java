package com.devgroup.jewelsys.domain;

import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A MortgageItem.
 */
@Entity
@Table(name = "mortgage_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MortgageItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "group_code", nullable = false)
    private MortgageItemGroup groupCode;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "item_name", nullable = false)
    private String itemName;

    @Column(name = "del_flg")
    private String delFlg;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MortgageItem id(Long id) {
        this.id = id;
        return this;
    }

    public MortgageItemGroup getGroupCode() {
        return this.groupCode;
    }

    public MortgageItem groupCode(MortgageItemGroup groupCode) {
        this.groupCode = groupCode;
        return this;
    }

    public void setGroupCode(MortgageItemGroup groupCode) {
        this.groupCode = groupCode;
    }

    public String getCode() {
        return this.code;
    }

    public MortgageItem code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getItemName() {
        return this.itemName;
    }

    public MortgageItem itemName(String itemName) {
        this.itemName = itemName;
        return this;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public MortgageItem delFlg(String delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MortgageItem)) {
            return false;
        }
        return id != null && id.equals(((MortgageItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MortgageItem{" +
            "id=" + getId() +
            ", groupCode='" + getGroupCode() + "'" +
            ", code='" + getCode() + "'" +
            ", itemName='" + getItemName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
