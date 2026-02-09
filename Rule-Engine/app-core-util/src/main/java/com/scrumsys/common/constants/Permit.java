package com.scrumsys.common.constants;

public enum Permit {
    ALLINDIA("AL"),
    ANDHRAPRADESH("AP"),
    ARUNACHALPRADESH("AR"),
    ASSAM("AS"),
    BIHAR("BR"),
    TAMILNADU("TN"),
    KERALA("KL"),
    CHHATTISGARH("CG"),
    GOA("GA"),
    GUJARAT("GJ"),
    HARYANA("HR"),
    HIMACHALPRADESH("HP"),
    JAMMUANDKASHMIR("JK"),
    JHARKHAND("JH"),
    KARNATAKA("KA"),
    MADHYAPRADESH("MP"),
    MAHARASHTRA("MH"),
    MANIPUR("MN"),
    MEGHALAYA("ML"),
    MIZORAM("MZ"),
    NAGALAND("NL"),
    ORISSA("OR"),
    PUNJAB("PB"),
    RAJASTHAN("RJ"),
    SIKKIM("SK"),
    TRIPURA("TR"),
    UTTARAKHAND("UK"),
    UTTARPRADESH("UP"),
    WESTBENGAL("WB"),
    ANDAMANANDNICOBARISLANDS("AN"),
    CHANDIGARH("CH"),
    DADRAANDNAGARHAVELI("DH"),
    DAMANANDDIU("DD"),
    DELHI("DL"),
    LAKSHADWEEP("LD"),
    PONDICHERRY("PY");


    private String stateShortForm;

    private Permit(String stateShortForm) {
        this.stateShortForm = stateShortForm;
    }

    public String getState() {
        return stateShortForm;
    }

}

