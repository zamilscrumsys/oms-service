package com.scrumsys.common.constants;

public enum CustomerType {

    TRANSPORTER("TRANSPORTER"),
    SHIPPER("SHIPPER"),
    BOTH("BOTH");

    private String cusType;

    private CustomerType(String cusType) {

        this.cusType = cusType;

    }

    public String getCustomerType() {
        return cusType;
    }


}
