package com.scrumsys.common.constants;

public enum CustomerStatus {

    ALLCREATED("0"),
    TRANSPORTER("1"),
    SHIPPER("2"),
    BOTH("3");

    private String status;

    private CustomerStatus(String status) {
        this.status = status;

    }

    public String getStatus() {
        return status;
    }

}
