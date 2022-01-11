package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.MenuGroup;
import com.devgroup.jewelsys.repository.MenuGroupRepository;
import com.devgroup.jewelsys.service.dto.MenuGroupDTO;
import com.devgroup.jewelsys.service.mapper.MenuGroupMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MenuGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MenuGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;

    private static final String ENTITY_API_URL = "/api/menu-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MenuGroupRepository menuGroupRepository;

    @Autowired
    private MenuGroupMapper menuGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMenuGroupMockMvc;

    private MenuGroup menuGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuGroup createEntity(EntityManager em) {
        MenuGroup menuGroup = new MenuGroup().name(DEFAULT_NAME).sequenceNo(DEFAULT_SEQUENCE_NO);
        return menuGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MenuGroup createUpdatedEntity(EntityManager em) {
        MenuGroup menuGroup = new MenuGroup().name(UPDATED_NAME).sequenceNo(UPDATED_SEQUENCE_NO);
        return menuGroup;
    }

    @BeforeEach
    public void initTest() {
        menuGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createMenuGroup() throws Exception {
        int databaseSizeBeforeCreate = menuGroupRepository.findAll().size();
        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);
        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isCreated());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeCreate + 1);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenuGroup.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    void createMenuGroupWithExistingId() throws Exception {
        // Create the MenuGroup with an existing ID
        menuGroup.setId(1L);
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        int databaseSizeBeforeCreate = menuGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuGroupRepository.findAll().size();
        // set the field null
        menuGroup.setName(null);

        // Create the MenuGroup, which fails.
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        restMenuGroupMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isBadRequest());

        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMenuGroups() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        // Get all the menuGroupList
        restMenuGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menuGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }

    @Test
    @Transactional
    void getMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        // Get the menuGroup
        restMenuGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, menuGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(menuGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }

    @Test
    @Transactional
    void getNonExistingMenuGroup() throws Exception {
        // Get the menuGroup
        restMenuGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup
        MenuGroup updatedMenuGroup = menuGroupRepository.findById(menuGroup.getId()).get();
        // Disconnect from session so that the updates on updatedMenuGroup are not directly saved in db
        em.detach(updatedMenuGroup);
        updatedMenuGroup.name(UPDATED_NAME).sequenceNo(UPDATED_SEQUENCE_NO);
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(updatedMenuGroup);

        restMenuGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuGroup.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    void putNonExistingMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(count.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, menuGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(count.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(count.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(menuGroupDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMenuGroupWithPatch() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup using partial update
        MenuGroup partialUpdatedMenuGroup = new MenuGroup();
        partialUpdatedMenuGroup.setId(menuGroup.getId());

        partialUpdatedMenuGroup.sequenceNo(UPDATED_SEQUENCE_NO);

        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuGroup))
            )
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMenuGroup.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    void fullUpdateMenuGroupWithPatch() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();

        // Update the menuGroup using partial update
        MenuGroup partialUpdatedMenuGroup = new MenuGroup();
        partialUpdatedMenuGroup.setId(menuGroup.getId());

        partialUpdatedMenuGroup.name(UPDATED_NAME).sequenceNo(UPDATED_SEQUENCE_NO);

        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMenuGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMenuGroup))
            )
            .andExpect(status().isOk());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
        MenuGroup testMenuGroup = menuGroupList.get(menuGroupList.size() - 1);
        assertThat(testMenuGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMenuGroup.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    void patchNonExistingMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(count.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, menuGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(count.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMenuGroup() throws Exception {
        int databaseSizeBeforeUpdate = menuGroupRepository.findAll().size();
        menuGroup.setId(count.incrementAndGet());

        // Create the MenuGroup
        MenuGroupDTO menuGroupDTO = menuGroupMapper.toDto(menuGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMenuGroupMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(menuGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MenuGroup in the database
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMenuGroup() throws Exception {
        // Initialize the database
        menuGroupRepository.saveAndFlush(menuGroup);

        int databaseSizeBeforeDelete = menuGroupRepository.findAll().size();

        // Delete the menuGroup
        restMenuGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, menuGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MenuGroup> menuGroupList = menuGroupRepository.findAll();
        assertThat(menuGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
