package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.CounterInfoRepository;
import com.devgroup.jewelsys.service.CounterInfoService;
import com.devgroup.jewelsys.service.dto.CounterInfoDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.CounterInfo}.
 */
@RestController
@RequestMapping("/api")
public class CounterInfoResource {

    private final Logger log = LoggerFactory.getLogger(CounterInfoResource.class);

    private static final String ENTITY_NAME = "counterInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CounterInfoService counterInfoService;

    private final CounterInfoRepository counterInfoRepository;

    public CounterInfoResource(CounterInfoService counterInfoService, CounterInfoRepository counterInfoRepository) {
        this.counterInfoService = counterInfoService;
        this.counterInfoRepository = counterInfoRepository;
    }

    /**
     * {@code POST  /counter-infos} : Create a new counterInfo.
     *
     * @param counterInfoDTO the counterInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new counterInfoDTO, or with status {@code 400 (Bad Request)} if the counterInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/counter-infos")
    public ResponseEntity<CounterInfoDTO> createCounterInfo(@Valid @RequestBody CounterInfoDTO counterInfoDTO) throws URISyntaxException {
        log.debug("REST request to save CounterInfo : {}", counterInfoDTO);
        if (counterInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new counterInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CounterInfoDTO result = counterInfoService.save(counterInfoDTO);
        return ResponseEntity
            .created(new URI("/api/counter-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /counter-infos/:id} : Updates an existing counterInfo.
     *
     * @param id the id of the counterInfoDTO to save.
     * @param counterInfoDTO the counterInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated counterInfoDTO,
     * or with status {@code 400 (Bad Request)} if the counterInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the counterInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/counter-infos/{id}")
    public ResponseEntity<CounterInfoDTO> updateCounterInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CounterInfoDTO counterInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CounterInfo : {}, {}", id, counterInfoDTO);
        if (counterInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, counterInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!counterInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CounterInfoDTO result = counterInfoService.save(counterInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, counterInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /counter-infos/:id} : Partial updates given fields of an existing counterInfo, field will ignore if it is null
     *
     * @param id the id of the counterInfoDTO to save.
     * @param counterInfoDTO the counterInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated counterInfoDTO,
     * or with status {@code 400 (Bad Request)} if the counterInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the counterInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the counterInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/counter-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CounterInfoDTO> partialUpdateCounterInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CounterInfoDTO counterInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CounterInfo partially : {}, {}", id, counterInfoDTO);
        if (counterInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, counterInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!counterInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CounterInfoDTO> result = counterInfoService.partialUpdate(counterInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, counterInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /counter-infos} : get all the counterInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of counterInfos in body.
     */
    @GetMapping("/counter-infos")
    public ResponseEntity<List<CounterInfoDTO>> getAllCounterInfos(Pageable pageable) {
        log.debug("REST request to get a page of CounterInfos");
        Page<CounterInfoDTO> page = counterInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /counter-infos/:id} : get the "id" counterInfo.
     *
     * @param id the id of the counterInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the counterInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/counter-infos/{id}")
    public ResponseEntity<CounterInfoDTO> getCounterInfo(@PathVariable Long id) {
        log.debug("REST request to get CounterInfo : {}", id);
        Optional<CounterInfoDTO> counterInfoDTO = counterInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(counterInfoDTO);
    }

    /**
     * {@code DELETE  /counter-infos/:id} : delete the "id" counterInfo.
     *
     * @param id the id of the counterInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/counter-infos/{id}")
    public ResponseEntity<Void> deleteCounterInfo(@PathVariable Long id) {
        log.debug("REST request to delete CounterInfo : {}", id);
        counterInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
