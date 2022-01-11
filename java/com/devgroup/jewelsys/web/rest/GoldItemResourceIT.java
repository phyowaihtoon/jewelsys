package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GoldItem;
import com.devgroup.jewelsys.repository.GoldItemRepository;
import com.devgroup.jewelsys.service.dto.GoldItemDTO;
import com.devgroup.jewelsys.service.mapper.GoldItemMapper;
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
 * Integration tests for the {@link GoldItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoldItemResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gold-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoldItemRepository goldItemRepository;

    @Autowired
    private GoldItemMapper goldItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoldItemMockMvc;

    private GoldItem goldItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldItem createEntity(EntityManager em) {
        GoldItem goldItem = new GoldItem().code(DEFAULT_CODE).name(DEFAULT_NAME).delFlg(DEFAULT_DEL_FLG);
        return goldItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldItem createUpdatedEntity(EntityManager em) {
        GoldItem goldItem = new GoldItem().code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        return goldItem;
    }

    @BeforeEach
    public void initTest() {
        goldItem = createEntity(em);
    }

    @Test
    @Transactional
    void createGoldItem() throws Exception {
        int databaseSizeBeforeCreate = goldItemRepository.findAll().size();
        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);
        restGoldItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemDTO)))
            .andExpect(status().isCreated());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeCreate + 1);
        GoldItem testGoldItem = goldItemList.get(goldItemList.size() - 1);
        assertThat(testGoldItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGoldItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoldItem.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGoldItemWithExistingId() throws Exception {
        // Create the GoldItem with an existing ID
        goldItem.setId(1L);
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        int databaseSizeBeforeCreate = goldItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoldItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldItemRepository.findAll().size();
        // set the field null
        goldItem.setCode(null);

        // Create the GoldItem, which fails.
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        restGoldItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemDTO)))
            .andExpect(status().isBadRequest());

        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldItemRepository.findAll().size();
        // set the field null
        goldItem.setName(null);

        // Create the GoldItem, which fails.
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        restGoldItemMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemDTO)))
            .andExpect(status().isBadRequest());

        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoldItems() throws Exception {
        // Initialize the database
        goldItemRepository.saveAndFlush(goldItem);

        // Get all the goldItemList
        restGoldItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goldItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGoldItem() throws Exception {
        // Initialize the database
        goldItemRepository.saveAndFlush(goldItem);

        // Get the goldItem
        restGoldItemMockMvc
            .perform(get(ENTITY_API_URL_ID, goldItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goldItem.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGoldItem() throws Exception {
        // Get the goldItem
        restGoldItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGoldItem() throws Exception {
        // Initialize the database
        goldItemRepository.saveAndFlush(goldItem);

        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();

        // Update the goldItem
        GoldItem updatedGoldItem = goldItemRepository.findById(goldItem.getId()).get();
        // Disconnect from session so that the updates on updatedGoldItem are not directly saved in db
        em.detach(updatedGoldItem);
        updatedGoldItem.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(updatedGoldItem);

        restGoldItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
        GoldItem testGoldItem = goldItemList.get(goldItemList.size() - 1);
        assertThat(testGoldItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldItem.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGoldItem() throws Exception {
        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();
        goldItem.setId(count.incrementAndGet());

        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoldItem() throws Exception {
        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();
        goldItem.setId(count.incrementAndGet());

        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoldItem() throws Exception {
        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();
        goldItem.setId(count.incrementAndGet());

        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldItemDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoldItemWithPatch() throws Exception {
        // Initialize the database
        goldItemRepository.saveAndFlush(goldItem);

        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();

        // Update the goldItem using partial update
        GoldItem partialUpdatedGoldItem = new GoldItem();
        partialUpdatedGoldItem.setId(goldItem.getId());

        partialUpdatedGoldItem.code(UPDATED_CODE);

        restGoldItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldItem))
            )
            .andExpect(status().isOk());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
        GoldItem testGoldItem = goldItemList.get(goldItemList.size() - 1);
        assertThat(testGoldItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldItem.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGoldItem.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGoldItemWithPatch() throws Exception {
        // Initialize the database
        goldItemRepository.saveAndFlush(goldItem);

        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();

        // Update the goldItem using partial update
        GoldItem partialUpdatedGoldItem = new GoldItem();
        partialUpdatedGoldItem.setId(goldItem.getId());

        partialUpdatedGoldItem.code(UPDATED_CODE).name(UPDATED_NAME).delFlg(UPDATED_DEL_FLG);

        restGoldItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldItem))
            )
            .andExpect(status().isOk());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
        GoldItem testGoldItem = goldItemList.get(goldItemList.size() - 1);
        assertThat(testGoldItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGoldItem.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGoldItem.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGoldItem() throws Exception {
        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();
        goldItem.setId(count.incrementAndGet());

        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goldItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoldItem() throws Exception {
        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();
        goldItem.setId(count.incrementAndGet());

        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoldItem() throws Exception {
        int databaseSizeBeforeUpdate = goldItemRepository.findAll().size();
        goldItem.setId(count.incrementAndGet());

        // Create the GoldItem
        GoldItemDTO goldItemDTO = goldItemMapper.toDto(goldItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldItemMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(goldItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldItem in the database
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoldItem() throws Exception {
        // Initialize the database
        goldItemRepository.saveAndFlush(goldItem);

        int databaseSizeBeforeDelete = goldItemRepository.findAll().size();

        // Delete the goldItem
        restGoldItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, goldItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoldItem> goldItemList = goldItemRepository.findAll();
        assertThat(goldItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
