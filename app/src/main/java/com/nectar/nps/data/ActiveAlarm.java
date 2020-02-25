package com.nectar.nps.data;

public class ActiveAlarm {
    public String EventTime;
    public String ProbableCause;
    public String ManagedElement;
    public String SpecificProblem;
    public String PerceivedSeverity;
    public String AlarmHour;

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

    public String getSpecificProblem() {
        return SpecificProblem;
    }

    public void setSpecificProblem(String specificProblem) {
        SpecificProblem = specificProblem;
    }

    public String getPerceivedSeverity() {
        return PerceivedSeverity;
    }

    public void setPerceivedSeverity(String perceivedSeverity) {
        PerceivedSeverity = perceivedSeverity;
    }

    public String getAlarmHour() {
        return AlarmHour;
    }

    public void setAlarmHour(String alarmHour) {
        AlarmHour = alarmHour;
    }
}
