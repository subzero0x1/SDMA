package ru.svalov.ma.model;

public enum EventType {

    HOLIDAY("HLD"),
    WORK("WRK"),
    SHORT("SRT"),
    VACATION("VAC"),
    FAKEVACATION("FVC"),
    FAKEWORK("FWK"),
    TEMPWORK("TWK"),
    DAILY_DUTY("DD"),
    SICK("SIK"),
    BUILD("BLD"),
    REMOTE("RMT");

    private String typeId;

    EventType(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeId() {
        return typeId;
    }
}
