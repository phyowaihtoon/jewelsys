package com.devgroup.jewelsys.domain;

import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RoleMenuMap.
 */
@Entity
@Table(name = "role_menu_map")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoleMenuMap extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "role_id")
    private String roleId;

    @ManyToOne
    private Menu menu;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleMenuMap id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoleId() {
        return this.roleId;
    }

    public RoleMenuMap roleId(String roleId) {
        this.roleId = roleId;
        return this;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public RoleMenuMap menu(Menu menu) {
        this.setMenu(menu);
        return this;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleMenuMap)) {
            return false;
        }
        return id != null && id.equals(((RoleMenuMap) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleMenuMap{" +
            "id=" + getId() +
            ", roleId='" + getRoleId() + "'" +
            "}";
    }
}
