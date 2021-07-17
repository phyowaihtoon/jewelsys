package com.devgroup.jewelsys.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A GoldItem.
 */
@Entity
@Table(name = "gold_item")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GoldItem extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "del_flg")
    private String delFlg;

    @ManyToOne
    @JsonIgnoreProperties(value = { "goldType" }, allowSetters = true)
    private GoldItemGroup goldItemGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GoldItem id(Long id) {
        this.id = id;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public GoldItem code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public GoldItem name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public GoldItem delFlg(String delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public GoldItemGroup getGoldItemGroup() {
        return this.goldItemGroup;
    }

    public GoldItem goldItemGroup(GoldItemGroup goldItemGroup) {
        this.setGoldItemGroup(goldItemGroup);
        return this;
    }

    public void setGoldItemGroup(GoldItemGroup goldItemGroup) {
        this.goldItemGroup = goldItemGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GoldItem)) {
            return false;
        }
        return id != null && id.equals(((GoldItem) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GoldItem{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
