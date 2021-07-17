package com.devgroup.jewelsys.service.dto;

import com.devgroup.jewelsys.domain.enumeration.MenuStatus;
import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.Menu} entity.
 */
public class MenuDTO implements Serializable {

    private Long id;

    @NotNull
    private String menuCode;

    @NotNull
    private String menuName;

    private String routerLink;

    @NotNull
    private MenuStatus status;

    private MenuGroupDTO menuGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getRouterLink() {
        return routerLink;
    }

    public void setRouterLink(String routerLink) {
        this.routerLink = routerLink;
    }

    public MenuStatus getStatus() {
        return status;
    }

    public void setStatus(MenuStatus status) {
        this.status = status;
    }

    public MenuGroupDTO getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroupDTO menuGroup) {
        this.menuGroup = menuGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuDTO)) {
            return false;
        }

        MenuDTO menuDTO = (MenuDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, menuDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MenuDTO{" +
            "id=" + getId() +
            ", menuCode='" + getMenuCode() + "'" +
            ", menuName='" + getMenuName() + "'" +
            ", routerLink='" + getRouterLink() + "'" +
            ", status='" + getStatus() + "'" +
            ", menuGroup=" + getMenuGroup() +
            "}";
    }
}
