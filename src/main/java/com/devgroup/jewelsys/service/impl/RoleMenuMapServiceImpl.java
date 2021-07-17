package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.Menu;
import com.devgroup.jewelsys.domain.MenuGroup;
import com.devgroup.jewelsys.domain.RoleMenuMap;
import com.devgroup.jewelsys.repository.RoleMenuMapRepository;
import com.devgroup.jewelsys.service.RoleMenuMapService;
import com.devgroup.jewelsys.service.dto.MenuAccessDTO;
import com.devgroup.jewelsys.service.dto.MenuDTO;
import com.devgroup.jewelsys.service.dto.MenuGroupSorting;
import com.devgroup.jewelsys.service.dto.RoleMenuMapDTO;
import com.devgroup.jewelsys.service.mapper.RoleMenuMapMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoleMenuMap}.
 */
@Service
@Transactional
public class RoleMenuMapServiceImpl implements RoleMenuMapService {

    private final Logger log = LoggerFactory.getLogger(RoleMenuMapServiceImpl.class);

    private final RoleMenuMapRepository roleMenuMapRepository;

    private final RoleMenuMapMapper roleMenuMapMapper;

    public RoleMenuMapServiceImpl(RoleMenuMapRepository roleMenuMapRepository, RoleMenuMapMapper roleMenuMapMapper) {
        this.roleMenuMapRepository = roleMenuMapRepository;
        this.roleMenuMapMapper = roleMenuMapMapper;
    }

    @Override
    public RoleMenuMapDTO save(RoleMenuMapDTO roleMenuMapDTO) {
        log.debug("Request to save RoleMenuMap : {}", roleMenuMapDTO);
        RoleMenuMap roleMenuMap = roleMenuMapMapper.toEntity(roleMenuMapDTO);
        roleMenuMap = roleMenuMapRepository.save(roleMenuMap);
        return roleMenuMapMapper.toDto(roleMenuMap);
    }

    @Override
    public Optional<RoleMenuMapDTO> partialUpdate(RoleMenuMapDTO roleMenuMapDTO) {
        log.debug("Request to partially update RoleMenuMap : {}", roleMenuMapDTO);

        return roleMenuMapRepository
            .findById(roleMenuMapDTO.getId())
            .map(
                existingRoleMenuMap -> {
                    roleMenuMapMapper.partialUpdate(existingRoleMenuMap, roleMenuMapDTO);
                    return existingRoleMenuMap;
                }
            )
            .map(roleMenuMapRepository::save)
            .map(roleMenuMapMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoleMenuMapDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RoleMenuMaps");
        Page<RoleMenuMap> page = roleMenuMapRepository.findAll(pageable);
        return page.map(roleMenuMapMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoleMenuMapDTO> findOne(Long id) {
        log.debug("Request to get RoleMenuMap : {}", id);
        return roleMenuMapRepository.findById(id).map(roleMenuMapMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoleMenuMap : {}", id);
        roleMenuMapRepository.deleteById(id);
    }

    @Override
    public List<MenuAccessDTO> findMenusByRole(Set<String> roles) {
        List<RoleMenuMap> dataList = roleMenuMapRepository.findByRoleIdIn(roles);
        List<MenuAccessDTO> menuAccessList = null;

        if (dataList != null && dataList.size() > 0) {
            menuAccessList = new ArrayList<MenuAccessDTO>();

            //Getting Menu Group ID List first by Role
            List<RoleMenuMap> menuList = dataList;
            Set<Long> groupIDList = new HashSet<Long>();
            for (RoleMenuMap data : menuList) {
                RoleMenuMapDTO dto = new RoleMenuMapDTO();
                dto.setId(data.getId());
                dto.setRoleId(data.getRoleId());
                Menu menu = data.getMenu();
                if (menu != null) {
                    MenuGroup menuGroup = menu.getMenuGroup();
                    groupIDList.add(menuGroup.getId());
                }
            }

            //Getting Menu Group Information by Menu Group ID
            List<MenuAccessDTO> menuGroupList = new ArrayList<MenuAccessDTO>();
            if (groupIDList != null && groupIDList.size() > 0) {
                Iterator it = groupIDList.iterator();
                while (it.hasNext()) {
                    Long menuGroupID = (Long) it.next();
                    for (RoleMenuMap data : dataList) {
                        MenuAccessDTO menuAccessDTO = new MenuAccessDTO();
                        Menu menu = data.getMenu();
                        if (menu != null) {
                            MenuGroup menuGroup = menu.getMenuGroup();
                            if (menuGroup != null && menuGroup.getId() == menuGroupID) {
                                menuAccessDTO.setMenuGroupId(menuGroup.getId());
                                menuAccessDTO.setMenuGroupName(menuGroup.getName());
                                menuAccessDTO.setSequenceNo(menuGroup.getSequenceNo());
                                menuGroupList.add(menuAccessDTO);
                                break;
                            }
                        }
                    }
                } //End of While
                Collections.sort(menuGroupList, new MenuGroupSorting());

                //Getting Sub Menu List by Menu Group ID
                if (menuGroupList != null && menuGroupList.size() > 0) {
                    for (MenuAccessDTO data : menuGroupList) {
                        MenuAccessDTO groupData = data;
                        List<MenuDTO> subMenuList = new ArrayList<MenuDTO>();
                        for (RoleMenuMap menuMap : dataList) {
                            MenuDTO menuDto = new MenuDTO();
                            Menu menu = menuMap.getMenu();
                            if (menu != null) {
                                MenuGroup menuGroup = menu.getMenuGroup();
                                if (menuGroup != null && menuGroup.getId() == data.getMenuGroupId()) {
                                    menuDto.setId(menuMap.getId());
                                    menuDto.setMenuName(menu.getMenuName());
                                    menuDto.setRouterLink(menu.getRouterLink());
                                    subMenuList.add(menuDto);
                                }
                            }
                        } //End of Second Loop

                        groupData.setMenuList(subMenuList);
                        menuAccessList.add(groupData);
                    }
                }
            }
        }
        return menuAccessList;
    }
}
