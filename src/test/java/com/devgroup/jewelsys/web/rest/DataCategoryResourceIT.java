package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.DataCategory;
import com.devgroup.jewelsys.repository.DataCategoryRepository;
import com.devgroup.jewelsys.service.dto.DataCategoryDTO;
import com.devgroup.jewelsys.service.mapper.DataCategoryMapper;
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
 * Integration tests for the {@link DataCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DataCategoryResourceIT {

    private static final String DEFAULT_CATEGORY_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/data-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DataCategoryRepository dataCategoryRepository;

    @Autowired
    private DataCategoryMapper dataCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDataCategoryMockMvc;

    private DataCategory dataCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataCategory createEntity(EntityManager em) {
        DataCategory dataCategory = new DataCategory()
            .categoryType(DEFAULT_CATEGORY_TYPE)
            .categoryCode(DEFAULT_CATEGORY_CODE)
            .value(DEFAULT_VALUE)
            .categoryDesc(DEFAULT_CATEGORY_DESC);
        return dataCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DataCategory createUpdatedEntity(EntityManager em) {
        DataCategory dataCategory = new DataCategory()
            .categoryType(UPDATED_CATEGORY_TYPE)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .value(UPDATED_VALUE)
            .categoryDesc(UPDATED_CATEGORY_DESC);
        return dataCategory;
    }

    @BeforeEach
    public void initTest() {
        dataCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createDataCategory() throws Exception {
        int databaseSizeBeforeCreate = dataCategoryRepository.findAll().size();
        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);
        restDataCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        DataCategory testDataCategory = dataCategoryList.get(dataCategoryList.size() - 1);
        assertThat(testDataCategory.getCategoryType()).isEqualTo(DEFAULT_CATEGORY_TYPE);
        assertThat(testDataCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testDataCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDataCategory.getCategoryDesc()).isEqualTo(DEFAULT_CATEGORY_DESC);
    }

    @Test
    @Transactional
    void createDataCategoryWithExistingId() throws Exception {
        // Create the DataCategory with an existing ID
        dataCategory.setId(1L);
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        int databaseSizeBeforeCreate = dataCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDataCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllDataCategories() throws Exception {
        // Initialize the database
        dataCategoryRepository.saveAndFlush(dataCategory);

        // Get all the dataCategoryList
        restDataCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dataCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryType").value(hasItem(DEFAULT_CATEGORY_TYPE)))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].categoryDesc").value(hasItem(DEFAULT_CATEGORY_DESC)));
    }

    @Test
    @Transactional
    void getDataCategory() throws Exception {
        // Initialize the database
        dataCategoryRepository.saveAndFlush(dataCategory);

        // Get the dataCategory
        restDataCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, dataCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dataCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryType").value(DEFAULT_CATEGORY_TYPE))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.categoryDesc").value(DEFAULT_CATEGORY_DESC));
    }

    @Test
    @Transactional
    void getNonExistingDataCategory() throws Exception {
        // Get the dataCategory
        restDataCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDataCategory() throws Exception {
        // Initialize the database
        dataCategoryRepository.saveAndFlush(dataCategory);

        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();

        // Update the dataCategory
        DataCategory updatedDataCategory = dataCategoryRepository.findById(dataCategory.getId()).get();
        // Disconnect from session so that the updates on updatedDataCategory are not directly saved in db
        em.detach(updatedDataCategory);
        updatedDataCategory
            .categoryType(UPDATED_CATEGORY_TYPE)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .value(UPDATED_VALUE)
            .categoryDesc(UPDATED_CATEGORY_DESC);
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(updatedDataCategory);

        restDataCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
        DataCategory testDataCategory = dataCategoryList.get(dataCategoryList.size() - 1);
        assertThat(testDataCategory.getCategoryType()).isEqualTo(UPDATED_CATEGORY_TYPE);
        assertThat(testDataCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testDataCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDataCategory.getCategoryDesc()).isEqualTo(UPDATED_CATEGORY_DESC);
    }

    @Test
    @Transactional
    void putNonExistingDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();
        dataCategory.setId(count.incrementAndGet());

        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dataCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();
        dataCategory.setId(count.incrementAndGet());

        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();
        dataCategory.setId(count.incrementAndGet());

        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDataCategoryWithPatch() throws Exception {
        // Initialize the database
        dataCategoryRepository.saveAndFlush(dataCategory);

        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();

        // Update the dataCategory using partial update
        DataCategory partialUpdatedDataCategory = new DataCategory();
        partialUpdatedDataCategory.setId(dataCategory.getId());

        partialUpdatedDataCategory.categoryCode(UPDATED_CATEGORY_CODE).categoryDesc(UPDATED_CATEGORY_DESC);

        restDataCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataCategory))
            )
            .andExpect(status().isOk());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
        DataCategory testDataCategory = dataCategoryList.get(dataCategoryList.size() - 1);
        assertThat(testDataCategory.getCategoryType()).isEqualTo(DEFAULT_CATEGORY_TYPE);
        assertThat(testDataCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testDataCategory.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDataCategory.getCategoryDesc()).isEqualTo(UPDATED_CATEGORY_DESC);
    }

    @Test
    @Transactional
    void fullUpdateDataCategoryWithPatch() throws Exception {
        // Initialize the database
        dataCategoryRepository.saveAndFlush(dataCategory);

        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();

        // Update the dataCategory using partial update
        DataCategory partialUpdatedDataCategory = new DataCategory();
        partialUpdatedDataCategory.setId(dataCategory.getId());

        partialUpdatedDataCategory
            .categoryType(UPDATED_CATEGORY_TYPE)
            .categoryCode(UPDATED_CATEGORY_CODE)
            .value(UPDATED_VALUE)
            .categoryDesc(UPDATED_CATEGORY_DESC);

        restDataCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDataCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDataCategory))
            )
            .andExpect(status().isOk());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
        DataCategory testDataCategory = dataCategoryList.get(dataCategoryList.size() - 1);
        assertThat(testDataCategory.getCategoryType()).isEqualTo(UPDATED_CATEGORY_TYPE);
        assertThat(testDataCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testDataCategory.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDataCategory.getCategoryDesc()).isEqualTo(UPDATED_CATEGORY_DESC);
    }

    @Test
    @Transactional
    void patchNonExistingDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();
        dataCategory.setId(count.incrementAndGet());

        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDataCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dataCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();
        dataCategory.setId(count.incrementAndGet());

        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDataCategory() throws Exception {
        int databaseSizeBeforeUpdate = dataCategoryRepository.findAll().size();
        dataCategory.setId(count.incrementAndGet());

        // Create the DataCategory
        DataCategoryDTO dataCategoryDTO = dataCategoryMapper.toDto(dataCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDataCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dataCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DataCategory in the database
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDataCategory() throws Exception {
        // Initialize the database
        dataCategoryRepository.saveAndFlush(dataCategory);

        int databaseSizeBeforeDelete = dataCategoryRepository.findAll().size();

        // Delete the dataCategory
        restDataCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, dataCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DataCategory> dataCategoryList = dataCategoryRepository.findAll();
        assertThat(dataCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
