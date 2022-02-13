package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.MortgageEntryRepository;
import com.devgroup.jewelsys.service.MortgageEntryService;
import com.devgroup.jewelsys.service.dto.CommonDTO;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.devgroup.jewelsys.domain.MortgageEntry}.
 */
@RestController
@RequestMapping("/api")
public class MortgageEntryResource {

    private final Logger log = LoggerFactory.getLogger(MortgageEntryResource.class);

    private static final String ENTITY_NAME = "mortgageEntry";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MortgageEntryService mortgageEntryService;

    private final MortgageEntryRepository mortgageEntryRepository;

    public MortgageEntryResource(MortgageEntryService mortgageEntryService, MortgageEntryRepository mortgageEntryRepository) {
        this.mortgageEntryService = mortgageEntryService;
        this.mortgageEntryRepository = mortgageEntryRepository;
    }

    /**
     * {@code POST  /mortgage-entries} : Create a new mortgageEntry.
     *
     * @param mortgageEntryDTO the mortgageEntryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mortgageEntryDTO, or with status {@code 400 (Bad Request)} if the mortgageEntry has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mortgage-entries")
    public ResponseEntity<MortgageEntryDTO> createMortgageEntry(@Valid @RequestBody MortgageEntryDTO mortgageEntryDTO)
        throws URISyntaxException {
        log.debug("REST request to save MortgageEntry : {}", mortgageEntryDTO);
        if (mortgageEntryDTO.getId() != null) {
            throw new BadRequestAlertException("A new mortgageEntry cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MortgageEntryDTO result = mortgageEntryService.save(mortgageEntryDTO);
        return ResponseEntity
            .created(new URI("/api/mortgage-entries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mortgage-entries/:id} : Updates an existing mortgageEntry.
     *
     * @param id the id of the mortgageEntryDTO to save.
     * @param mortgageEntryDTO the mortgageEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mortgageEntryDTO,
     * or with status {@code 400 (Bad Request)} if the mortgageEntryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mortgageEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mortgage-entries/{id}")
    public ResponseEntity<MortgageEntryDTO> updateMortgageEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MortgageEntryDTO mortgageEntryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MortgageEntry : {}, {}", id, mortgageEntryDTO);
        if (mortgageEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mortgageEntryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mortgageEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MortgageEntryDTO result = mortgageEntryService.save(mortgageEntryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mortgageEntryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mortgage-entries/:id} : Partial updates given fields of an existing mortgageEntry, field will ignore if it is null
     *
     * @param id the id of the mortgageEntryDTO to save.
     * @param mortgageEntryDTO the mortgageEntryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mortgageEntryDTO,
     * or with status {@code 400 (Bad Request)} if the mortgageEntryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mortgageEntryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mortgageEntryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mortgage-entries/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MortgageEntryDTO> partialUpdateMortgageEntry(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MortgageEntryDTO mortgageEntryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MortgageEntry partially : {}, {}", id, mortgageEntryDTO);
        if (mortgageEntryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mortgageEntryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mortgageEntryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MortgageEntryDTO> result = mortgageEntryService.partialUpdate(mortgageEntryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mortgageEntryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mortgage-entries} : get all the mortgageEntries.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mortgageEntries in body.
     */
    @GetMapping("/mortgage-entries")
    public ResponseEntity<List<MortgageEntryDTO>> getAllMortgageEntries(Pageable pageable) {
        log.debug("REST request to get a page of MortgageEntries");
        CommonDTO commonDTO = mortgageEntryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            commonDTO.getmEntryPage()
        );
        return ResponseEntity.ok().headers(headers).body(commonDTO.getUpdatedMEList());
    }

    /**
     * {@code GET  /mortgage-entries/:id} : get the "id" mortgageEntry.
     *
     * @param id the id of the mortgageEntryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mortgageEntryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mortgage-entries/{id}")
    public ResponseEntity<MortgageEntryDTO> getMortgageEntry(@PathVariable Long id) {
        log.debug("REST request to get MortgageEntry : {}", id);
        Optional<MortgageEntryDTO> mortgageEntryDTO = mortgageEntryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mortgageEntryDTO);
    }

    /**
     * {@code DELETE  /mortgage-entries/:id} : delete the "id" mortgageEntry.
     *
     * @param id the id of the mortgageEntryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mortgage-entries/{id}")
    public ResponseEntity<Void> deleteMortgageEntry(@PathVariable Long id) {
        log.debug("REST request to delete MortgageEntry : {}", id);
        mortgageEntryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
