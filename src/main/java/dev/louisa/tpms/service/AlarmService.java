package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;

public class AlarmService {
    private final Alarm alarm;
    private final PressureGauge pressureGauge;

    public AlarmService(Alarm alarm, PressureGauge pressureGauge) {
        this.pressureGauge = pressureGauge;
        this.alarm = alarm;
    }

    public void check(TireSensor tireSensor) {
        if (pressureIsNotOptimal(tireSensor)) {
            alarm.activate();  
        }
    }

    private boolean pressureIsNotOptimal(TireSensor tireSensor) {
        return !(pressureGauge.measure(tireSensor) == Pressure.OPTIMAL);
    }

    public AlarmStatus getAlarmStatus() {
        return alarm.getStatus();
    }
}