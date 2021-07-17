package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.DataCategoryRepository;
import com.devgroup.jewelsys.service.DataCategoryService;
import com.devgroup.jewelsys.service.dto.DataCategoryDTO;
import com.devgroup.jewelsys.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.DataCategory}.
 */
@RestController
@RequestMapping("/api")
public class DataCategoryResource {

    private final Logger log = LoggerFactory.getLogger(DataCategoryResource.class);

    private static final String ENTITY_NAME = "dataCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataCategoryService dataCategoryService;

    private final DataCategoryRepository dataCategoryRepository;

    public DataCategoryResource(DataCategoryService dataCategoryService, DataCategoryRepository dataCategoryRepository) {
        this.dataCategoryService = dataCategoryService;
        this.dataCategoryRepository = dataCategoryRepository;
    }

    /**
     * {@code POST  /data-categories} : Create a new dataCategory.
     *
     * @param dataCategoryDTO the dataCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataCategoryDTO, or with status {@code 400 (Bad Request)} if the dataCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-categories")
    public ResponseEntity<DataCategoryDTO> createDataCategory(@RequestBody DataCategoryDTO dataCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save DataCategory : {}", dataCategoryDTO);
        if (dataCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataCategoryDTO result = dataCategoryService.save(dataCategoryDTO);
        return ResponseEntity
            .created(new URI("/api/data-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-categories/:id} : Updates an existing dataCategory.
     *
     * @param id the id of the dataCategoryDTO to save.
     * @param dataCategoryDTO the dataCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the dataCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-categories/{id}")
    public ResponseEntity<DataCategoryDTO> updateDataCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataCategoryDTO dataCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DataCategory : {}, {}", id, dataCategoryDTO);
        if (dataCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DataCategoryDTO result = dataCategoryService.save(dataCategoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /data-categories/:id} : Partial updates given fields of an existing dataCategory, field will ignore if it is null
     *
     * @param id the id of the dataCategoryDTO to save.
     * @param dataCategoryDTO the dataCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the dataCategoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dataCategoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dataCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/data-categories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DataCategoryDTO> partialUpdateDataCategory(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DataCategoryDTO dataCategoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DataCategory partially : {}, {}", id, dataCategoryDTO);
        if (dataCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dataCategoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dataCategoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DataCategoryDTO> result = dataCategoryService.partialUpdate(dataCategoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataCategoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /data-categories} : get all the dataCategories.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataCategories in body.
     */
    @GetMapping("/data-categories")
    public ResponseEntity<List<DataCategoryDTO>> getAllDataCategories(Pageable pageable) {
        log.debug("REST request to get a page of DataCategories");
        Page<DataCategoryDTO> page = dataCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /data-categories/:id} : get the "id" dataCategory.
     *
     * @param id the id of the dataCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-categories/{id}")
    public ResponseEntity<DataCategoryDTO> getDataCategory(@PathVariable Long id) {
        log.debug("REST request to get DataCategory : {}", id);
        Optional<DataCategoryDTO> dataCategoryDTO = dataCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataCategoryDTO);
    }

    /**
     * {@code DELETE  /data-categories/:id} : delete the "id" dataCategory.
     *
     * @param id the id of the dataCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-categories/{id}")
    public ResponseEntity<Void> deleteDataCategory(@PathVariable Long id) {
        log.debug("REST request to delete DataCategory : {}", id);
        dataCategoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
