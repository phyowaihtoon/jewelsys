package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.GoldPriceRateRepository;
import com.devgroup.jewelsys.service.GoldPriceRateService;
import com.devgroup.jewelsys.service.dto.GoldPriceRateDTO;
import com.devgroup.jewelsys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.devgroup.jewelsys.domain.GoldPriceRate}.
 */
@RestController
@RequestMapping("/api")
public class GoldPriceRateResource {

    private final Logger log = LoggerFactory.getLogger(GoldPriceRateResource.class);

    private static final String ENTITY_NAME = "goldPriceRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoldPriceRateService goldPriceRateService;

    private final GoldPriceRateRepository goldPriceRateRepository;

    public GoldPriceRateResource(GoldPriceRateService goldPriceRateService, GoldPriceRateRepository goldPriceRateRepository) {
        this.goldPriceRateService = goldPriceRateService;
        this.goldPriceRateRepository = goldPriceRateRepository;
    }

    /**
     * {@code POST  /gold-price-rates} : Create a new goldPriceRate.
     *
     * @param goldPriceRateDTO the goldPriceRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goldPriceRateDTO, or with status {@code 400 (Bad Request)} if the goldPriceRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gold-price-rates")
    public ResponseEntity<GoldPriceRateDTO> createGoldPriceRate(@Valid @RequestBody GoldPriceRateDTO goldPriceRateDTO)
        throws URISyntaxException {
        log.debug("REST request to save GoldPriceRate : {}", goldPriceRateDTO);
        if (goldPriceRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new goldPriceRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoldPriceRateDTO result = goldPriceRateService.save(goldPriceRateDTO);
        return ResponseEntity
            .created(new URI("/api/gold-price-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gold-price-rates/:id} : Updates an existing goldPriceRate.
     *
     * @param id the id of the goldPriceRateDTO to save.
     * @param goldPriceRateDTO the goldPriceRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldPriceRateDTO,
     * or with status {@code 400 (Bad Request)} if the goldPriceRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goldPriceRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gold-price-rates/{id}")
    public ResponseEntity<GoldPriceRateDTO> updateGoldPriceRate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GoldPriceRateDTO goldPriceRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GoldPriceRate : {}, {}", id, goldPriceRateDTO);
        if (goldPriceRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldPriceRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldPriceRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoldPriceRateDTO result = goldPriceRateService.save(goldPriceRateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldPriceRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gold-price-rates/:id} : Partial updates given fields of an existing goldPriceRate, field will ignore if it is null
     *
     * @param id the id of the goldPriceRateDTO to save.
     * @param goldPriceRateDTO the goldPriceRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldPriceRateDTO,
     * or with status {@code 400 (Bad Request)} if the goldPriceRateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goldPriceRateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goldPriceRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gold-price-rates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GoldPriceRateDTO> partialUpdateGoldPriceRate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GoldPriceRateDTO goldPriceRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoldPriceRate partially : {}, {}", id, goldPriceRateDTO);
        if (goldPriceRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldPriceRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldPriceRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoldPriceRateDTO> result = goldPriceRateService.partialUpdate(goldPriceRateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldPriceRateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gold-price-rates} : get all the goldPriceRates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goldPriceRates in body.
     */
    @GetMapping("/gold-price-rates")
    public ResponseEntity<List<GoldPriceRateDTO>> getAllGoldPriceRates(Pageable pageable) {
        log.debug("REST request to get a page of GoldPriceRates");
        Page<GoldPriceRateDTO> page = goldPriceRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gold-price-rates/:id} : get the "id" goldPriceRate.
     *
     * @param id the id of the goldPriceRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goldPriceRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gold-price-rates/{id}")
    public ResponseEntity<GoldPriceRateDTO> getGoldPriceRate(@PathVariable Long id) {
        log.debug("REST request to get GoldPriceRate : {}", id);
        Optional<GoldPriceRateDTO> goldPriceRateDTO = goldPriceRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goldPriceRateDTO);
    }

    /**
     * {@code DELETE  /gold-price-rates/:id} : delete the "id" goldPriceRate.
     *
     * @param id the id of the goldPriceRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gold-price-rates/{id}")
    public ResponseEntity<Void> deleteGoldPriceRate(@PathVariable Long id) {
        log.debug("REST request to delete GoldPriceRate : {}", id);
        goldPriceRateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
