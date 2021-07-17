package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.GemsPriceRateRepository;
import com.devgroup.jewelsys.service.GemsPriceRateService;
import com.devgroup.jewelsys.service.dto.GemsPriceRateDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.GemsPriceRate}.
 */
@RestController
@RequestMapping("/api")
public class GemsPriceRateResource {

    private final Logger log = LoggerFactory.getLogger(GemsPriceRateResource.class);

    private static final String ENTITY_NAME = "gemsPriceRate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemsPriceRateService gemsPriceRateService;

    private final GemsPriceRateRepository gemsPriceRateRepository;

    public GemsPriceRateResource(GemsPriceRateService gemsPriceRateService, GemsPriceRateRepository gemsPriceRateRepository) {
        this.gemsPriceRateService = gemsPriceRateService;
        this.gemsPriceRateRepository = gemsPriceRateRepository;
    }

    /**
     * {@code POST  /gems-price-rates} : Create a new gemsPriceRate.
     *
     * @param gemsPriceRateDTO the gemsPriceRateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemsPriceRateDTO, or with status {@code 400 (Bad Request)} if the gemsPriceRate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gems-price-rates")
    public ResponseEntity<GemsPriceRateDTO> createGemsPriceRate(@Valid @RequestBody GemsPriceRateDTO gemsPriceRateDTO)
        throws URISyntaxException {
        log.debug("REST request to save GemsPriceRate : {}", gemsPriceRateDTO);
        if (gemsPriceRateDTO.getId() != null) {
            throw new BadRequestAlertException("A new gemsPriceRate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GemsPriceRateDTO result = gemsPriceRateService.save(gemsPriceRateDTO);
        return ResponseEntity
            .created(new URI("/api/gems-price-rates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gems-price-rates/:id} : Updates an existing gemsPriceRate.
     *
     * @param id the id of the gemsPriceRateDTO to save.
     * @param gemsPriceRateDTO the gemsPriceRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemsPriceRateDTO,
     * or with status {@code 400 (Bad Request)} if the gemsPriceRateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gemsPriceRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gems-price-rates/{id}")
    public ResponseEntity<GemsPriceRateDTO> updateGemsPriceRate(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GemsPriceRateDTO gemsPriceRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GemsPriceRate : {}, {}", id, gemsPriceRateDTO);
        if (gemsPriceRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemsPriceRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemsPriceRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GemsPriceRateDTO result = gemsPriceRateService.save(gemsPriceRateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gemsPriceRateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gems-price-rates/:id} : Partial updates given fields of an existing gemsPriceRate, field will ignore if it is null
     *
     * @param id the id of the gemsPriceRateDTO to save.
     * @param gemsPriceRateDTO the gemsPriceRateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemsPriceRateDTO,
     * or with status {@code 400 (Bad Request)} if the gemsPriceRateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gemsPriceRateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gemsPriceRateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gems-price-rates/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GemsPriceRateDTO> partialUpdateGemsPriceRate(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GemsPriceRateDTO gemsPriceRateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GemsPriceRate partially : {}, {}", id, gemsPriceRateDTO);
        if (gemsPriceRateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemsPriceRateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemsPriceRateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GemsPriceRateDTO> result = gemsPriceRateService.partialUpdate(gemsPriceRateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gemsPriceRateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gems-price-rates} : get all the gemsPriceRates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemsPriceRates in body.
     */
    @GetMapping("/gems-price-rates")
    public ResponseEntity<List<GemsPriceRateDTO>> getAllGemsPriceRates(Pageable pageable) {
        log.debug("REST request to get a page of GemsPriceRates");
        Page<GemsPriceRateDTO> page = gemsPriceRateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gems-price-rates/:id} : get the "id" gemsPriceRate.
     *
     * @param id the id of the gemsPriceRateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemsPriceRateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gems-price-rates/{id}")
    public ResponseEntity<GemsPriceRateDTO> getGemsPriceRate(@PathVariable Long id) {
        log.debug("REST request to get GemsPriceRate : {}", id);
        Optional<GemsPriceRateDTO> gemsPriceRateDTO = gemsPriceRateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gemsPriceRateDTO);
    }

    /**
     * {@code DELETE  /gems-price-rates/:id} : delete the "id" gemsPriceRate.
     *
     * @param id the id of the gemsPriceRateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gems-price-rates/{id}")
    public ResponseEntity<Void> deleteGemsPriceRate(@PathVariable Long id) {
        log.debug("REST request to delete GemsPriceRate : {}", id);
        gemsPriceRateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
