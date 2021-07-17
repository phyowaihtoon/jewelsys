package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.GemsTypeRepository;
import com.devgroup.jewelsys.service.GemsTypeService;
import com.devgroup.jewelsys.service.dto.GemsTypeDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.GemsType}.
 */
@RestController
@RequestMapping("/api")
public class GemsTypeResource {

    private final Logger log = LoggerFactory.getLogger(GemsTypeResource.class);

    private static final String ENTITY_NAME = "gemsType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemsTypeService gemsTypeService;

    private final GemsTypeRepository gemsTypeRepository;

    public GemsTypeResource(GemsTypeService gemsTypeService, GemsTypeRepository gemsTypeRepository) {
        this.gemsTypeService = gemsTypeService;
        this.gemsTypeRepository = gemsTypeRepository;
    }

    /**
     * {@code POST  /gems-types} : Create a new gemsType.
     *
     * @param gemsTypeDTO the gemsTypeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemsTypeDTO, or with status {@code 400 (Bad Request)} if the gemsType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gems-types")
    public ResponseEntity<GemsTypeDTO> createGemsType(@Valid @RequestBody GemsTypeDTO gemsTypeDTO) throws URISyntaxException {
        log.debug("REST request to save GemsType : {}", gemsTypeDTO);
        if (gemsTypeDTO.getId() != null) {
            throw new BadRequestAlertException("A new gemsType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GemsTypeDTO result = gemsTypeService.save(gemsTypeDTO);
        return ResponseEntity
            .created(new URI("/api/gems-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gems-types/:id} : Updates an existing gemsType.
     *
     * @param id the id of the gemsTypeDTO to save.
     * @param gemsTypeDTO the gemsTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemsTypeDTO,
     * or with status {@code 400 (Bad Request)} if the gemsTypeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gemsTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gems-types/{id}")
    public ResponseEntity<GemsTypeDTO> updateGemsType(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GemsTypeDTO gemsTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GemsType : {}, {}", id, gemsTypeDTO);
        if (gemsTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemsTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemsTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GemsTypeDTO result = gemsTypeService.save(gemsTypeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gemsTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gems-types/:id} : Partial updates given fields of an existing gemsType, field will ignore if it is null
     *
     * @param id the id of the gemsTypeDTO to save.
     * @param gemsTypeDTO the gemsTypeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemsTypeDTO,
     * or with status {@code 400 (Bad Request)} if the gemsTypeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gemsTypeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gemsTypeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gems-types/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GemsTypeDTO> partialUpdateGemsType(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GemsTypeDTO gemsTypeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GemsType partially : {}, {}", id, gemsTypeDTO);
        if (gemsTypeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemsTypeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemsTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GemsTypeDTO> result = gemsTypeService.partialUpdate(gemsTypeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gemsTypeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gems-types} : get all the gemsTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemsTypes in body.
     */
    @GetMapping("/gems-types")
    public ResponseEntity<List<GemsTypeDTO>> getAllGemsTypes(Pageable pageable) {
        log.debug("REST request to get a page of GemsTypes");
        Page<GemsTypeDTO> page = gemsTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gems-types/:id} : get the "id" gemsType.
     *
     * @param id the id of the gemsTypeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemsTypeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gems-types/{id}")
    public ResponseEntity<GemsTypeDTO> getGemsType(@PathVariable Long id) {
        log.debug("REST request to get GemsType : {}", id);
        Optional<GemsTypeDTO> gemsTypeDTO = gemsTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gemsTypeDTO);
    }

    /**
     * {@code DELETE  /gems-types/:id} : delete the "id" gemsType.
     *
     * @param id the id of the gemsTypeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gems-types/{id}")
    public ResponseEntity<Void> deleteGemsType(@PathVariable Long id) {
        log.debug("REST request to delete GemsType : {}", id);
        gemsTypeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
