package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;

public class AlarmService {
    private final double LowPressureThreshold = 24;
    private final double HighPressureThreshold = 29;
    private final Alarm alarm;

    public AlarmService(Alarm alarm) {
        this.alarm = alarm;
    }

    public void check(TireSensor tireSensor) {
        var psiPressureValue = tireSensor.measureTirePressure();
        if (psiPressureValue < LowPressureThreshold || HighPressureThreshold < psiPressureValue) {
            alarm.activate();
        }
    }

    public AlarmStatus getAlarmStatus() {
        return alarm.getStatus();
    }
}