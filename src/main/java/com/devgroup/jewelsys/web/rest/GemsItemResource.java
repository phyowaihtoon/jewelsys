package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.GemsItemRepository;
import com.devgroup.jewelsys.service.GemsItemService;
import com.devgroup.jewelsys.service.dto.GemsItemDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.GemsItem}.
 */
@RestController
@RequestMapping("/api")
public class GemsItemResource {

    private final Logger log = LoggerFactory.getLogger(GemsItemResource.class);

    private static final String ENTITY_NAME = "gemsItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GemsItemService gemsItemService;

    private final GemsItemRepository gemsItemRepository;

    public GemsItemResource(GemsItemService gemsItemService, GemsItemRepository gemsItemRepository) {
        this.gemsItemService = gemsItemService;
        this.gemsItemRepository = gemsItemRepository;
    }

    /**
     * {@code POST  /gems-items} : Create a new gemsItem.
     *
     * @param gemsItemDTO the gemsItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new gemsItemDTO, or with status {@code 400 (Bad Request)} if the gemsItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gems-items")
    public ResponseEntity<GemsItemDTO> createGemsItem(@Valid @RequestBody GemsItemDTO gemsItemDTO) throws URISyntaxException {
        log.debug("REST request to save GemsItem : {}", gemsItemDTO);
        if (gemsItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new gemsItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GemsItemDTO result = gemsItemService.save(gemsItemDTO);
        return ResponseEntity
            .created(new URI("/api/gems-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gems-items/:id} : Updates an existing gemsItem.
     *
     * @param id the id of the gemsItemDTO to save.
     * @param gemsItemDTO the gemsItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemsItemDTO,
     * or with status {@code 400 (Bad Request)} if the gemsItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the gemsItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gems-items/{id}")
    public ResponseEntity<GemsItemDTO> updateGemsItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GemsItemDTO gemsItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GemsItem : {}, {}", id, gemsItemDTO);
        if (gemsItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemsItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemsItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GemsItemDTO result = gemsItemService.save(gemsItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gemsItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gems-items/:id} : Partial updates given fields of an existing gemsItem, field will ignore if it is null
     *
     * @param id the id of the gemsItemDTO to save.
     * @param gemsItemDTO the gemsItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated gemsItemDTO,
     * or with status {@code 400 (Bad Request)} if the gemsItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the gemsItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the gemsItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gems-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GemsItemDTO> partialUpdateGemsItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GemsItemDTO gemsItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GemsItem partially : {}, {}", id, gemsItemDTO);
        if (gemsItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, gemsItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!gemsItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GemsItemDTO> result = gemsItemService.partialUpdate(gemsItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, gemsItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gems-items} : get all the gemsItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of gemsItems in body.
     */
    @GetMapping("/gems-items")
    public ResponseEntity<List<GemsItemDTO>> getAllGemsItems(Pageable pageable) {
        log.debug("REST request to get a page of GemsItems");
        Page<GemsItemDTO> page = gemsItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gems-items/:id} : get the "id" gemsItem.
     *
     * @param id the id of the gemsItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the gemsItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gems-items/{id}")
    public ResponseEntity<GemsItemDTO> getGemsItem(@PathVariable Long id) {
        log.debug("REST request to get GemsItem : {}", id);
        Optional<GemsItemDTO> gemsItemDTO = gemsItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(gemsItemDTO);
    }

    /**
     * {@code DELETE  /gems-items/:id} : delete the "id" gemsItem.
     *
     * @param id the id of the gemsItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gems-items/{id}")
    public ResponseEntity<Void> deleteGemsItem(@PathVariable Long id) {
        log.debug("REST request to delete GemsItem : {}", id);
        gemsItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
