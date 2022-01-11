package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.GemsPriceRate;
import com.devgroup.jewelsys.repository.GemsPriceRateRepository;
import com.devgroup.jewelsys.service.dto.GemsPriceRateDTO;
import com.devgroup.jewelsys.service.mapper.GemsPriceRateMapper;
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
 * Integration tests for the {@link GemsPriceRateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class GemsPriceRateResourceIT {

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

    private static final String ENTITY_API_URL = "/api/gems-price-rates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private GemsPriceRateRepository gemsPriceRateRepository;

    @Autowired
    private GemsPriceRateMapper gemsPriceRateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGemsPriceRateMockMvc;

    private GemsPriceRate gemsPriceRate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GemsPriceRate createEntity(EntityManager em) {
        GemsPriceRate gemsPriceRate = new GemsPriceRate()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .rateSrNo(DEFAULT_RATE_SR_NO)
            .rateType(DEFAULT_RATE_TYPE)
            .priceRate(DEFAULT_PRICE_RATE)
            .delFlg(DEFAULT_DEL_FLG);
        return gemsPriceRate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GemsPriceRate createUpdatedEntity(EntityManager em) {
        GemsPriceRate gemsPriceRate = new GemsPriceRate()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .rateSrNo(UPDATED_RATE_SR_NO)
            .rateType(UPDATED_RATE_TYPE)
            .priceRate(UPDATED_PRICE_RATE)
            .delFlg(UPDATED_DEL_FLG);
        return gemsPriceRate;
    }

    @BeforeEach
    public void initTest() {
        gemsPriceRate = createEntity(em);
    }

    @Test
    @Transactional
    void createGemsPriceRate() throws Exception {
        int databaseSizeBeforeCreate = gemsPriceRateRepository.findAll().size();
        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);
        restGemsPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeCreate + 1);
        GemsPriceRate testGemsPriceRate = gemsPriceRateList.get(gemsPriceRateList.size() - 1);
        assertThat(testGemsPriceRate.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testGemsPriceRate.getRateSrNo()).isEqualTo(DEFAULT_RATE_SR_NO);
        assertThat(testGemsPriceRate.getRateType()).isEqualTo(DEFAULT_RATE_TYPE);
        assertThat(testGemsPriceRate.getPriceRate()).isEqualTo(DEFAULT_PRICE_RATE);
        assertThat(testGemsPriceRate.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createGemsPriceRateWithExistingId() throws Exception {
        // Create the GemsPriceRate with an existing ID
        gemsPriceRate.setId(1L);
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        int databaseSizeBeforeCreate = gemsPriceRateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restGemsPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEffectiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsPriceRateRepository.findAll().size();
        // set the field null
        gemsPriceRate.setEffectiveDate(null);

        // Create the GemsPriceRate, which fails.
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        restGemsPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRateSrNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsPriceRateRepository.findAll().size();
        // set the field null
        gemsPriceRate.setRateSrNo(null);

        // Create the GemsPriceRate, which fails.
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        restGemsPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRateTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = gemsPriceRateRepository.findAll().size();
        // set the field null
        gemsPriceRate.setRateType(null);

        // Create the GemsPriceRate, which fails.
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        restGemsPriceRateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllGemsPriceRates() throws Exception {
        // Initialize the database
        gemsPriceRateRepository.saveAndFlush(gemsPriceRate);

        // Get all the gemsPriceRateList
        restGemsPriceRateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(gemsPriceRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].rateSrNo").value(hasItem(DEFAULT_RATE_SR_NO)))
            .andExpect(jsonPath("$.[*].rateType").value(hasItem(DEFAULT_RATE_TYPE)))
            .andExpect(jsonPath("$.[*].priceRate").value(hasItem(DEFAULT_PRICE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getGemsPriceRate() throws Exception {
        // Initialize the database
        gemsPriceRateRepository.saveAndFlush(gemsPriceRate);

        // Get the gemsPriceRate
        restGemsPriceRateMockMvc
            .perform(get(ENTITY_API_URL_ID, gemsPriceRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(gemsPriceRate.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.rateSrNo").value(DEFAULT_RATE_SR_NO))
            .andExpect(jsonPath("$.rateType").value(DEFAULT_RATE_TYPE))
            .andExpect(jsonPath("$.priceRate").value(DEFAULT_PRICE_RATE.doubleValue()))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingGemsPriceRate() throws Exception {
        // Get the gemsPriceRate
        restGemsPriceRateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewGemsPriceRate() throws Exception {
        // Initialize the database
        gemsPriceRateRepository.saveAndFlush(gemsPriceRate);

        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();

        // Update the gemsPriceRate
        GemsPriceRate updatedGemsPriceRate = gemsPriceRateRepository.findById(gemsPriceRate.getId()).get();
        // Disconnect from session so that the updates on updatedGemsPriceRate are not directly saved in db
        em.detach(updatedGemsPriceRate);
        updatedGemsPriceRate
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .rateSrNo(UPDATED_RATE_SR_NO)
            .rateType(UPDATED_RATE_TYPE)
            .priceRate(UPDATED_PRICE_RATE)
            .delFlg(UPDATED_DEL_FLG);
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(updatedGemsPriceRate);

        restGemsPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemsPriceRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isOk());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
        GemsPriceRate testGemsPriceRate = gemsPriceRateList.get(gemsPriceRateList.size() - 1);
        assertThat(testGemsPriceRate.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testGemsPriceRate.getRateSrNo()).isEqualTo(UPDATED_RATE_SR_NO);
        assertThat(testGemsPriceRate.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testGemsPriceRate.getPriceRate()).isEqualTo(UPDATED_PRICE_RATE);
        assertThat(testGemsPriceRate.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingGemsPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();
        gemsPriceRate.setId(count.incrementAndGet());

        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemsPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, gemsPriceRateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchGemsPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();
        gemsPriceRate.setId(count.incrementAndGet());

        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamGemsPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();
        gemsPriceRate.setId(count.incrementAndGet());

        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsPriceRateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateGemsPriceRateWithPatch() throws Exception {
        // Initialize the database
        gemsPriceRateRepository.saveAndFlush(gemsPriceRate);

        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();

        // Update the gemsPriceRate using partial update
        GemsPriceRate partialUpdatedGemsPriceRate = new GemsPriceRate();
        partialUpdatedGemsPriceRate.setId(gemsPriceRate.getId());

        partialUpdatedGemsPriceRate.rateType(UPDATED_RATE_TYPE).priceRate(UPDATED_PRICE_RATE).delFlg(UPDATED_DEL_FLG);

        restGemsPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemsPriceRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGemsPriceRate))
            )
            .andExpect(status().isOk());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
        GemsPriceRate testGemsPriceRate = gemsPriceRateList.get(gemsPriceRateList.size() - 1);
        assertThat(testGemsPriceRate.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testGemsPriceRate.getRateSrNo()).isEqualTo(DEFAULT_RATE_SR_NO);
        assertThat(testGemsPriceRate.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testGemsPriceRate.getPriceRate()).isEqualTo(UPDATED_PRICE_RATE);
        assertThat(testGemsPriceRate.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateGemsPriceRateWithPatch() throws Exception {
        // Initialize the database
        gemsPriceRateRepository.saveAndFlush(gemsPriceRate);

        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();

        // Update the gemsPriceRate using partial update
        GemsPriceRate partialUpdatedGemsPriceRate = new GemsPriceRate();
        partialUpdatedGemsPriceRate.setId(gemsPriceRate.getId());

        partialUpdatedGemsPriceRate
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .rateSrNo(UPDATED_RATE_SR_NO)
            .rateType(UPDATED_RATE_TYPE)
            .priceRate(UPDATED_PRICE_RATE)
            .delFlg(UPDATED_DEL_FLG);

        restGemsPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedGemsPriceRate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedGemsPriceRate))
            )
            .andExpect(status().isOk());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
        GemsPriceRate testGemsPriceRate = gemsPriceRateList.get(gemsPriceRateList.size() - 1);
        assertThat(testGemsPriceRate.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testGemsPriceRate.getRateSrNo()).isEqualTo(UPDATED_RATE_SR_NO);
        assertThat(testGemsPriceRate.getRateType()).isEqualTo(UPDATED_RATE_TYPE);
        assertThat(testGemsPriceRate.getPriceRate()).isEqualTo(UPDATED_PRICE_RATE);
        assertThat(testGemsPriceRate.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingGemsPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();
        gemsPriceRate.setId(count.incrementAndGet());

        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGemsPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, gemsPriceRateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchGemsPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();
        gemsPriceRate.setId(count.incrementAndGet());

        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamGemsPriceRate() throws Exception {
        int databaseSizeBeforeUpdate = gemsPriceRateRepository.findAll().size();
        gemsPriceRate.setId(count.incrementAndGet());

        // Create the GemsPriceRate
        GemsPriceRateDTO gemsPriceRateDTO = gemsPriceRateMapper.toDto(gemsPriceRate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restGemsPriceRateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(gemsPriceRateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the GemsPriceRate in the database
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteGemsPriceRate() throws Exception {
        // Initialize the database
        gemsPriceRateRepository.saveAndFlush(gemsPriceRate);

        int databaseSizeBeforeDelete = gemsPriceRateRepository.findAll().size();

        // Delete the gemsPriceRate
        restGemsPriceRateMockMvc
            .perform(delete(ENTITY_API_URL_ID, gemsPriceRate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GemsPriceRate> gemsPriceRateList = gemsPriceRateRepository.findAll();
        assertThat(gemsPriceRateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
