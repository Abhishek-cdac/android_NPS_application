package com.nectar.nps.data;

public class AlarmDashboard {
    String VenderID;
    String VenderName;
    String NetworkTypeID;
    String NetworkType;
    String TotalCount;
    String count;
    String title;
    String SpecificProblem;
    String SiteName;
    String ProbableCause;
    String ManagedElement;
    String EventTime;
    String SiteDetailID;
    String SiteDetailName;
    String TotalAlarm;
    String PerceivedSeverity;

    public String getSiteDetailName() {
        return SiteDetailName;
    }

    public void setSiteDetailName(String siteDetailName) {
        SiteDetailName = siteDetailName;
    }

    public String getTotalAlarm() {
        return TotalAlarm;
    }

    public void setTotalAlarm(String totalAlarm) {
        TotalAlarm = totalAlarm;
    }

    public String getSiteDetailID() {
        return SiteDetailID;
    }

    public void setSiteDetailID(String siteDetailID) {
        SiteDetailID = siteDetailID;
    }

    public String getEventTime() {
        return EventTime;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public String getProbableCause() {
        return ProbableCause;
    }

    public void setProbableCause(String probableCause) {
        ProbableCause = probableCause;
    }

    public String getManagedElement() {
        return ManagedElement;
    }

    public void setManagedElement(String managedElement) {
        ManagedElement = managedElement;
    }

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

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpecificProblem() {
        return SpecificProblem;
    }

    public void setSpecificProblem(String specificProblem) {
        SpecificProblem = specificProblem;
    }

    public String getSiteName() {
        return SiteName;
    }

    public void setSiteName(String siteName) {
        SiteName = siteName;
    }

    public String getPerceivedSeverity() {
        return PerceivedSeverity;
    }

    public void setPerceivedSeverity(String perceivedSeverity) {
        PerceivedSeverity = perceivedSeverity;
    }
}
