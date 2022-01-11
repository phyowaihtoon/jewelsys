package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GemsItem;
import com.devgroup.jewelsys.repository.GemsItemRepository;
import com.devgroup.jewelsys.service.dto.GemsItemDTO;
import com.devgroup.jewelsys.service.mapper.GemsItemMapper;
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
 * Integration tests for the {@link GemsItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemsItemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gems-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GemsItemRepository gemsItemRepository;

    @Autowired
    private GemsItemMapper gemsItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemsItemMockMvc;

    private GemsItem gemsItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GemsItem createEntity(EntityManager em) {
        GemsItem gemsItem = new GemsItem().code(DEFAULT_CODE).name(DEFAULT_NAME).delFlg(DEFAULT_DEL_FLG);
        return gemsItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GemsItem createUpdatedEntity(EntityManager em) {
        GemsItem gemsItem = new GemsItem().code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        return gemsItem;
    }

    @BeforeEach
    public void initTest() {
        gemsItem = createEntity(em);
    }

    @Test
    @Transactional
    void createGemsItem() throws Exception {
        int databaseSizeBeforeCreate = gemsItemRepository.findAll().size();
        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);
        restGemsItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsItemDTO)))
            .andExpect(status().isCreated());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeCreate + 1);
        GemsItem testGemsItem = gemsItemList.get(gemsItemList.size() - 1);
        assertThat(testGemsItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGemsItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGemsItem.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGemsItemWithExistingId() throws Exception {
        // Create the GemsItem with an existing ID
        gemsItem.setId(1L);
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        int databaseSizeBeforeCreate = gemsItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemsItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsItemRepository.findAll().size();
        // set the field null
        gemsItem.setCode(null);

        // Create the GemsItem, which fails.
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        restGemsItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsItemDTO)))
            .andExpect(status().isBadRequest());

        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsItemRepository.findAll().size();
        // set the field null
        gemsItem.setName(null);

        // Create the GemsItem, which fails.
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        restGemsItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsItemDTO)))
            .andExpect(status().isBadRequest());

        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGemsItems() throws Exception {
        // Initialize the database
        gemsItemRepository.saveAndFlush(gemsItem);

        // Get all the gemsItemList
        restGemsItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemsItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGemsItem() throws Exception {
        // Initialize the database
        gemsItemRepository.saveAndFlush(gemsItem);

        // Get the gemsItem
        restGemsItemMockMvc
            .perform(get(ENTITY_API_URL_ID, gemsItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemsItem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGemsItem() throws Exception {
        // Get the gemsItem
        restGemsItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGemsItem() throws Exception {
        // Initialize the database
        gemsItemRepository.saveAndFlush(gemsItem);

        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();

        // Update the gemsItem
        GemsItem updatedGemsItem = gemsItemRepository.findById(gemsItem.getId()).get();
        // Disconnect from session so that the updates on updatedGemsItem are not directly saved in db
        em.detach(updatedGemsItem);
        updatedGemsItem.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(updatedGemsItem);

        restGemsItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemsItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
        GemsItem testGemsItem = gemsItemList.get(gemsItemList.size() - 1);
        assertThat(testGemsItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGemsItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGemsItem.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGemsItem() throws Exception {
        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();
        gemsItem.setId(count.incrementAndGet());

        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemsItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemsItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGemsItem() throws Exception {
        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();
        gemsItem.setId(count.incrementAndGet());

        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGemsItem() throws Exception {
        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();
        gemsItem.setId(count.incrementAndGet());

        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGemsItemWithPatch() throws Exception {
        // Initialize the database
        gemsItemRepository.saveAndFlush(gemsItem);

        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();

        // Update the gemsItem using partial update
        GemsItem partialUpdatedGemsItem = new GemsItem();
        partialUpdatedGemsItem.setId(gemsItem.getId());

        partialUpdatedGemsItem.code(UPDATED_CODE);

        restGemsItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemsItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGemsItem))
            )
            .andExpect(status().isOk());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
        GemsItem testGemsItem = gemsItemList.get(gemsItemList.size() - 1);
        assertThat(testGemsItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGemsItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGemsItem.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGemsItemWithPatch() throws Exception {
        // Initialize the database
        gemsItemRepository.saveAndFlush(gemsItem);

        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();

        // Update the gemsItem using partial update
        GemsItem partialUpdatedGemsItem = new GemsItem();
        partialUpdatedGemsItem.setId(gemsItem.getId());

        partialUpdatedGemsItem.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);

        restGemsItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemsItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGemsItem))
            )
            .andExpect(status().isOk());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
        GemsItem testGemsItem = gemsItemList.get(gemsItemList.size() - 1);
        assertThat(testGemsItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGemsItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGemsItem.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGemsItem() throws Exception {
        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();
        gemsItem.setId(count.incrementAndGet());

        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemsItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gemsItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGemsItem() throws Exception {
        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();
        gemsItem.setId(count.incrementAndGet());

        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGemsItem() throws Exception {
        int databaseSizeBeforeUpdate = gemsItemRepository.findAll().size();
        gemsItem.setId(count.incrementAndGet());

        // Create the GemsItem
        GemsItemDTO gemsItemDTO = gemsItemMapper.toDto(gemsItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(gemsItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GemsItem in the database
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGemsItem() throws Exception {
        // Initialize the database
        gemsItemRepository.saveAndFlush(gemsItem);

        int databaseSizeBeforeDelete = gemsItemRepository.findAll().size();

        // Delete the gemsItem
        restGemsItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemsItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GemsItem> gemsItemList = gemsItemRepository.findAll();
        assertThat(gemsItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
