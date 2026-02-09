package com.scrumsys.common.constants;

public enum BiddingStatus {

    SUBMITTED("SUBMITTED"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");


    private String biddingStatus;

    private BiddingStatus(String biddingStatus) {
        this.biddingStatus = biddingStatus;
    }

    public String getBiddingStatus() {
        return biddingStatus;
    }

}
