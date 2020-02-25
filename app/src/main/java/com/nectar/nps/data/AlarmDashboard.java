package com.nectar.nps.data;

public class AlarmDashboard {
    String VenderID;
    String VenderName;
    String NetworkTypeID;
    String NetworkType;
    String TotalCount;

    public String getVenderID() {
        return VenderID;
    }

    public void setVenderID(String venderID) {
        VenderID = venderID;
    }

    public String getVenderName() {
        return VenderName;
    }

    public void setVenderName(String venderName) {
        VenderName = venderName;
    }

    public String getNetworkTypeID() {
        return NetworkTypeID;
    }

    public void setNetworkTypeID(String networkTypeID) {
        NetworkTypeID = networkTypeID;
    }

    public String getNetworkType() {
        return NetworkType;
    }

    public void setNetworkType(String networkType) {
        NetworkType = networkType;
    }

    public String getTotalCount() {
        return TotalCount;
    }

    public void setTotalCount(String totalCount) {
        TotalCount = totalCount;
    }
}
