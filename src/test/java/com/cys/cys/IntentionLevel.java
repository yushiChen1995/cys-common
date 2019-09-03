package com.cys.cys;


import com.google.common.collect.Maps;

import java.util.Map;



public enum IntentionLevel {


    IL_ZERO("系主任", (byte) 0),
    IL_H("教务处", (byte) 1),
    IL_A("院领导", (byte) 2),
    IL_C("我最大", (byte) 10),
    IL_B("老大", (byte) 5);

    private String DESC;
    private byte CODE;

    IntentionLevel(String DESC, byte CODE) {
        this.DESC = DESC;
        this.CODE = CODE;
    }

    public String getDESC() {
        return DESC;
    }

    public void setDESC(String DESC) {
        this.DESC = DESC;
    }

    public byte getCODE() {
        return CODE;
    }

    public void setCODE(byte CODE) {
        this.CODE = CODE;
    }


    public static Map<String, Byte> roleMap = Maps.newHashMap();

    static {
        IntentionLevel[] types = IntentionLevel.values();
        for (IntentionLevel type : types) {
            roleMap.put(type.DESC, type.CODE);
        }
    }

}
