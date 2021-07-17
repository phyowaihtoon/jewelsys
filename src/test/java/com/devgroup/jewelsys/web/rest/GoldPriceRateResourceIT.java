package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GoldPriceRate;
import com.devgroup.jewelsys.repository.GoldPriceRateRepository;
import com.devgroup.jewelsys.service.dto.GoldPriceRateDTO;
import com.devgroup.jewelsys.service.mapper.GoldPriceRateMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link GoldPriceRateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GoldPriceRateResourceIT {

    private static final Instant DEFAULT_EFFECTIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFFECTIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_RATE_SR_NO = 1;
    private static final Integer UPDATED_RATE_SR_NO = 2;

    private static final String DEFAULT_RATE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RATE_TYPE = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE_RATE = 1D;
    private static final Double UPDATED_PRICE_RATE = 2D;

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/gold-price-rates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GoldPriceRateRepository goldPriceRateRepository;

    @Autowired
    private GoldPriceRateMapper goldPriceRateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGoldPriceRateMockMvc;

    private GoldPriceRate goldPriceRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldPriceRate createEntity(EntityManager em) {
        GoldPriceRate goldPriceRate = new GoldPriceRate()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .rateSrNo(DEFAULT_RATE_SR_NO)
            .rateType(DEFAULT_RATE_TYPE)
            .priceRate(DEFAULT_PRICE_RATE)
            .delFlg(DEFAULT_DEL_FLG);
        return goldPriceRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GoldPriceRate createUpdatedEntity(EntityManager em) {
        GoldPriceRate goldPriceRate = new GoldPriceRate()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .rateSrNo(UPDATED_RATE_SR_NO)
            .rateType(UPDATED_RATE_TYPE)
            .priceRate(UPDATED_PRICE_RATE)
            .delFlg(UPDATED_DEL_FLG);
        return goldPriceRate;
    }

    @BeforeEach
    public void initTest() {
        goldPriceRate = createEntity(em);
    }

    @Test
    @Transactional
    void createGoldPriceRate() throws Exception {
        int databaseSizeBeforeCreate = goldPriceRateRepository.findAll().size();
        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);
        restGoldPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeCreate + 1);
        GoldPriceRate testGoldPriceRate = goldPriceRateList.get(goldPriceRateList.size() - 1);
        assertThat(testGoldPriceRate.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testGoldPriceRate.getRateSrNo()).isEqualTo(DEFAULT_RATE_SR_NO);
        assertThat(testGoldPriceRate.getRateType()).isEqualTo(DEFAULT_RATE_TYPE);
        assertThat(testGoldPriceRate.getPriceRate()).isEqualTo(DEFAULT_PRICE_RATE);
        assertThat(testGoldPriceRate.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGoldPriceRateWithExistingId() throws Exception {
        // Create the GoldPriceRate with an existing ID
        goldPriceRate.setId(1L);
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        int databaseSizeBeforeCreate = goldPriceRateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGoldPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffectiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldPriceRateRepository.findAll().size();
        // set the field null
        goldPriceRate.setEffectiveDate(null);

        // Create the GoldPriceRate, which fails.
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        restGoldPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRateSrNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldPriceRateRepository.findAll().size();
        // set the field null
        goldPriceRate.setRateSrNo(null);

        // Create the GoldPriceRate, which fails.
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        restGoldPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRateTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = goldPriceRateRepository.findAll().size();
        // set the field null
        goldPriceRate.setRateType(null);

        // Create the GoldPriceRate, which fails.
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        restGoldPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGoldPriceRates() throws Exception {
        // Initialize the database
        goldPriceRateRepository.saveAndFlush(goldPriceRate);

        // Get all the goldPriceRateList
        restGoldPriceRateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(goldPriceRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].rateSrNo").value(hasItem(DEFAULT_RATE_SR_NO)))
            .andExpect(jsonPath("$.[*].rateType").value(hasItem(DEFAULT_RATE_TYPE)))
            .andExpect(jsonPath("$.[*].priceRate").value(hasItem(DEFAULT_PRICE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGoldPriceRate() throws Exception {
        // Initialize the database
        goldPriceRateRepository.saveAndFlush(goldPriceRate);

        // Get the goldPriceRate
        restGoldPriceRateMockMvc
            .perform(get(ENTITY_API_URL_ID, goldPriceRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(goldPriceRate.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.rateSrNo").value(DEFAULT_RATE_SR_NO))
            .andExpect(jsonPath("$.rateType").value(DEFAULT_RATE_TYPE))
            .andExpect(jsonPath("$.priceRate").value(DEFAULT_PRICE_RATE.doubleValue()))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGoldPriceRate() throws Exception {
        // Get the goldPriceRate
        restGoldPriceRateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGoldPriceRate() throws Exception {
        // Initialize the database
        goldPriceRateRepository.saveAndFlush(goldPriceRate);

        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();

        // Update the goldPriceRate
        GoldPriceRate updatedGoldPriceRate = goldPriceRateRepository.findById(goldPriceRate.getId()).get();
        // Disconnect from session so that the updates on updatedGoldPriceRate are not directly saved in db
        em.detach(updatedGoldPriceRate);
        updatedGoldPriceRate
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .rateSrNo(UPDATED_RATE_SR_NO)
            .rateType(UPDATED_RATE_TYPE)
            .priceRate(UPDATED_PRICE_RATE)
            .delFlg(UPDATED_DEL_FLG);
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(updatedGoldPriceRate);

        restGoldPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldPriceRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isOk());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
        GoldPriceRate testGoldPriceRate = goldPriceRateList.get(goldPriceRateList.size() - 1);
        assertThat(testGoldPriceRate.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testGoldPriceRate.getRateSrNo()).isEqualTo(UPDATED_RATE_SR_NO);
        assertThat(testGoldPriceRate.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testGoldPriceRate.getPriceRate()).isEqualTo(UPDATED_PRICE_RATE);
        assertThat(testGoldPriceRate.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGoldPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();
        goldPriceRate.setId(count.incrementAndGet());

        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, goldPriceRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGoldPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();
        goldPriceRate.setId(count.incrementAndGet());

        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGoldPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();
        goldPriceRate.setId(count.incrementAndGet());

        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGoldPriceRateWithPatch() throws Exception {
        // Initialize the database
        goldPriceRateRepository.saveAndFlush(goldPriceRate);

        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();

        // Update the goldPriceRate using partial update
        GoldPriceRate partialUpdatedGoldPriceRate = new GoldPriceRate();
        partialUpdatedGoldPriceRate.setId(goldPriceRate.getId());

        partialUpdatedGoldPriceRate.rateSrNo(UPDATED_RATE_SR_NO);

        restGoldPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldPriceRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldPriceRate))
            )
            .andExpect(status().isOk());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
        GoldPriceRate testGoldPriceRate = goldPriceRateList.get(goldPriceRateList.size() - 1);
        assertThat(testGoldPriceRate.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testGoldPriceRate.getRateSrNo()).isEqualTo(UPDATED_RATE_SR_NO);
        assertThat(testGoldPriceRate.getRateType()).isEqualTo(DEFAULT_RATE_TYPE);
        assertThat(testGoldPriceRate.getPriceRate()).isEqualTo(DEFAULT_PRICE_RATE);
        assertThat(testGoldPriceRate.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGoldPriceRateWithPatch() throws Exception {
        // Initialize the database
        goldPriceRateRepository.saveAndFlush(goldPriceRate);

        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();

        // Update the goldPriceRate using partial update
        GoldPriceRate partialUpdatedGoldPriceRate = new GoldPriceRate();
        partialUpdatedGoldPriceRate.setId(goldPriceRate.getId());

        partialUpdatedGoldPriceRate
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .rateSrNo(UPDATED_RATE_SR_NO)
            .rateType(UPDATED_RATE_TYPE)
            .priceRate(UPDATED_PRICE_RATE)
            .delFlg(UPDATED_DEL_FLG);

        restGoldPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGoldPriceRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGoldPriceRate))
            )
            .andExpect(status().isOk());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
        GoldPriceRate testGoldPriceRate = goldPriceRateList.get(goldPriceRateList.size() - 1);
        assertThat(testGoldPriceRate.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testGoldPriceRate.getRateSrNo()).isEqualTo(UPDATED_RATE_SR_NO);
        assertThat(testGoldPriceRate.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testGoldPriceRate.getPriceRate()).isEqualTo(UPDATED_PRICE_RATE);
        assertThat(testGoldPriceRate.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGoldPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();
        goldPriceRate.setId(count.incrementAndGet());

        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGoldPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, goldPriceRateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGoldPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();
        goldPriceRate.setId(count.incrementAndGet());

        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGoldPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = goldPriceRateRepository.findAll().size();
        goldPriceRate.setId(count.incrementAndGet());

        // Create the GoldPriceRate
        GoldPriceRateDTO goldPriceRateDTO = goldPriceRateMapper.toDto(goldPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGoldPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(goldPriceRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GoldPriceRate in the database
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGoldPriceRate() throws Exception {
        // Initialize the database
        goldPriceRateRepository.saveAndFlush(goldPriceRate);

        int databaseSizeBeforeDelete = goldPriceRateRepository.findAll().size();

        // Delete the goldPriceRate
        restGoldPriceRateMockMvc
            .perform(delete(ENTITY_API_URL_ID, goldPriceRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GoldPriceRate> goldPriceRateList = goldPriceRateRepository.findAll();
        assertThat(goldPriceRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
