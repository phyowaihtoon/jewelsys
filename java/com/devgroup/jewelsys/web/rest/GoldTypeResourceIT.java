package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GoldType;
import com.devgroup.jewelsys.repository.GoldTypeRepository;
import com.devgroup.jewelsys.service.dto.GoldTypeDTO;
import com.devgroup.jewelsys.service.mapper.GoldTypeMapper;
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
 * Integration tests for the {@link GoldTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoldTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gold-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoldTypeRepository goldTypeRepository;

    @Autowired
    private GoldTypeMapper goldTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoldTypeMockMvc;

    private GoldType goldType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldType createEntity(EntityManager em) {
        GoldType goldType = new GoldType().code(DEFAULT_CODE).name(DEFAULT_NAME).delFlg(DEFAULT_DEL_FLG);
        return goldType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldType createUpdatedEntity(EntityManager em) {
        GoldType goldType = new GoldType().code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        return goldType;
    }

    @BeforeEach
    public void initTest() {
        goldType = createEntity(em);
    }

    @Test
    @Transactional
    void createGoldType() throws Exception {
        int databaseSizeBeforeCreate = goldTypeRepository.findAll().size();
        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);
        restGoldTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeCreate + 1);
        GoldType testGoldType = goldTypeList.get(goldTypeList.size() - 1);
        assertThat(testGoldType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGoldType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoldType.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGoldTypeWithExistingId() throws Exception {
        // Create the GoldType with an existing ID
        goldType.setId(1L);
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        int databaseSizeBeforeCreate = goldTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoldTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldTypeRepository.findAll().size();
        // set the field null
        goldType.setCode(null);

        // Create the GoldType, which fails.
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        restGoldTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldTypeDTO)))
            .andExpect(status().isBadRequest());

        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldTypeRepository.findAll().size();
        // set the field null
        goldType.setName(null);

        // Create the GoldType, which fails.
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        restGoldTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldTypeDTO)))
            .andExpect(status().isBadRequest());

        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoldTypes() throws Exception {
        // Initialize the database
        goldTypeRepository.saveAndFlush(goldType);

        // Get all the goldTypeList
        restGoldTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goldType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGoldType() throws Exception {
        // Initialize the database
        goldTypeRepository.saveAndFlush(goldType);

        // Get the goldType
        restGoldTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, goldType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goldType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGoldType() throws Exception {
        // Get the goldType
        restGoldTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGoldType() throws Exception {
        // Initialize the database
        goldTypeRepository.saveAndFlush(goldType);

        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();

        // Update the goldType
        GoldType updatedGoldType = goldTypeRepository.findById(goldType.getId()).get();
        // Disconnect from session so that the updates on updatedGoldType are not directly saved in db
        em.detach(updatedGoldType);
        updatedGoldType.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(updatedGoldType);

        restGoldTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
        GoldType testGoldType = goldTypeList.get(goldTypeList.size() - 1);
        assertThat(testGoldType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldType.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGoldType() throws Exception {
        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();
        goldType.setId(count.incrementAndGet());

        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoldType() throws Exception {
        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();
        goldType.setId(count.incrementAndGet());

        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoldType() throws Exception {
        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();
        goldType.setId(count.incrementAndGet());

        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoldTypeWithPatch() throws Exception {
        // Initialize the database
        goldTypeRepository.saveAndFlush(goldType);

        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();

        // Update the goldType using partial update
        GoldType partialUpdatedGoldType = new GoldType();
        partialUpdatedGoldType.setId(goldType.getId());

        restGoldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldType))
            )
            .andExpect(status().isOk());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
        GoldType testGoldType = goldTypeList.get(goldTypeList.size() - 1);
        assertThat(testGoldType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGoldType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoldType.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGoldTypeWithPatch() throws Exception {
        // Initialize the database
        goldTypeRepository.saveAndFlush(goldType);

        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();

        // Update the goldType using partial update
        GoldType partialUpdatedGoldType = new GoldType();
        partialUpdatedGoldType.setId(goldType.getId());

        partialUpdatedGoldType.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);

        restGoldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldType))
            )
            .andExpect(status().isOk());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
        GoldType testGoldType = goldTypeList.get(goldTypeList.size() - 1);
        assertThat(testGoldType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldType.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGoldType() throws Exception {
        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();
        goldType.setId(count.incrementAndGet());

        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goldTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoldType() throws Exception {
        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();
        goldType.setId(count.incrementAndGet());

        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoldType() throws Exception {
        int databaseSizeBeforeUpdate = goldTypeRepository.findAll().size();
        goldType.setId(count.incrementAndGet());

        // Create the GoldType
        GoldTypeDTO goldTypeDTO = goldTypeMapper.toDto(goldType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(goldTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldType in the database
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoldType() throws Exception {
        // Initialize the database
        goldTypeRepository.saveAndFlush(goldType);

        int databaseSizeBeforeDelete = goldTypeRepository.findAll().size();

        // Delete the goldType
        restGoldTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, goldType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoldType> goldTypeList = goldTypeRepository.findAll();
        assertThat(goldTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
