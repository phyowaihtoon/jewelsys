package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.GoldTypeRepository;
import com.devgroup.jewelsys.service.GoldTypeService;
import com.devgroup.jewelsys.service.dto.GoldTypeDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.GoldType}.
 */
@RestController
@RequestMapping("/api")
public class GoldTypeResource {

    private final Logger log = LoggerFactory.getLogger(GoldTypeResource.class);

    private static final String ENTITY_NAME = "goldType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoldTypeService goldTypeService;

    private final GoldTypeRepository goldTypeRepository;

    public GoldTypeResource(GoldTypeService goldTypeService, GoldTypeRepository goldTypeRepository) {
        this.goldTypeService = goldTypeService;
        this.goldTypeRepository = goldTypeRepository;
    }

    /**
     * {@code POST  /gold-types} : Create a new goldType.
     *
     * @param goldTypeDTO the goldTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goldTypeDTO, or with status {@code 400 (Bad Request)} if the goldType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gold-types")
    public ResponseEntity<GoldTypeDTO> createGoldType(@Valid @RequestBody GoldTypeDTO goldTypeDTO) throws URISyntaxException {
        log.debug("REST request to save GoldType : {}", goldTypeDTO);
        if (goldTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new goldType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoldTypeDTO result = goldTypeService.save(goldTypeDTO);
        return ResponseEntity
            .created(new URI("/api/gold-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gold-types/:id} : Updates an existing goldType.
     *
     * @param id the id of the goldTypeDTO to save.
     * @param goldTypeDTO the goldTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldTypeDTO,
     * or with status {@code 400 (Bad Request)} if the goldTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goldTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gold-types/{id}")
    public ResponseEntity<GoldTypeDTO> updateGoldType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GoldTypeDTO goldTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GoldType : {}, {}", id, goldTypeDTO);
        if (goldTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoldTypeDTO result = goldTypeService.save(goldTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gold-types/:id} : Partial updates given fields of an existing goldType, field will ignore if it is null
     *
     * @param id the id of the goldTypeDTO to save.
     * @param goldTypeDTO the goldTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldTypeDTO,
     * or with status {@code 400 (Bad Request)} if the goldTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goldTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goldTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gold-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GoldTypeDTO> partialUpdateGoldType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GoldTypeDTO goldTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoldType partially : {}, {}", id, goldTypeDTO);
        if (goldTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoldTypeDTO> result = goldTypeService.partialUpdate(goldTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gold-types} : get all the goldTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goldTypes in body.
     */
    @GetMapping("/gold-types")
    public ResponseEntity<List<GoldTypeDTO>> getAllGoldTypes(Pageable pageable) {
        log.debug("REST request to get a page of GoldTypes");
        Page<GoldTypeDTO> page = goldTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @GetMapping("/gold-types/loadall")
    public List<GoldTypeDTO> loadAllGoldTypes() {
        log.debug("REST request to get a page of GoldTypes");
        List<GoldTypeDTO> goldTypeList = goldTypeService.loadAll();
        return goldTypeList;
    }

    /**
     * {@code GET  /gold-types/:id} : get the "id" goldType.
     *
     * @param id the id of the goldTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goldTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gold-types/{id}")
    public ResponseEntity<GoldTypeDTO> getGoldType(@PathVariable Long id) {
        log.debug("REST request to get GoldType : {}", id);
        Optional<GoldTypeDTO> goldTypeDTO = goldTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goldTypeDTO);
    }

    /**
     * {@code DELETE  /gold-types/:id} : delete the "id" goldType.
     *
     * @param id the id of the goldTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gold-types/{id}")
    public ResponseEntity<Void> deleteGoldType(@PathVariable Long id) {
        log.debug("REST request to delete GoldType : {}", id);
        goldTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
