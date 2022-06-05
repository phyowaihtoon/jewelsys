package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.MortgageItemRepository;
import com.devgroup.jewelsys.service.MortgageItemService;
import com.devgroup.jewelsys.service.dto.MortgageItemDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.devgroup.jewelsys.domain.MortgageItem}.
 */
@RestController
@RequestMapping("/api")
public class MortgageItemResource {

    private final Logger log = LoggerFactory.getLogger(MortgageItemResource.class);

    private static final String ENTITY_NAME = "mortgageItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MortgageItemService mortgageItemService;

    private final MortgageItemRepository mortgageItemRepository;

    public MortgageItemResource(MortgageItemService mortgageItemService, MortgageItemRepository mortgageItemRepository) {
        this.mortgageItemService = mortgageItemService;
        this.mortgageItemRepository = mortgageItemRepository;
    }

    /**
     * {@code POST  /mortgage-items} : Create a new mortgageItem.
     *
     * @param mortgageItemDTO the mortgageItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mortgageItemDTO, or with status {@code 400 (Bad Request)} if the mortgageItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mortgage-items")
    public ResponseEntity<MortgageItemDTO> createMortgageItem(@Valid @RequestBody MortgageItemDTO mortgageItemDTO)
        throws URISyntaxException {
        if (mortgageItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new mortgageItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MortgageItemDTO result = mortgageItemService.save(mortgageItemDTO);
        return ResponseEntity
            .created(new URI("/api/mortgage-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mortgage-items/:id} : Updates an existing mortgageItem.
     *
     * @param id the id of the mortgageItemDTO to save.
     * @param mortgageItemDTO the mortgageItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mortgageItemDTO,
     * or with status {@code 400 (Bad Request)} if the mortgageItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mortgageItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mortgage-items/{id}")
    public ResponseEntity<MortgageItemDTO> updateMortgageItem(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody MortgageItemDTO mortgageItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to update MortgageItem : {}, {}", id, mortgageItemDTO);
        if (mortgageItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mortgageItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mortgageItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        MortgageItemDTO result = mortgageItemService.save(mortgageItemDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mortgageItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /mortgage-items/:id} : Partial updates given fields of an existing mortgageItem, field will ignore if it is null
     *
     * @param id the id of the mortgageItemDTO to save.
     * @param mortgageItemDTO the mortgageItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mortgageItemDTO,
     * or with status {@code 400 (Bad Request)} if the mortgageItemDTO is not valid,
     * or with status {@code 404 (Not Found)} if the mortgageItemDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the mortgageItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/mortgage-items/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<MortgageItemDTO> partialUpdateMortgageItem(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody MortgageItemDTO mortgageItemDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update MortgageItem partially : {}, {}", id, mortgageItemDTO);
        if (mortgageItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, mortgageItemDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!mortgageItemRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MortgageItemDTO> result = mortgageItemService.partialUpdate(mortgageItemDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mortgageItemDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /mortgage-items} : get all the mortgageItems.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mortgageItems in body.
     */
    @GetMapping("/mortgage-items")
    public ResponseEntity<List<MortgageItemDTO>> getAllMortgageItems(Pageable pageable) {
        log.debug("REST request to get a page of MortgageItems");
        Page<MortgageItemDTO> page = mortgageItemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /mortgage-items/:id} : get the "id" mortgageItem.
     *
     * @param id the id of the mortgageItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mortgageItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mortgage-items/{id}")
    public ResponseEntity<MortgageItemDTO> getMortgageItem(@PathVariable Long id) {
        log.debug("REST request to get MortgageItem : {}", id);
        Optional<MortgageItemDTO> mortgageItemDTO = mortgageItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(mortgageItemDTO);
    }

    /**
     * {@code DELETE  /mortgage-items/:id} : delete the "id" mortgageItem.
     *
     * @param id the id of the mortgageItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mortgage-items/{id}")
    public ResponseEntity<Void> deleteMortgageItem(@PathVariable Long id) {
        log.debug("REST request to delete MortgageItem : {}", id);
        mortgageItemService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @GetMapping("/mortgage-items/loadall")
    public List<MortgageItemDTO> loadAllMortgageItems() {
        log.debug("REST request to get a page of Mortgage Items");
        List<MortgageItemDTO> itemList = mortgageItemService.loadAll();
        return itemList;
    }
}
