package com.devgroup.jewelsys.domain.enumeration;

/**
 * The MortgageItemGroup enumeration.
 */
public enum MortgageItemGroup {
    G01("လက်စွပ်"),
    G02("လက်ကောက်"),
    G03("ဆွဲကြိုး"),
    G04("နားကပ်"),
    G05("ကလစ်"),
    G06("ဆွဲပြား"),
    G07("ဘယက်"),
    G08("ဟန်းချိန်း"),
    G09("ဖူးချိန်း");

    private final String value;

    MortgageItemGroup(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
