package com.devgroup.jewelsys.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.devgroup.jewelsys.domain.RoleMenuMap} entity.
 */
public class RoleMenuMapDTO implements Serializable {

    private Long id;
    private String roleId;
    private MenuDTO menu;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleMenuMapDTO)) {
            return false;
        }

        RoleMenuMapDTO roleMenuMapDTO = (RoleMenuMapDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, roleMenuMapDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleMenuMapDTO{" +
            "id=" + getId() +
            ", roleId='" + getRoleId() + "'" +
            ", menu='" + getMenu() + "'" +
            "}";
    }
}
