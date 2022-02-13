package com.devgroup.jewelsys.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.devgroup.jewelsys.IntegrationTest;
import com.devgroup.jewelsys.domain.MortgageEntry;
import com.devgroup.jewelsys.domain.enumeration.MortgageDamageType;
import com.devgroup.jewelsys.domain.enumeration.MortgageItemGroup;
import com.devgroup.jewelsys.repository.MortgageEntryRepository;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
import com.devgroup.jewelsys.service.mapper.MortgageEntryMapper;
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
 * Integration tests for the {@link MortgageEntryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MortgageEntryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final MortgageItemGroup DEFAULT_GROUP_CODE = MortgageItemGroup.G01;
    private static final MortgageItemGroup UPDATED_GROUP_CODE = MortgageItemGroup.G02;

    private static final String DEFAULT_ITEM_CODE = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_CODE = "BBBBBBBBBB";

    private static final MortgageDamageType DEFAULT_DAMAGE_TYPE = MortgageDamageType.DT01;
    private static final MortgageDamageType UPDATED_DAMAGE_TYPE = MortgageDamageType.DT02;

    private static final Integer DEFAULT_W_IN_KYAT = 1;
    private static final Integer UPDATED_W_IN_KYAT = 2;

    private static final Integer DEFAULT_W_IN_PAE = 1;
    private static final Integer UPDATED_W_IN_PAE = 2;

    private static final Integer DEFAULT_W_IN_YWAY = 1;
    private static final Integer UPDATED_W_IN_YWAY = 2;

    private static final Double DEFAULT_PRINCIPAL_AMOUNT = 1D;
    private static final Double UPDATED_PRINCIPAL_AMOUNT = 2D;

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_INTEREST_RATE = 1D;
    private static final Double UPDATED_INTEREST_RATE = 2D;

    private static final Integer DEFAULT_TERM = 1;
    private static final Integer UPDATED_TERM = 2;

    private static final String DEFAULT_DEL_FLG = "AAAAAAAAAA";
    private static final String UPDATED_DEL_FLG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/mortgage-entries";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private MortgageEntryRepository mortgageEntryRepository;

    @Autowired
    private MortgageEntryMapper mortgageEntryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMortgageEntryMockMvc;

    private MortgageEntry mortgageEntry;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MortgageEntry createEntity(EntityManager em) {
        MortgageEntry mortgageEntry = new MortgageEntry()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .groupCode(DEFAULT_GROUP_CODE)
            .itemCode(DEFAULT_ITEM_CODE)
            .damageType(DEFAULT_DAMAGE_TYPE)
            .wInKyat(DEFAULT_W_IN_KYAT)
            .wInPae(DEFAULT_W_IN_PAE)
            .wInYway(DEFAULT_W_IN_YWAY)
            .principalAmount(DEFAULT_PRINCIPAL_AMOUNT)
            .startDate(DEFAULT_START_DATE)
            .interestRate(DEFAULT_INTEREST_RATE)
            .term(DEFAULT_TERM)
            .delFlg(DEFAULT_DEL_FLG);
        return mortgageEntry;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MortgageEntry createUpdatedEntity(EntityManager em) {
        MortgageEntry mortgageEntry = new MortgageEntry()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .groupCode(UPDATED_GROUP_CODE)
            .itemCode(UPDATED_ITEM_CODE)
            .damageType(UPDATED_DAMAGE_TYPE)
            .wInKyat(UPDATED_W_IN_KYAT)
            .wInPae(UPDATED_W_IN_PAE)
            .wInYway(UPDATED_W_IN_YWAY)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .term(UPDATED_TERM)
            .delFlg(UPDATED_DEL_FLG);
        return mortgageEntry;
    }

    @BeforeEach
    public void initTest() {
        mortgageEntry = createEntity(em);
    }

    @Test
    @Transactional
    void createMortgageEntry() throws Exception {
        int databaseSizeBeforeCreate = mortgageEntryRepository.findAll().size();
        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);
        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeCreate + 1);
        MortgageEntry testMortgageEntry = mortgageEntryList.get(mortgageEntryList.size() - 1);
        assertThat(testMortgageEntry.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMortgageEntry.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMortgageEntry.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testMortgageEntry.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testMortgageEntry.getItemCode()).isEqualTo(DEFAULT_ITEM_CODE);
        assertThat(testMortgageEntry.getDamageType()).isEqualTo(DEFAULT_DAMAGE_TYPE);
        assertThat(testMortgageEntry.getwInKyat()).isEqualTo(DEFAULT_W_IN_KYAT);
        assertThat(testMortgageEntry.getwInPae()).isEqualTo(DEFAULT_W_IN_PAE);
        assertThat(testMortgageEntry.getwInYway()).isEqualTo(DEFAULT_W_IN_YWAY);
        assertThat(testMortgageEntry.getPrincipalAmount()).isEqualTo(DEFAULT_PRINCIPAL_AMOUNT);
        assertThat(testMortgageEntry.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testMortgageEntry.getInterestRate()).isEqualTo(DEFAULT_INTEREST_RATE);
        assertThat(testMortgageEntry.getTerm()).isEqualTo(DEFAULT_TERM);
        assertThat(testMortgageEntry.getDelFlg()).isEqualTo(DEFAULT_DEL_FLG);
    }

    @Test
    @Transactional
    void createMortgageEntryWithExistingId() throws Exception {
        // Create the MortgageEntry with an existing ID
        mortgageEntry.setId(1L);
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        int databaseSizeBeforeCreate = mortgageEntryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageEntryRepository.findAll().size();
        // set the field null
        mortgageEntry.setName(null);

        // Create the MortgageEntry, which fails.
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAddressIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageEntryRepository.findAll().size();
        // set the field null
        mortgageEntry.setAddress(null);

        // Create the MortgageEntry, which fails.
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageEntryRepository.findAll().size();
        // set the field null
        mortgageEntry.setGroupCode(null);

        // Create the MortgageEntry, which fails.
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkItemCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageEntryRepository.findAll().size();
        // set the field null
        mortgageEntry.setItemCode(null);

        // Create the MortgageEntry, which fails.
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrincipalAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageEntryRepository.findAll().size();
        // set the field null
        mortgageEntry.setPrincipalAmount(null);

        // Create the MortgageEntry, which fails.
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = mortgageEntryRepository.findAll().size();
        // set the field null
        mortgageEntry.setStartDate(null);

        // Create the MortgageEntry, which fails.
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMortgageEntries() throws Exception {
        // Initialize the database
        mortgageEntryRepository.saveAndFlush(mortgageEntry);

        // Get all the mortgageEntryList
        restMortgageEntryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mortgageEntry.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].itemCode").value(hasItem(DEFAULT_ITEM_CODE)))
            .andExpect(jsonPath("$.[*].damageType").value(hasItem(DEFAULT_DAMAGE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].wInKyat").value(hasItem(DEFAULT_W_IN_KYAT)))
            .andExpect(jsonPath("$.[*].wInPae").value(hasItem(DEFAULT_W_IN_PAE)))
            .andExpect(jsonPath("$.[*].wInYway").value(hasItem(DEFAULT_W_IN_YWAY)))
            .andExpect(jsonPath("$.[*].principalAmount").value(hasItem(DEFAULT_PRINCIPAL_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].interestRate").value(hasItem(DEFAULT_INTEREST_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].term").value(hasItem(DEFAULT_TERM)))
            .andExpect(jsonPath("$.[*].delFlg").value(hasItem(DEFAULT_DEL_FLG)));
    }

    @Test
    @Transactional
    void getMortgageEntry() throws Exception {
        // Initialize the database
        mortgageEntryRepository.saveAndFlush(mortgageEntry);

        // Get the mortgageEntry
        restMortgageEntryMockMvc
            .perform(get(ENTITY_API_URL_ID, mortgageEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mortgageEntry.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.itemCode").value(DEFAULT_ITEM_CODE))
            .andExpect(jsonPath("$.damageType").value(DEFAULT_DAMAGE_TYPE.toString()))
            .andExpect(jsonPath("$.wInKyat").value(DEFAULT_W_IN_KYAT))
            .andExpect(jsonPath("$.wInPae").value(DEFAULT_W_IN_PAE))
            .andExpect(jsonPath("$.wInYway").value(DEFAULT_W_IN_YWAY))
            .andExpect(jsonPath("$.principalAmount").value(DEFAULT_PRINCIPAL_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.interestRate").value(DEFAULT_INTEREST_RATE.doubleValue()))
            .andExpect(jsonPath("$.term").value(DEFAULT_TERM))
            .andExpect(jsonPath("$.delFlg").value(DEFAULT_DEL_FLG));
    }

    @Test
    @Transactional
    void getNonExistingMortgageEntry() throws Exception {
        // Get the mortgageEntry
        restMortgageEntryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewMortgageEntry() throws Exception {
        // Initialize the database
        mortgageEntryRepository.saveAndFlush(mortgageEntry);

        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();

        // Update the mortgageEntry
        MortgageEntry updatedMortgageEntry = mortgageEntryRepository.findById(mortgageEntry.getId()).get();
        // Disconnect from session so that the updates on updatedMortgageEntry are not directly saved in db
        em.detach(updatedMortgageEntry);
        updatedMortgageEntry
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .groupCode(UPDATED_GROUP_CODE)
            .itemCode(UPDATED_ITEM_CODE)
            .damageType(UPDATED_DAMAGE_TYPE)
            .wInKyat(UPDATED_W_IN_KYAT)
            .wInPae(UPDATED_W_IN_PAE)
            .wInYway(UPDATED_W_IN_YWAY)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .term(UPDATED_TERM)
            .delFlg(UPDATED_DEL_FLG);
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(updatedMortgageEntry);

        restMortgageEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mortgageEntryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isOk());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
        MortgageEntry testMortgageEntry = mortgageEntryList.get(mortgageEntryList.size() - 1);
        assertThat(testMortgageEntry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMortgageEntry.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMortgageEntry.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMortgageEntry.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testMortgageEntry.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testMortgageEntry.getDamageType()).isEqualTo(UPDATED_DAMAGE_TYPE);
        assertThat(testMortgageEntry.getwInKyat()).isEqualTo(UPDATED_W_IN_KYAT);
        assertThat(testMortgageEntry.getwInPae()).isEqualTo(UPDATED_W_IN_PAE);
        assertThat(testMortgageEntry.getwInYway()).isEqualTo(UPDATED_W_IN_YWAY);
        assertThat(testMortgageEntry.getPrincipalAmount()).isEqualTo(UPDATED_PRINCIPAL_AMOUNT);
        assertThat(testMortgageEntry.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMortgageEntry.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMortgageEntry.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testMortgageEntry.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void putNonExistingMortgageEntry() throws Exception {
        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();
        mortgageEntry.setId(count.incrementAndGet());

        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMortgageEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, mortgageEntryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMortgageEntry() throws Exception {
        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();
        mortgageEntry.setId(count.incrementAndGet());

        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageEntryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMortgageEntry() throws Exception {
        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();
        mortgageEntry.setId(count.incrementAndGet());

        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageEntryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMortgageEntryWithPatch() throws Exception {
        // Initialize the database
        mortgageEntryRepository.saveAndFlush(mortgageEntry);

        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();

        // Update the mortgageEntry using partial update
        MortgageEntry partialUpdatedMortgageEntry = new MortgageEntry();
        partialUpdatedMortgageEntry.setId(mortgageEntry.getId());

        partialUpdatedMortgageEntry
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .groupCode(UPDATED_GROUP_CODE)
            .itemCode(UPDATED_ITEM_CODE)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .term(UPDATED_TERM)
            .delFlg(UPDATED_DEL_FLG);

        restMortgageEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMortgageEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMortgageEntry))
            )
            .andExpect(status().isOk());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
        MortgageEntry testMortgageEntry = mortgageEntryList.get(mortgageEntryList.size() - 1);
        assertThat(testMortgageEntry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMortgageEntry.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMortgageEntry.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMortgageEntry.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testMortgageEntry.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testMortgageEntry.getDamageType()).isEqualTo(DEFAULT_DAMAGE_TYPE);
        assertThat(testMortgageEntry.getwInKyat()).isEqualTo(DEFAULT_W_IN_KYAT);
        assertThat(testMortgageEntry.getwInPae()).isEqualTo(DEFAULT_W_IN_PAE);
        assertThat(testMortgageEntry.getwInYway()).isEqualTo(DEFAULT_W_IN_YWAY);
        assertThat(testMortgageEntry.getPrincipalAmount()).isEqualTo(UPDATED_PRINCIPAL_AMOUNT);
        assertThat(testMortgageEntry.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMortgageEntry.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMortgageEntry.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testMortgageEntry.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void fullUpdateMortgageEntryWithPatch() throws Exception {
        // Initialize the database
        mortgageEntryRepository.saveAndFlush(mortgageEntry);

        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();

        // Update the mortgageEntry using partial update
        MortgageEntry partialUpdatedMortgageEntry = new MortgageEntry();
        partialUpdatedMortgageEntry.setId(mortgageEntry.getId());

        partialUpdatedMortgageEntry
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .groupCode(UPDATED_GROUP_CODE)
            .itemCode(UPDATED_ITEM_CODE)
            .damageType(UPDATED_DAMAGE_TYPE)
            .wInKyat(UPDATED_W_IN_KYAT)
            .wInPae(UPDATED_W_IN_PAE)
            .wInYway(UPDATED_W_IN_YWAY)
            .principalAmount(UPDATED_PRINCIPAL_AMOUNT)
            .startDate(UPDATED_START_DATE)
            .interestRate(UPDATED_INTEREST_RATE)
            .term(UPDATED_TERM)
            .delFlg(UPDATED_DEL_FLG);

        restMortgageEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMortgageEntry.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedMortgageEntry))
            )
            .andExpect(status().isOk());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
        MortgageEntry testMortgageEntry = mortgageEntryList.get(mortgageEntryList.size() - 1);
        assertThat(testMortgageEntry.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMortgageEntry.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMortgageEntry.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testMortgageEntry.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testMortgageEntry.getItemCode()).isEqualTo(UPDATED_ITEM_CODE);
        assertThat(testMortgageEntry.getDamageType()).isEqualTo(UPDATED_DAMAGE_TYPE);
        assertThat(testMortgageEntry.getwInKyat()).isEqualTo(UPDATED_W_IN_KYAT);
        assertThat(testMortgageEntry.getwInPae()).isEqualTo(UPDATED_W_IN_PAE);
        assertThat(testMortgageEntry.getwInYway()).isEqualTo(UPDATED_W_IN_YWAY);
        assertThat(testMortgageEntry.getPrincipalAmount()).isEqualTo(UPDATED_PRINCIPAL_AMOUNT);
        assertThat(testMortgageEntry.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testMortgageEntry.getInterestRate()).isEqualTo(UPDATED_INTEREST_RATE);
        assertThat(testMortgageEntry.getTerm()).isEqualTo(UPDATED_TERM);
        assertThat(testMortgageEntry.getDelFlg()).isEqualTo(UPDATED_DEL_FLG);
    }

    @Test
    @Transactional
    void patchNonExistingMortgageEntry() throws Exception {
        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();
        mortgageEntry.setId(count.incrementAndGet());

        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMortgageEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, mortgageEntryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMortgageEntry() throws Exception {
        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();
        mortgageEntry.setId(count.incrementAndGet());

        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageEntryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMortgageEntry() throws Exception {
        int databaseSizeBeforeUpdate = mortgageEntryRepository.findAll().size();
        mortgageEntry.setId(count.incrementAndGet());

        // Create the MortgageEntry
        MortgageEntryDTO mortgageEntryDTO = mortgageEntryMapper.toDto(mortgageEntry);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMortgageEntryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(mortgageEntryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the MortgageEntry in the database
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMortgageEntry() throws Exception {
        // Initialize the database
        mortgageEntryRepository.saveAndFlush(mortgageEntry);

        int databaseSizeBeforeDelete = mortgageEntryRepository.findAll().size();

        // Delete the mortgageEntry
        restMortgageEntryMockMvc
            .perform(delete(ENTITY_API_URL_ID, mortgageEntry.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MortgageEntry> mortgageEntryList = mortgageEntryRepository.findAll();
        assertThat(mortgageEntryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
