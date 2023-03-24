package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;

public class AlarmService {
    private final double LowPressureThreshold = 24;
    private final double HighPressureThreshold = 29;
    private final Alarm alarm;
    private final PressureGauge pressureGauge;

    public AlarmService(Alarm alarm) {
        this.pressureGauge = new PressureGauge(LowPressureThreshold, HighPressureThreshold);
        this.alarm = alarm;
    }

    public void check(TireSensor tireSensor) {
        if (!(pressureGauge.measure(tireSensor) == Pressure.OPTIMAL)) {
            alarm.activate();  
        }
    }

    public AlarmStatus getAlarmStatus() {
        return alarm.getStatus();
    }
}