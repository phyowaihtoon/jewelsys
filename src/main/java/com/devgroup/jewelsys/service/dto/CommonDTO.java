package com.devgroup.jewelsys.service.dto;

import java.util.List;
import org.springframework.data.domain.Page;

public class CommonDTO {

    // Mortgage Entry List
    Page<MortgageEntryDTO> mEntryPage;
    List<MortgageEntryDTO> updatedMEList;

    public Page<MortgageEntryDTO> getmEntryPage() {
        return mEntryPage;
    }

    public void setmEntryPage(Page<MortgageEntryDTO> mEntryPage) {
        this.mEntryPage = mEntryPage;
    }

    public List<MortgageEntryDTO> getUpdatedMEList() {
        return updatedMEList;
    }

    public void setUpdatedMEList(List<MortgageEntryDTO> updatedMEList) {
        this.updatedMEList = updatedMEList;
    }
}
