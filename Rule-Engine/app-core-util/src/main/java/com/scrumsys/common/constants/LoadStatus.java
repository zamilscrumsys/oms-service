package com.scrumsys.common.constants;

public enum LoadStatus {

    NEW("NEW"),
    POST("POST"),
    BIDDINGACCEPTED("BIDDINGACCEPTED"),
    CANCELLED("CANCELLED");


    private String postLoadStatus;

    private LoadStatus(String postLoadStatus) {
        this.postLoadStatus = postLoadStatus;
    }

    public String getLoadStatus() {
        return postLoadStatus;
    }

}
