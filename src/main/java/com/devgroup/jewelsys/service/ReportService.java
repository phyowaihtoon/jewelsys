package com.devgroup.jewelsys.service;

import com.devgroup.jewelsys.service.dto.RptParamsDTO;
import com.devgroup.partner.domain.PGoldType;
import java.util.List;

public interface ReportService {
    public String printUserList(RptParamsDTO rptPara);

    public List<PGoldType> getAllGoldTypeFrmPS();
}
