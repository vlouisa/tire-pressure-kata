package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;

public class AlarmService {
    private final double LowPressureThreshold = 24;
    private final double HighPressureThreshold = 29;

    private TireSensor tireSensor = new TireSensor();

    private AlarmStatus alarmStatus = AlarmStatus.OFF;
    
    public void check() {
        var psiPressureValue = tireSensor.measureTirePressure();
        if (psiPressureValue < LowPressureThreshold || HighPressureThreshold < psiPressureValue) {
            alarmStatus = AlarmStatus.ON;
        }
    }

    public AlarmStatus getAlarmStatus() {
        return alarmStatus;
    }
}