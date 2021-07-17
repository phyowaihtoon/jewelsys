package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DataCategory.
 */
@Entity
@Table(name = "data_category")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataCategory extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "category_type")
    private String categoryType;

    @Column(name = "category_code")
    private String categoryCode;

    @Column(name = "value")
    private String value;

    @Column(name = "category_desc")
    private String categoryDesc;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DataCategory id(Long id) {
        this.id = id;
        return this;
    }

    public String getCategoryType() {
        return this.categoryType;
    }

    public DataCategory categoryType(String categoryType) {
        this.categoryType = categoryType;
        return this;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryCode() {
        return this.categoryCode;
    }

    public DataCategory categoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
        return this;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getValue() {
        return this.value;
    }

    public DataCategory value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCategoryDesc() {
        return this.categoryDesc;
    }

    public DataCategory categoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
        return this;
    }

    public void setCategoryDesc(String categoryDesc) {
        this.categoryDesc = categoryDesc;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataCategory)) {
            return false;
        }
        return id != null && id.equals(((DataCategory) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DataCategory{" +
            "id=" + getId() +
            ", categoryType='" + getCategoryType() + "'" +
            ", categoryCode='" + getCategoryCode() + "'" +
            ", value='" + getValue() + "'" +
            ", categoryDesc='" + getCategoryDesc() + "'" +
            "}";
    }
}
