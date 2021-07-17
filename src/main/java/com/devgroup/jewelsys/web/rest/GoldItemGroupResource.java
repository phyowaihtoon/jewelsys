package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.GoldItemGroupRepository;
import com.devgroup.jewelsys.service.GoldItemGroupService;
import com.devgroup.jewelsys.service.dto.GoldItemGroupDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.GoldItemGroup}.
 */
@RestController
@RequestMapping("/api")
public class GoldItemGroupResource {

    private final Logger log = LoggerFactory.getLogger(GoldItemGroupResource.class);

    private static final String ENTITY_NAME = "goldItemGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GoldItemGroupService goldItemGroupService;

    private final GoldItemGroupRepository goldItemGroupRepository;

    public GoldItemGroupResource(GoldItemGroupService goldItemGroupService, GoldItemGroupRepository goldItemGroupRepository) {
        this.goldItemGroupService = goldItemGroupService;
        this.goldItemGroupRepository = goldItemGroupRepository;
    }

    /**
     * {@code POST  /gold-item-groups} : Create a new goldItemGroup.
     *
     * @param goldItemGroupDTO the goldItemGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new goldItemGroupDTO, or with status {@code 400 (Bad Request)} if the goldItemGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/gold-item-groups")
    public ResponseEntity<GoldItemGroupDTO> createGoldItemGroup(@Valid @RequestBody GoldItemGroupDTO goldItemGroupDTO)
        throws URISyntaxException {
        log.debug("REST request to save GoldItemGroup : {}", goldItemGroupDTO);
        if (goldItemGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new goldItemGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GoldItemGroupDTO result = goldItemGroupService.save(goldItemGroupDTO);
        return ResponseEntity
            .created(new URI("/api/gold-item-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /gold-item-groups/:id} : Updates an existing goldItemGroup.
     *
     * @param id the id of the goldItemGroupDTO to save.
     * @param goldItemGroupDTO the goldItemGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldItemGroupDTO,
     * or with status {@code 400 (Bad Request)} if the goldItemGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the goldItemGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/gold-item-groups/{id}")
    public ResponseEntity<GoldItemGroupDTO> updateGoldItemGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody GoldItemGroupDTO goldItemGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to update GoldItemGroup : {}, {}", id, goldItemGroupDTO);
        if (goldItemGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldItemGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldItemGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        GoldItemGroupDTO result = goldItemGroupService.save(goldItemGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldItemGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /gold-item-groups/:id} : Partial updates given fields of an existing goldItemGroup, field will ignore if it is null
     *
     * @param id the id of the goldItemGroupDTO to save.
     * @param goldItemGroupDTO the goldItemGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated goldItemGroupDTO,
     * or with status {@code 400 (Bad Request)} if the goldItemGroupDTO is not valid,
     * or with status {@code 404 (Not Found)} if the goldItemGroupDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the goldItemGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/gold-item-groups/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<GoldItemGroupDTO> partialUpdateGoldItemGroup(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody GoldItemGroupDTO goldItemGroupDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update GoldItemGroup partially : {}, {}", id, goldItemGroupDTO);
        if (goldItemGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, goldItemGroupDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!goldItemGroupRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<GoldItemGroupDTO> result = goldItemGroupService.partialUpdate(goldItemGroupDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, goldItemGroupDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /gold-item-groups} : get all the goldItemGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of goldItemGroups in body.
     */
    @GetMapping("/gold-item-groups")
    public ResponseEntity<List<GoldItemGroupDTO>> getAllGoldItemGroups(Pageable pageable) {
        log.debug("REST request to get a page of GoldItemGroups");
        Page<GoldItemGroupDTO> page = goldItemGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /gold-item-groups/:id} : get the "id" goldItemGroup.
     *
     * @param id the id of the goldItemGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the goldItemGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/gold-item-groups/{id}")
    public ResponseEntity<GoldItemGroupDTO> getGoldItemGroup(@PathVariable Long id) {
        log.debug("REST request to get GoldItemGroup : {}", id);
        Optional<GoldItemGroupDTO> goldItemGroupDTO = goldItemGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(goldItemGroupDTO);
    }

    /**
     * {@code DELETE  /gold-item-groups/:id} : delete the "id" goldItemGroup.
     *
     * @param id the id of the goldItemGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/gold-item-groups/{id}")
    public ResponseEntity<Void> deleteGoldItemGroup(@PathVariable Long id) {
        log.debug("REST request to delete GoldItemGroup : {}", id);
        goldItemGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
