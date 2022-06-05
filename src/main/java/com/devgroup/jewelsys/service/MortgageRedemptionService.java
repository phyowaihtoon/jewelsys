package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.MortgageRedemptionDTO;

public interface MortgageRedemptionService {
    MortgageRedemptionDTO save(MortgageRedemptionDTO mrRedemptionDTO);
    MortgageRedemptionDTO search(Long mortgageID);
}
