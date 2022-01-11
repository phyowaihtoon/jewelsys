package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GemsType;
import com.devgroup.jewelsys.repository.GemsTypeRepository;
import com.devgroup.jewelsys.service.dto.GemsTypeDTO;
import com.devgroup.jewelsys.service.mapper.GemsTypeMapper;
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
 * Integration tests for the {@link GemsTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemsTypeResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gems-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GemsTypeRepository gemsTypeRepository;

    @Autowired
    private GemsTypeMapper gemsTypeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemsTypeMockMvc;

    private GemsType gemsType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GemsType createEntity(EntityManager em) {
        GemsType gemsType = new GemsType().code(DEFAULT_CODE).name(DEFAULT_NAME).delFlg(DEFAULT_DEL_FLG);
        return gemsType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GemsType createUpdatedEntity(EntityManager em) {
        GemsType gemsType = new GemsType().code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        return gemsType;
    }

    @BeforeEach
    public void initTest() {
        gemsType = createEntity(em);
    }

    @Test
    @Transactional
    void createGemsType() throws Exception {
        int databaseSizeBeforeCreate = gemsTypeRepository.findAll().size();
        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);
        restGemsTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeCreate + 1);
        GemsType testGemsType = gemsTypeList.get(gemsTypeList.size() - 1);
        assertThat(testGemsType.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGemsType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGemsType.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGemsTypeWithExistingId() throws Exception {
        // Create the GemsType with an existing ID
        gemsType.setId(1L);
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        int databaseSizeBeforeCreate = gemsTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemsTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsTypeRepository.findAll().size();
        // set the field null
        gemsType.setCode(null);

        // Create the GemsType, which fails.
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        restGemsTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO)))
            .andExpect(status().isBadRequest());

        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsTypeRepository.findAll().size();
        // set the field null
        gemsType.setName(null);

        // Create the GemsType, which fails.
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        restGemsTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO)))
            .andExpect(status().isBadRequest());

        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGemsTypes() throws Exception {
        // Initialize the database
        gemsTypeRepository.saveAndFlush(gemsType);

        // Get all the gemsTypeList
        restGemsTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemsType.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGemsType() throws Exception {
        // Initialize the database
        gemsTypeRepository.saveAndFlush(gemsType);

        // Get the gemsType
        restGemsTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, gemsType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemsType.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGemsType() throws Exception {
        // Get the gemsType
        restGemsTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGemsType() throws Exception {
        // Initialize the database
        gemsTypeRepository.saveAndFlush(gemsType);

        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();

        // Update the gemsType
        GemsType updatedGemsType = gemsTypeRepository.findById(gemsType.getId()).get();
        // Disconnect from session so that the updates on updatedGemsType are not directly saved in db
        em.detach(updatedGemsType);
        updatedGemsType.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(updatedGemsType);

        restGemsTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemsTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
        GemsType testGemsType = gemsTypeList.get(gemsTypeList.size() - 1);
        assertThat(testGemsType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGemsType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGemsType.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGemsType() throws Exception {
        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();
        gemsType.setId(count.incrementAndGet());

        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemsTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemsTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGemsType() throws Exception {
        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();
        gemsType.setId(count.incrementAndGet());

        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGemsType() throws Exception {
        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();
        gemsType.setId(count.incrementAndGet());

        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGemsTypeWithPatch() throws Exception {
        // Initialize the database
        gemsTypeRepository.saveAndFlush(gemsType);

        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();

        // Update the gemsType using partial update
        GemsType partialUpdatedGemsType = new GemsType();
        partialUpdatedGemsType.setId(gemsType.getId());

        partialUpdatedGemsType.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);

        restGemsTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemsType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGemsType))
            )
            .andExpect(status().isOk());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
        GemsType testGemsType = gemsTypeList.get(gemsTypeList.size() - 1);
        assertThat(testGemsType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGemsType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGemsType.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGemsTypeWithPatch() throws Exception {
        // Initialize the database
        gemsTypeRepository.saveAndFlush(gemsType);

        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();

        // Update the gemsType using partial update
        GemsType partialUpdatedGemsType = new GemsType();
        partialUpdatedGemsType.setId(gemsType.getId());

        partialUpdatedGemsType.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);

        restGemsTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemsType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGemsType))
            )
            .andExpect(status().isOk());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
        GemsType testGemsType = gemsTypeList.get(gemsTypeList.size() - 1);
        assertThat(testGemsType.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGemsType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGemsType.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGemsType() throws Exception {
        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();
        gemsType.setId(count.incrementAndGet());

        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemsTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gemsTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGemsType() throws Exception {
        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();
        gemsType.setId(count.incrementAndGet());

        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGemsType() throws Exception {
        int databaseSizeBeforeUpdate = gemsTypeRepository.findAll().size();
        gemsType.setId(count.incrementAndGet());

        // Create the GemsType
        GemsTypeDTO gemsTypeDTO = gemsTypeMapper.toDto(gemsType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsTypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gemsTypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GemsType in the database
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGemsType() throws Exception {
        // Initialize the database
        gemsTypeRepository.saveAndFlush(gemsType);

        int databaseSizeBeforeDelete = gemsTypeRepository.findAll().size();

        // Delete the gemsType
        restGemsTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemsType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GemsType> gemsTypeList = gemsTypeRepository.findAll();
        assertThat(gemsTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
