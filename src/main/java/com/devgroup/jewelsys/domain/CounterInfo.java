package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CounterInfo.
 */
@Entity
@Table(name = "counter_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CounterInfo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "counter_no", nullable = false, unique = true)
    private String counterNo;

    @NotNull
    @Column(name = "counter_name", nullable = false)
    private String counterName;

    @Column(name = "del_flg")
    private String delFlg;

    @ManyToOne
    private ShopInfo shopInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CounterInfo id(Long id) {
        this.id = id;
        return this;
    }

    public String getCounterNo() {
        return this.counterNo;
    }

    public CounterInfo counterNo(String counterNo) {
        this.counterNo = counterNo;
        return this;
    }

    public void setCounterNo(String counterNo) {
        this.counterNo = counterNo;
    }

    public String getCounterName() {
        return this.counterName;
    }

    public CounterInfo counterName(String counterName) {
        this.counterName = counterName;
        return this;
    }

    public void setCounterName(String counterName) {
        this.counterName = counterName;
    }

    public String getDelFlg() {
        return this.delFlg;
    }

    public CounterInfo delFlg(String delFlg) {
        this.delFlg = delFlg;
        return this;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public ShopInfo getShopInfo() {
        return this.shopInfo;
    }

    public CounterInfo shopInfo(ShopInfo shopInfo) {
        this.setShopInfo(shopInfo);
        return this;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CounterInfo)) {
            return false;
        }
        return id != null && id.equals(((CounterInfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CounterInfo{" +
            "id=" + getId() +
            ", counterNo='" + getCounterNo() + "'" +
            ", counterName='" + getCounterName() + "'" +
            ", delFlg='" + getDelFlg() + "'" +
            "}";
    }
}
