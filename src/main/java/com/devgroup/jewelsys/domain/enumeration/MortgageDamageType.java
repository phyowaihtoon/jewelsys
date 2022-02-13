package com.devgroup.jewelsys.domain.enumeration;

/**
 * The MortgageDamageType enumeration.
 */
public enum MortgageDamageType {
    DT01("ပိန်"),
    DT02("ပြတ်"),
    DT03("ကျိုး"),
    DT04("ပေါက်"),
    DT05("ကျောက်ထွက်");

    private final String value;

    MortgageDamageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
