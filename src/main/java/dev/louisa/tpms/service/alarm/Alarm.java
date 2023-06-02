package dev.louisa.tpms.service.alarm;

public class Alarm {

    private AlarmStatus status = AlarmStatus.OFF;

    public void activate() {
        this.status = AlarmStatus.ON;
    }

    public AlarmStatus getStatus() {
        return this.status;
    }
}
