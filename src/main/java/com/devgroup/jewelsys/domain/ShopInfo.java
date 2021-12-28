package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ShopInfo.
 */
@Entity
@Table(name = "shop_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShopInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "shop_code", nullable = false, unique = true)
    private String shopCode;

    @NotNull
    @Column(name = "name_eng", nullable = false)
    private String nameEng;

    @Column(name = "name_myan")
    private String nameMyan;

    @Column(name = "addr_eng")
    private String addrEng;

    @Column(name = "addr_myan")
    private String addrMyan;

    @Column(name = "phone")
    private String phone;

    @Column(name = "del_flg")
    private String delFlg;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ShopInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getShopCode() {
        return this.shopCode;
    }

    public ShopInfo shopCode(String shopCode) {
        this.shopCode = shopCode;
        return this;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getNameEng() {
        return this.nameEng;
    }

    public ShopInfo nameEng(String nameEng) {
        this.nameEng = nameEng;
        return this;
    }

    public void setNameEng(String nameEng) {
        this.nameEng = nameEng;
    }

    public String getNameMyan() {
        return this.nameMyan;
    }

    public ShopInfo nameMyan(String nameMyan) {
        this.nameMyan = nameMyan;
        return this;
    }

    public void setNameMyan(String nameMyan) {
        this.nameMyan = nameMyan;
    }

    public String getAddrEng() {
        return this.addrEng;
    }

    public ShopInfo addrEng(String addrEng) {
        this.addrEng = addrEng;
        return this;
    }

    public void setAddrEng(String addrEng) {
        this.addrEng = addrEng;
    }

    public String getAddrMyan() {
        return this.addrMyan;
    }

    public ShopInfo addrMyan(String addrMyan) {
        this.addrMyan = addrMyan;
        return this;
    }

    public void setAddrMyan(String addrMyan) {
        this.addrMyan = addrMyan;
    }

    public String getPhone() {
        return this.phone;
    }

    public ShopInfo phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public ShopInfo delFlg(String delFlg) {
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
        if (!(o instanceof ShopInfo)) {
            return false;
        }
        return id != null && id.equals(((ShopInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ShopInfo{" +
            "id=" + getId() +
            ", shopCode='" + getShopCode() + "'" +
            ", nameEng='" + getNameEng() + "'" +
            ", nameMyan='" + getNameMyan() + "'" +
            ", addrEng='" + getAddrEng() + "'" +
            ", addrMyan='" + getAddrMyan() + "'" +
            ", phone='" + getPhone() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
