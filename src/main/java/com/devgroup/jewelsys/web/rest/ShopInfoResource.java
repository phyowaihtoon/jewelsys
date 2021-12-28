package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.repository.ShopInfoRepository;
import com.devgroup.jewelsys.service.ShopInfoService;
import com.devgroup.jewelsys.service.dto.ShopInfoDTO;
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
 * REST controller for managing {@link com.devgroup.jewelsys.domain.ShopInfo}.
 */
@RestController
@RequestMapping("/api")
public class ShopInfoResource {

    private final Logger log = LoggerFactory.getLogger(ShopInfoResource.class);

    private static final String ENTITY_NAME = "shopInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShopInfoService shopInfoService;

    private final ShopInfoRepository shopInfoRepository;

    public ShopInfoResource(ShopInfoService shopInfoService, ShopInfoRepository shopInfoRepository) {
        this.shopInfoService = shopInfoService;
        this.shopInfoRepository = shopInfoRepository;
    }

    /**
     * {@code POST  /shop-infos} : Create a new shopInfo.
     *
     * @param shopInfoDTO the shopInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shopInfoDTO, or with status {@code 400 (Bad Request)} if the shopInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shop-infos")
    public ResponseEntity<ShopInfoDTO> createShopInfo(@Valid @RequestBody ShopInfoDTO shopInfoDTO) throws URISyntaxException {
        log.debug("REST request to save ShopInfo : {}", shopInfoDTO);
        if (shopInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new shopInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShopInfoDTO result = shopInfoService.save(shopInfoDTO);
        return ResponseEntity
            .created(new URI("/api/shop-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shop-infos/:id} : Updates an existing shopInfo.
     *
     * @param id the id of the shopInfoDTO to save.
     * @param shopInfoDTO the shopInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shopInfoDTO,
     * or with status {@code 400 (Bad Request)} if the shopInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shopInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shop-infos/{id}")
    public ResponseEntity<ShopInfoDTO> updateShopInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ShopInfoDTO shopInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ShopInfo : {}, {}", id, shopInfoDTO);
        if (shopInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shopInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shopInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ShopInfoDTO result = shopInfoService.save(shopInfoDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shopInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /shop-infos/:id} : Partial updates given fields of an existing shopInfo, field will ignore if it is null
     *
     * @param id the id of the shopInfoDTO to save.
     * @param shopInfoDTO the shopInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shopInfoDTO,
     * or with status {@code 400 (Bad Request)} if the shopInfoDTO is not valid,
     * or with status {@code 404 (Not Found)} if the shopInfoDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the shopInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/shop-infos/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ShopInfoDTO> partialUpdateShopInfo(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ShopInfoDTO shopInfoDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ShopInfo partially : {}, {}", id, shopInfoDTO);
        if (shopInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, shopInfoDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!shopInfoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ShopInfoDTO> result = shopInfoService.partialUpdate(shopInfoDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shopInfoDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /shop-infos} : get all the shopInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shopInfos in body.
     */
    @GetMapping("/shop-infos")
    public ResponseEntity<List<ShopInfoDTO>> getAllShopInfos(Pageable pageable) {
        log.debug("REST request to get a page of ShopInfos");
        Page<ShopInfoDTO> page = shopInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shop-infos/:id} : get the "id" shopInfo.
     *
     * @param id the id of the shopInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shopInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shop-infos/{id}")
    public ResponseEntity<ShopInfoDTO> getShopInfo(@PathVariable Long id) {
        log.debug("REST request to get ShopInfo : {}", id);
        Optional<ShopInfoDTO> shopInfoDTO = shopInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shopInfoDTO);
    }

    /**
     * {@code DELETE  /shop-infos/:id} : delete the "id" shopInfo.
     *
     * @param id the id of the shopInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shop-infos/{id}")
    public ResponseEntity<Void> deleteShopInfo(@PathVariable Long id) {
        log.debug("REST request to delete ShopInfo : {}", id);
        shopInfoService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
