package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.CounterInfo;
import com.devgroup.jewelsys.repository.CounterInfoRepository;
import com.devgroup.jewelsys.service.dto.CounterInfoDTO;
import com.devgroup.jewelsys.service.mapper.CounterInfoMapper;
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
 * Integration tests for the {@link CounterInfoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CounterInfoResourceIT {

    private static final String DEFAULT_COUNTER_NO = "AAAAAAAAAA";
    private static final String UPDATED_COUNTER_NO = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COUNTER_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/counter-infos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CounterInfoRepository counterInfoRepository;

    @Autowired
    private CounterInfoMapper counterInfoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCounterInfoMockMvc;

    private CounterInfo counterInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CounterInfo createEntity(EntityManager em) {
        CounterInfo counterInfo = new CounterInfo().counterNo(DEFAULT_COUNTER_NO).counterName(DEFAULT_COUNTER_NAME).delFlg(DEFAULT_DEL_FLG);
        return counterInfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CounterInfo createUpdatedEntity(EntityManager em) {
        CounterInfo counterInfo = new CounterInfo().counterNo(UPDATED_COUNTER_NO).counterName(UPDATED_COUNTER_NAME).delFlg(UPDATED_DEL_FLG);
        return counterInfo;
    }

    @BeforeEach
    public void initTest() {
        counterInfo = createEntity(em);
    }

    @Test
    @Transactional
    void createCounterInfo() throws Exception {
        int databaseSizeBeforeCreate = counterInfoRepository.findAll().size();
        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);
        restCounterInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeCreate + 1);
        CounterInfo testCounterInfo = counterInfoList.get(counterInfoList.size() - 1);
        assertThat(testCounterInfo.getCounterNo()).isEqualTo(DEFAULT_COUNTER_NO);
        assertThat(testCounterInfo.getCounterName()).isEqualTo(DEFAULT_COUNTER_NAME);
        assertThat(testCounterInfo.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createCounterInfoWithExistingId() throws Exception {
        // Create the CounterInfo with an existing ID
        counterInfo.setId(1L);
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        int databaseSizeBeforeCreate = counterInfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCounterInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCounterNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = counterInfoRepository.findAll().size();
        // set the field null
        counterInfo.setCounterNo(null);

        // Create the CounterInfo, which fails.
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        restCounterInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCounterNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = counterInfoRepository.findAll().size();
        // set the field null
        counterInfo.setCounterName(null);

        // Create the CounterInfo, which fails.
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        restCounterInfoMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCounterInfos() throws Exception {
        // Initialize the database
        counterInfoRepository.saveAndFlush(counterInfo);

        // Get all the counterInfoList
        restCounterInfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(counterInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].counterNo").value(hasItem(DEFAULT_COUNTER_NO)))
            .andExpect(jsonPath("$.[*].counterName").value(hasItem(DEFAULT_COUNTER_NAME)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getCounterInfo() throws Exception {
        // Initialize the database
        counterInfoRepository.saveAndFlush(counterInfo);

        // Get the counterInfo
        restCounterInfoMockMvc
            .perform(get(ENTITY_API_URL_ID, counterInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(counterInfo.getId().intValue()))
            .andExpect(jsonPath("$.counterNo").value(DEFAULT_COUNTER_NO))
            .andExpect(jsonPath("$.counterName").value(DEFAULT_COUNTER_NAME))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingCounterInfo() throws Exception {
        // Get the counterInfo
        restCounterInfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCounterInfo() throws Exception {
        // Initialize the database
        counterInfoRepository.saveAndFlush(counterInfo);

        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();

        // Update the counterInfo
        CounterInfo updatedCounterInfo = counterInfoRepository.findById(counterInfo.getId()).get();
        // Disconnect from session so that the updates on updatedCounterInfo are not directly saved in db
        em.detach(updatedCounterInfo);
        updatedCounterInfo.counterNo(UPDATED_COUNTER_NO).counterName(UPDATED_COUNTER_NAME).delFlg(UPDATED_DEL_FLG);
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(updatedCounterInfo);

        restCounterInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, counterInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
        CounterInfo testCounterInfo = counterInfoList.get(counterInfoList.size() - 1);
        assertThat(testCounterInfo.getCounterNo()).isEqualTo(UPDATED_COUNTER_NO);
        assertThat(testCounterInfo.getCounterName()).isEqualTo(UPDATED_COUNTER_NAME);
        assertThat(testCounterInfo.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingCounterInfo() throws Exception {
        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();
        counterInfo.setId(count.incrementAndGet());

        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCounterInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, counterInfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCounterInfo() throws Exception {
        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();
        counterInfo.setId(count.incrementAndGet());

        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterInfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCounterInfo() throws Exception {
        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();
        counterInfo.setId(count.incrementAndGet());

        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterInfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(counterInfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCounterInfoWithPatch() throws Exception {
        // Initialize the database
        counterInfoRepository.saveAndFlush(counterInfo);

        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();

        // Update the counterInfo using partial update
        CounterInfo partialUpdatedCounterInfo = new CounterInfo();
        partialUpdatedCounterInfo.setId(counterInfo.getId());

        partialUpdatedCounterInfo.counterNo(UPDATED_COUNTER_NO).counterName(UPDATED_COUNTER_NAME).delFlg(UPDATED_DEL_FLG);

        restCounterInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCounterInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCounterInfo))
            )
            .andExpect(status().isOk());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
        CounterInfo testCounterInfo = counterInfoList.get(counterInfoList.size() - 1);
        assertThat(testCounterInfo.getCounterNo()).isEqualTo(UPDATED_COUNTER_NO);
        assertThat(testCounterInfo.getCounterName()).isEqualTo(UPDATED_COUNTER_NAME);
        assertThat(testCounterInfo.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateCounterInfoWithPatch() throws Exception {
        // Initialize the database
        counterInfoRepository.saveAndFlush(counterInfo);

        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();

        // Update the counterInfo using partial update
        CounterInfo partialUpdatedCounterInfo = new CounterInfo();
        partialUpdatedCounterInfo.setId(counterInfo.getId());

        partialUpdatedCounterInfo.counterNo(UPDATED_COUNTER_NO).counterName(UPDATED_COUNTER_NAME).delFlg(UPDATED_DEL_FLG);

        restCounterInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCounterInfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCounterInfo))
            )
            .andExpect(status().isOk());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
        CounterInfo testCounterInfo = counterInfoList.get(counterInfoList.size() - 1);
        assertThat(testCounterInfo.getCounterNo()).isEqualTo(UPDATED_COUNTER_NO);
        assertThat(testCounterInfo.getCounterName()).isEqualTo(UPDATED_COUNTER_NAME);
        assertThat(testCounterInfo.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingCounterInfo() throws Exception {
        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();
        counterInfo.setId(count.incrementAndGet());

        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCounterInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, counterInfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCounterInfo() throws Exception {
        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();
        counterInfo.setId(count.incrementAndGet());

        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterInfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCounterInfo() throws Exception {
        int databaseSizeBeforeUpdate = counterInfoRepository.findAll().size();
        counterInfo.setId(count.incrementAndGet());

        // Create the CounterInfo
        CounterInfoDTO counterInfoDTO = counterInfoMapper.toDto(counterInfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCounterInfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(counterInfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CounterInfo in the database
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCounterInfo() throws Exception {
        // Initialize the database
        counterInfoRepository.saveAndFlush(counterInfo);

        int databaseSizeBeforeDelete = counterInfoRepository.findAll().size();

        // Delete the counterInfo
        restCounterInfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, counterInfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CounterInfo> counterInfoList = counterInfoRepository.findAll();
        assertThat(counterInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
