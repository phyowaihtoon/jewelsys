package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.MortgageItem;
import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import com.devgroup.jewelsys.repository.MortgageItemRepository;
import com.devgroup.jewelsys.service.dto.MortgageItemDTO;
import com.devgroup.jewelsys.service.mapper.MortgageItemMapper;
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
 * Integration tests for the {@link MortgageItemResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MortgageItemResourceIT {

    private static final MortgageItemGroup DEFAULT_GROUP_CODE = MortgageItemGroup.G01;
    private static final MortgageItemGroup UPDATED_GROUP_CODE = MortgageItemGroup.G02;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mortgage-items";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MortgageItemRepository mortgageItemRepository;

    @Autowired
    private MortgageItemMapper mortgageItemMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMortgageItemMockMvc;

    private MortgageItem mortgageItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MortgageItem createEntity(EntityManager em) {
        MortgageItem mortgageItem = new MortgageItem()
            .groupCode(DEFAULT_GROUP_CODE)
            .code(DEFAULT_CODE)
            .itemName(DEFAULT_ITEM_NAME)
            .delFlg(DEFAULT_DEL_FLG);
        return mortgageItem;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MortgageItem createUpdatedEntity(EntityManager em) {
        MortgageItem mortgageItem = new MortgageItem()
            .groupCode(UPDATED_GROUP_CODE)
            .code(UPDATED_CODE)
            .itemName(UPDATED_ITEM_NAME)
            .delFlg(UPDATED_DEL_FLG);
        return mortgageItem;
    }

    @BeforeEach
    public void initTest() {
        mortgageItem = createEntity(em);
    }

    @Test
    @Transactional
    void createMortgageItem() throws Exception {
        int databaseSizeBeforeCreate = mortgageItemRepository.findAll().size();
        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);
        restMortgageItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeCreate + 1);
        MortgageItem testMortgageItem = mortgageItemList.get(mortgageItemList.size() - 1);
        assertThat(testMortgageItem.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testMortgageItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMortgageItem.getItemName()).isEqualTo(DEFAULT_ITEM_NAME);
        assertThat(testMortgageItem.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createMortgageItemWithExistingId() throws Exception {
        // Create the MortgageItem with an existing ID
        mortgageItem.setId(1L);
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        int databaseSizeBeforeCreate = mortgageItemRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMortgageItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageItemRepository.findAll().size();
        // set the field null
        mortgageItem.setGroupCode(null);

        // Create the MortgageItem, which fails.
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        restMortgageItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageItemRepository.findAll().size();
        // set the field null
        mortgageItem.setCode(null);

        // Create the MortgageItem, which fails.
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        restMortgageItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkItemNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageItemRepository.findAll().size();
        // set the field null
        mortgageItem.setItemName(null);

        // Create the MortgageItem, which fails.
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        restMortgageItemMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMortgageItems() throws Exception {
        // Initialize the database
        mortgageItemRepository.saveAndFlush(mortgageItem);

        // Get all the mortgageItemList
        restMortgageItemMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mortgageItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].itemName").value(hasItem(DEFAULT_ITEM_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getMortgageItem() throws Exception {
        // Initialize the database
        mortgageItemRepository.saveAndFlush(mortgageItem);

        // Get the mortgageItem
        restMortgageItemMockMvc
            .perform(get(ENTITY_API_URL_ID, mortgageItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mortgageItem.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.itemName").value(DEFAULT_ITEM_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingMortgageItem() throws Exception {
        // Get the mortgageItem
        restMortgageItemMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMortgageItem() throws Exception {
        // Initialize the database
        mortgageItemRepository.saveAndFlush(mortgageItem);

        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();

        // Update the mortgageItem
        MortgageItem updatedMortgageItem = mortgageItemRepository.findById(mortgageItem.getId()).get();
        // Disconnect from session so that the updates on updatedMortgageItem are not directly saved in db
        em.detach(updatedMortgageItem);
        updatedMortgageItem.groupCode(UPDATED_GROUP_CODE).code(UPDATED_CODE).itemName(UPDATED_ITEM_NAME).delFlg(UPDATED_DEL_FLG);
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(updatedMortgageItem);

        restMortgageItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mortgageItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isOk());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
        MortgageItem testMortgageItem = mortgageItemList.get(mortgageItemList.size() - 1);
        assertThat(testMortgageItem.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testMortgageItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMortgageItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testMortgageItem.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingMortgageItem() throws Exception {
        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();
        mortgageItem.setId(count.incrementAndGet());

        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMortgageItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mortgageItemDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMortgageItem() throws Exception {
        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();
        mortgageItem.setId(count.incrementAndGet());

        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageItemMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMortgageItem() throws Exception {
        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();
        mortgageItem.setId(count.incrementAndGet());

        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageItemMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMortgageItemWithPatch() throws Exception {
        // Initialize the database
        mortgageItemRepository.saveAndFlush(mortgageItem);

        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();

        // Update the mortgageItem using partial update
        MortgageItem partialUpdatedMortgageItem = new MortgageItem();
        partialUpdatedMortgageItem.setId(mortgageItem.getId());

        partialUpdatedMortgageItem.groupCode(UPDATED_GROUP_CODE).itemName(UPDATED_ITEM_NAME);

        restMortgageItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMortgageItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMortgageItem))
            )
            .andExpect(status().isOk());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
        MortgageItem testMortgageItem = mortgageItemList.get(mortgageItemList.size() - 1);
        assertThat(testMortgageItem.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testMortgageItem.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMortgageItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testMortgageItem.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateMortgageItemWithPatch() throws Exception {
        // Initialize the database
        mortgageItemRepository.saveAndFlush(mortgageItem);

        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();

        // Update the mortgageItem using partial update
        MortgageItem partialUpdatedMortgageItem = new MortgageItem();
        partialUpdatedMortgageItem.setId(mortgageItem.getId());

        partialUpdatedMortgageItem.groupCode(UPDATED_GROUP_CODE).code(UPDATED_CODE).itemName(UPDATED_ITEM_NAME).delFlg(UPDATED_DEL_FLG);

        restMortgageItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMortgageItem.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMortgageItem))
            )
            .andExpect(status().isOk());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
        MortgageItem testMortgageItem = mortgageItemList.get(mortgageItemList.size() - 1);
        assertThat(testMortgageItem.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testMortgageItem.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMortgageItem.getItemName()).isEqualTo(UPDATED_ITEM_NAME);
        assertThat(testMortgageItem.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingMortgageItem() throws Exception {
        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();
        mortgageItem.setId(count.incrementAndGet());

        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMortgageItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mortgageItemDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMortgageItem() throws Exception {
        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();
        mortgageItem.setId(count.incrementAndGet());

        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageItemMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMortgageItem() throws Exception {
        int databaseSizeBeforeUpdate = mortgageItemRepository.findAll().size();
        mortgageItem.setId(count.incrementAndGet());

        // Create the MortgageItem
        MortgageItemDTO mortgageItemDTO = mortgageItemMapper.toDto(mortgageItem);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageItemMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mortgageItemDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MortgageItem in the database
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMortgageItem() throws Exception {
        // Initialize the database
        mortgageItemRepository.saveAndFlush(mortgageItem);

        int databaseSizeBeforeDelete = mortgageItemRepository.findAll().size();

        // Delete the mortgageItem
        restMortgageItemMockMvc
            .perform(delete(ENTITY_API_URL_ID, mortgageItem.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MortgageItem> mortgageItemList = mortgageItemRepository.findAll();
        assertThat(mortgageItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
