package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GoldItemGroup;
import com.devgroup.jewelsys.repository.GoldItemGroupRepository;
import com.devgroup.jewelsys.service.dto.GoldItemGroupDTO;
import com.devgroup.jewelsys.service.mapper.GoldItemGroupMapper;
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
 * Integration tests for the {@link GoldItemGroupResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoldItemGroupResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gold-item-groups";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoldItemGroupRepository goldItemGroupRepository;

    @Autowired
    private GoldItemGroupMapper goldItemGroupMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoldItemGroupMockMvc;

    private GoldItemGroup goldItemGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldItemGroup createEntity(EntityManager em) {
        GoldItemGroup goldItemGroup = new GoldItemGroup().code(DEFAULT_CODE).name(DEFAULT_NAME).delFlg(DEFAULT_DEL_FLG);
        return goldItemGroup;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldItemGroup createUpdatedEntity(EntityManager em) {
        GoldItemGroup goldItemGroup = new GoldItemGroup().code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        return goldItemGroup;
    }

    @BeforeEach
    public void initTest() {
        goldItemGroup = createEntity(em);
    }

    @Test
    @Transactional
    void createGoldItemGroup() throws Exception {
        int databaseSizeBeforeCreate = goldItemGroupRepository.findAll().size();
        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);
        restGoldItemGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeCreate + 1);
        GoldItemGroup testGoldItemGroup = goldItemGroupList.get(goldItemGroupList.size() - 1);
        assertThat(testGoldItemGroup.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGoldItemGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoldItemGroup.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGoldItemGroupWithExistingId() throws Exception {
        // Create the GoldItemGroup with an existing ID
        goldItemGroup.setId(1L);
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        int databaseSizeBeforeCreate = goldItemGroupRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoldItemGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldItemGroupRepository.findAll().size();
        // set the field null
        goldItemGroup.setCode(null);

        // Create the GoldItemGroup, which fails.
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        restGoldItemGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldItemGroupRepository.findAll().size();
        // set the field null
        goldItemGroup.setName(null);

        // Create the GoldItemGroup, which fails.
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        restGoldItemGroupMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoldItemGroups() throws Exception {
        // Initialize the database
        goldItemGroupRepository.saveAndFlush(goldItemGroup);

        // Get all the goldItemGroupList
        restGoldItemGroupMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goldItemGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGoldItemGroup() throws Exception {
        // Initialize the database
        goldItemGroupRepository.saveAndFlush(goldItemGroup);

        // Get the goldItemGroup
        restGoldItemGroupMockMvc
            .perform(get(ENTITY_API_URL_ID, goldItemGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goldItemGroup.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGoldItemGroup() throws Exception {
        // Get the goldItemGroup
        restGoldItemGroupMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGoldItemGroup() throws Exception {
        // Initialize the database
        goldItemGroupRepository.saveAndFlush(goldItemGroup);

        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();

        // Update the goldItemGroup
        GoldItemGroup updatedGoldItemGroup = goldItemGroupRepository.findById(goldItemGroup.getId()).get();
        // Disconnect from session so that the updates on updatedGoldItemGroup are not directly saved in db
        em.detach(updatedGoldItemGroup);
        updatedGoldItemGroup.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(updatedGoldItemGroup);

        restGoldItemGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldItemGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isOk());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
        GoldItemGroup testGoldItemGroup = goldItemGroupList.get(goldItemGroupList.size() - 1);
        assertThat(testGoldItemGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldItemGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldItemGroup.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGoldItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();
        goldItemGroup.setId(count.incrementAndGet());

        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldItemGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldItemGroupDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoldItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();
        goldItemGroup.setId(count.incrementAndGet());

        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemGroupMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoldItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();
        goldItemGroup.setId(count.incrementAndGet());

        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemGroupMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoldItemGroupWithPatch() throws Exception {
        // Initialize the database
        goldItemGroupRepository.saveAndFlush(goldItemGroup);

        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();

        // Update the goldItemGroup using partial update
        GoldItemGroup partialUpdatedGoldItemGroup = new GoldItemGroup();
        partialUpdatedGoldItemGroup.setId(goldItemGroup.getId());

        partialUpdatedGoldItemGroup.code(UPDATED_CODE).name(UPDATED_NAME);

        restGoldItemGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldItemGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldItemGroup))
            )
            .andExpect(status().isOk());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
        GoldItemGroup testGoldItemGroup = goldItemGroupList.get(goldItemGroupList.size() - 1);
        assertThat(testGoldItemGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldItemGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldItemGroup.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGoldItemGroupWithPatch() throws Exception {
        // Initialize the database
        goldItemGroupRepository.saveAndFlush(goldItemGroup);

        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();

        // Update the goldItemGroup using partial update
        GoldItemGroup partialUpdatedGoldItemGroup = new GoldItemGroup();
        partialUpdatedGoldItemGroup.setId(goldItemGroup.getId());

        partialUpdatedGoldItemGroup.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);

        restGoldItemGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldItemGroup.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldItemGroup))
            )
            .andExpect(status().isOk());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
        GoldItemGroup testGoldItemGroup = goldItemGroupList.get(goldItemGroupList.size() - 1);
        assertThat(testGoldItemGroup.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldItemGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldItemGroup.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGoldItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();
        goldItemGroup.setId(count.incrementAndGet());

        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldItemGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goldItemGroupDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoldItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();
        goldItemGroup.setId(count.incrementAndGet());

        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemGroupMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoldItemGroup() throws Exception {
        int databaseSizeBeforeUpdate = goldItemGroupRepository.findAll().size();
        goldItemGroup.setId(count.incrementAndGet());

        // Create the GoldItemGroup
        GoldItemGroupDTO goldItemGroupDTO = goldItemGroupMapper.toDto(goldItemGroup);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemGroupMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldItemGroupDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldItemGroup in the database
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoldItemGroup() throws Exception {
        // Initialize the database
        goldItemGroupRepository.saveAndFlush(goldItemGroup);

        int databaseSizeBeforeDelete = goldItemGroupRepository.findAll().size();

        // Delete the goldItemGroup
        restGoldItemGroupMockMvc
            .perform(delete(ENTITY_API_URL_ID, goldItemGroup.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoldItemGroup> goldItemGroupList = goldItemGroupRepository.findAll();
        assertThat(goldItemGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
