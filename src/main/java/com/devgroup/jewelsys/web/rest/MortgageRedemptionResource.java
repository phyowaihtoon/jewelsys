package com.devgroup.jewelsys.web.rest;

import com.devgroup.jewelsys.service.MortgageEntryService;
import com.devgroup.jewelsys.service.MortgageRedemptionService;
import com.devgroup.jewelsys.service.dto.MortgageEntryDTO;
import com.devgroup.jewelsys.service.dto.MortgageRedemptionDTO;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.web.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class MortgageRedemptionResource {

    private final Logger log = LoggerFactory.getLogger(MortgageRedemptionResource.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "mortgageRedemption";

    private final MortgageRedemptionService mrRedemptionService;
    private final MortgageEntryService mortgageEntryService;

    public MortgageRedemptionResource(MortgageRedemptionService mrRedemptionService, MortgageEntryService mortgageEntryService) {
        this.mrRedemptionService = mrRedemptionService;
        this.mortgageEntryService = mortgageEntryService;
    }

    @PostMapping("/mr")
    public ResponseEntity<MortgageRedemptionDTO> createMortgageRedemption(@Valid @RequestBody MortgageRedemptionDTO data)
        throws URISyntaxException {
        log.debug("REST request to save MortgageRedemption : {}", data);
        MortgageRedemptionDTO mrDTO = this.mrRedemptionService.save(data);
        return ResponseEntity
            .created(new URI("/api/mr/" + mrDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, mrDTO.getId().toString()))
            .body(mrDTO);
    }

    @GetMapping("/mr/{mortgageID}")
    public ResponseEntity<MortgageRedemptionDTO> search(@PathVariable String mortgageID) {
        MortgageRedemptionDTO mrDTO = null;
        Optional<MortgageEntryDTO> optional = this.mortgageEntryService.findOneByMortgageID(mortgageID);
        MortgageEntryDTO mortgageEntryDTO = optional.get();
        if (mortgageEntryDTO != null) {
            mrDTO = this.mrRedemptionService.search(mortgageEntryDTO.getId());
            if (mrDTO == null) {
                mrDTO = new MortgageRedemptionDTO();
                mrDTO.setEntryDTO(mortgageEntryDTO);
            } else mrDTO.setEntryDTO(mortgageEntryDTO);
        }
        return ResponseEntity.ok().body(mrDTO);
    }
}
