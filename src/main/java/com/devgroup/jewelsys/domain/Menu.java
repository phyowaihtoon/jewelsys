package com.devgroup.jewelsys.domain;

import com.devgroup.jewelsys.domain.enumeration.MenuStatus;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Menu.
 */
@Entity
@Table(name = "menu")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Menu extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "menu_code", nullable = false, unique = true)
    private String menuCode;

    @NotNull
    @Column(name = "menu_name", nullable = false)
    private String menuName;

    @Column(name = "router_link")
    private String routerLink;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private MenuStatus status;

    @ManyToOne
    private MenuGroup menuGroup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Menu id(Long id) {
        this.id = id;
        return this;
    }

    public String getMenuCode() {
        return this.menuCode;
    }

    public Menu menuCode(String menuCode) {
        this.menuCode = menuCode;
        return this;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return this.menuName;
    }

    public Menu menuName(String menuName) {
        this.menuName = menuName;
        return this;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getRouterLink() {
        return this.routerLink;
    }

    public Menu routerLink(String routerLink) {
        this.routerLink = routerLink;
        return this;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public MenuStatus getStatus() {
        return this.status;
    }

    public Menu status(MenuStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(MenuStatus status) {
        this.status = status;
    }

    public MenuGroup getMenuGroup() {
        return this.menuGroup;
    }

    public Menu menuGroup(MenuGroup menuGroup) {
        this.setMenuGroup(menuGroup);
        return this;
    }

    public void setMenuGroup(MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Menu)) {
            return false;
        }
        return id != null && id.equals(((Menu) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Menu{" +
            "id=" + getId() +
            ", menuCode='" + getMenuCode() + "'" +
            ", menuName='" + getMenuName() + "'" +
            ", routerLink='" + getRouterLink() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
