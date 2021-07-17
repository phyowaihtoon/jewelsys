package com.devgroup.jewelsys.service.impl;

import com.devgroup.jewelsys.domain.MenuGroup;
import com.devgroup.jewelsys.repository.MenuGroupRepository;
import com.devgroup.jewelsys.service.MenuGroupService;
import com.devgroup.jewelsys.service.dto.MenuGroupDTO;
import com.devgroup.jewelsys.service.mapper.MenuGroupMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MenuGroup}.
 */
@Service
@Transactional
public class MenuGroupServiceImpl implements MenuGroupService {

    private final Logger log = LoggerFactory.getLogger(MenuGroupServiceImpl.class);

    private final MenuGroupRepository menuGroupRepository;

    private final MenuGroupMapper menuGroupMapper;

    public MenuGroupServiceImpl(MenuGroupRepository menuGroupRepository, MenuGroupMapper menuGroupMapper) {
        this.menuGroupRepository = menuGroupRepository;
        this.menuGroupMapper = menuGroupMapper;
    }

    @Override
    public MenuGroupDTO save(MenuGroupDTO menuGroupDTO) {
        log.debug("Request to save MenuGroup : {}", menuGroupDTO);
        MenuGroup menuGroup = menuGroupMapper.toEntity(menuGroupDTO);
        menuGroup = menuGroupRepository.save(menuGroup);
        return menuGroupMapper.toDto(menuGroup);
    }

    @Override
    public Optional<MenuGroupDTO> partialUpdate(MenuGroupDTO menuGroupDTO) {
        log.debug("Request to partially update MenuGroup : {}", menuGroupDTO);

        return menuGroupRepository
            .findById(menuGroupDTO.getId())
            .map(
                existingMenuGroup -> {
                    menuGroupMapper.partialUpdate(existingMenuGroup, menuGroupDTO);
                    return existingMenuGroup;
                }
            )
            .map(menuGroupRepository::save)
            .map(menuGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MenuGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MenuGroups");
        return menuGroupRepository.findAll(pageable).map(menuGroupMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MenuGroupDTO> findOne(Long id) {
        log.debug("Request to get MenuGroup : {}", id);
        return menuGroupRepository.findById(id).map(menuGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MenuGroup : {}", id);
        menuGroupRepository.deleteById(id);
    }
}
