package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.ShopInfo;
import com.devgroup.jewelsys.repository.ShopInfoRepository;
import com.devgroup.jewelsys.service.dto.ShopInfoDTO;
import com.devgroup.jewelsys.service.mapper.ShopInfoMapper;
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
 * Integration tests for the {@link ShopInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ShopInfoResourceIT {

    private static final String DEFAULT_SHOP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SHOP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_ENG = "AAAAAAAAAA";
    private static final String UPDATED_NAME_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_NAME_MYAN = "AAAAAAAAAA";
    private static final String UPDATED_NAME_MYAN = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_ENG = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_ENG = "BBBBBBBBBB";

    private static final String DEFAULT_ADDR_MYAN = "AAAAAAAAAA";
    private static final String UPDATED_ADDR_MYAN = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/shop-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ShopInfoRepository shopInfoRepository;

    @Autowired
    private ShopInfoMapper shopInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShopInfoMockMvc;

    private ShopInfo shopInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShopInfo createEntity(EntityManager em) {
        ShopInfo shopInfo = new ShopInfo()
            .shopCode(DEFAULT_SHOP_CODE)
            .nameEng(DEFAULT_NAME_ENG)
            .nameMyan(DEFAULT_NAME_MYAN)
            .addrEng(DEFAULT_ADDR_ENG)
            .addrMyan(DEFAULT_ADDR_MYAN)
            .phone(DEFAULT_PHONE)
            .delFlg(DEFAULT_DEL_FLG);
        return shopInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShopInfo createUpdatedEntity(EntityManager em) {
        ShopInfo shopInfo = new ShopInfo()
            .shopCode(UPDATED_SHOP_CODE)
            .nameEng(UPDATED_NAME_ENG)
            .nameMyan(UPDATED_NAME_MYAN)
            .addrEng(UPDATED_ADDR_ENG)
            .addrMyan(UPDATED_ADDR_MYAN)
            .phone(UPDATED_PHONE)
            .delFlg(UPDATED_DEL_FLG);
        return shopInfo;
    }

    @BeforeEach
    public void initTest() {
        shopInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createShopInfo() throws Exception {
        int databaseSizeBeforeCreate = shopInfoRepository.findAll().size();
        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);
        restShopInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeCreate + 1);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getShopCode()).isEqualTo(DEFAULT_SHOP_CODE);
        assertThat(testShopInfo.getNameEng()).isEqualTo(DEFAULT_NAME_ENG);
        assertThat(testShopInfo.getNameMyan()).isEqualTo(DEFAULT_NAME_MYAN);
        assertThat(testShopInfo.getAddrEng()).isEqualTo(DEFAULT_ADDR_ENG);
        assertThat(testShopInfo.getAddrMyan()).isEqualTo(DEFAULT_ADDR_MYAN);
        assertThat(testShopInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testShopInfo.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createShopInfoWithExistingId() throws Exception {
        // Create the ShopInfo with an existing ID
        shopInfo.setId(1L);
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        int databaseSizeBeforeCreate = shopInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restShopInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkShopCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopInfoRepository.findAll().size();
        // set the field null
        shopInfo.setShopCode(null);

        // Create the ShopInfo, which fails.
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        restShopInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameEngIsRequired() throws Exception {
        int databaseSizeBeforeTest = shopInfoRepository.findAll().size();
        // set the field null
        shopInfo.setNameEng(null);

        // Create the ShopInfo, which fails.
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        restShopInfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isBadRequest());

        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllShopInfos() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        // Get all the shopInfoList
        restShopInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shopInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].shopCode").value(hasItem(DEFAULT_SHOP_CODE)))
            .andExpect(jsonPath("$.[*].nameEng").value(hasItem(DEFAULT_NAME_ENG)))
            .andExpect(jsonPath("$.[*].nameMyan").value(hasItem(DEFAULT_NAME_MYAN)))
            .andExpect(jsonPath("$.[*].addrEng").value(hasItem(DEFAULT_ADDR_ENG)))
            .andExpect(jsonPath("$.[*].addrMyan").value(hasItem(DEFAULT_ADDR_MYAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getShopInfo() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        // Get the shopInfo
        restShopInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, shopInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shopInfo.getId().intValue()))
            .andExpect(jsonPath("$.shopCode").value(DEFAULT_SHOP_CODE))
            .andExpect(jsonPath("$.nameEng").value(DEFAULT_NAME_ENG))
            .andExpect(jsonPath("$.nameMyan").value(DEFAULT_NAME_MYAN))
            .andExpect(jsonPath("$.addrEng").value(DEFAULT_ADDR_ENG))
            .andExpect(jsonPath("$.addrMyan").value(DEFAULT_ADDR_MYAN))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingShopInfo() throws Exception {
        // Get the shopInfo
        restShopInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewShopInfo() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();

        // Update the shopInfo
        ShopInfo updatedShopInfo = shopInfoRepository.findById(shopInfo.getId()).get();
        // Disconnect from session so that the updates on updatedShopInfo are not directly saved in db
        em.detach(updatedShopInfo);
        updatedShopInfo
            .shopCode(UPDATED_SHOP_CODE)
            .nameEng(UPDATED_NAME_ENG)
            .nameMyan(UPDATED_NAME_MYAN)
            .addrEng(UPDATED_ADDR_ENG)
            .addrMyan(UPDATED_ADDR_MYAN)
            .phone(UPDATED_PHONE)
            .delFlg(UPDATED_DEL_FLG);
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(updatedShopInfo);

        restShopInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shopInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getShopCode()).isEqualTo(UPDATED_SHOP_CODE);
        assertThat(testShopInfo.getNameEng()).isEqualTo(UPDATED_NAME_ENG);
        assertThat(testShopInfo.getNameMyan()).isEqualTo(UPDATED_NAME_MYAN);
        assertThat(testShopInfo.getAddrEng()).isEqualTo(UPDATED_ADDR_ENG);
        assertThat(testShopInfo.getAddrMyan()).isEqualTo(UPDATED_ADDR_MYAN);
        assertThat(testShopInfo.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShopInfo.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();
        shopInfo.setId(count.incrementAndGet());

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShopInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, shopInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();
        shopInfo.setId(count.incrementAndGet());

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShopInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();
        shopInfo.setId(count.incrementAndGet());

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShopInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(shopInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateShopInfoWithPatch() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();

        // Update the shopInfo using partial update
        ShopInfo partialUpdatedShopInfo = new ShopInfo();
        partialUpdatedShopInfo.setId(shopInfo.getId());

        partialUpdatedShopInfo.shopCode(UPDATED_SHOP_CODE).addrEng(UPDATED_ADDR_ENG).addrMyan(UPDATED_ADDR_MYAN);

        restShopInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShopInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShopInfo))
            )
            .andExpect(status().isOk());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getShopCode()).isEqualTo(UPDATED_SHOP_CODE);
        assertThat(testShopInfo.getNameEng()).isEqualTo(DEFAULT_NAME_ENG);
        assertThat(testShopInfo.getNameMyan()).isEqualTo(DEFAULT_NAME_MYAN);
        assertThat(testShopInfo.getAddrEng()).isEqualTo(UPDATED_ADDR_ENG);
        assertThat(testShopInfo.getAddrMyan()).isEqualTo(UPDATED_ADDR_MYAN);
        assertThat(testShopInfo.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testShopInfo.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateShopInfoWithPatch() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();

        // Update the shopInfo using partial update
        ShopInfo partialUpdatedShopInfo = new ShopInfo();
        partialUpdatedShopInfo.setId(shopInfo.getId());

        partialUpdatedShopInfo
            .shopCode(UPDATED_SHOP_CODE)
            .nameEng(UPDATED_NAME_ENG)
            .nameMyan(UPDATED_NAME_MYAN)
            .addrEng(UPDATED_ADDR_ENG)
            .addrMyan(UPDATED_ADDR_MYAN)
            .phone(UPDATED_PHONE)
            .delFlg(UPDATED_DEL_FLG);

        restShopInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedShopInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedShopInfo))
            )
            .andExpect(status().isOk());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
        ShopInfo testShopInfo = shopInfoList.get(shopInfoList.size() - 1);
        assertThat(testShopInfo.getShopCode()).isEqualTo(UPDATED_SHOP_CODE);
        assertThat(testShopInfo.getNameEng()).isEqualTo(UPDATED_NAME_ENG);
        assertThat(testShopInfo.getNameMyan()).isEqualTo(UPDATED_NAME_MYAN);
        assertThat(testShopInfo.getAddrEng()).isEqualTo(UPDATED_ADDR_ENG);
        assertThat(testShopInfo.getAddrMyan()).isEqualTo(UPDATED_ADDR_MYAN);
        assertThat(testShopInfo.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testShopInfo.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();
        shopInfo.setId(count.incrementAndGet());

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShopInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, shopInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();
        shopInfo.setId(count.incrementAndGet());

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShopInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(shopInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamShopInfo() throws Exception {
        int databaseSizeBeforeUpdate = shopInfoRepository.findAll().size();
        shopInfo.setId(count.incrementAndGet());

        // Create the ShopInfo
        ShopInfoDTO shopInfoDTO = shopInfoMapper.toDto(shopInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restShopInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(shopInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ShopInfo in the database
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteShopInfo() throws Exception {
        // Initialize the database
        shopInfoRepository.saveAndFlush(shopInfo);

        int databaseSizeBeforeDelete = shopInfoRepository.findAll().size();

        // Delete the shopInfo
        restShopInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, shopInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShopInfo> shopInfoList = shopInfoRepository.findAll();
        assertThat(shopInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
