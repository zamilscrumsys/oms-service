package com.scrumsys.common.constants;

public enum PermitType {
    ALLINDIAPERMIT("ALLINDIAPERMIT"),
    NATIONALPERMIT("NATIONALPERMIT");

    private String permitTypeName;

    private PermitType(String permitTypeName) {
        this.permitTypeName = permitTypeName;
    }

    public String getPermit() {
        return permitTypeName;
    }

}
