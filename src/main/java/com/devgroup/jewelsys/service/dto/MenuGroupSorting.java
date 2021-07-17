package com.devgroup.jewelsys.service.dto;

import java.util.Comparator;

public class MenuGroupSorting implements Comparator<MenuAccessDTO> {

    @Override
    public int compare(MenuAccessDTO o1, MenuAccessDTO o2) {
        return o1.getSequenceNo() - o2.getSequenceNo();
    }
}
