package dev.louisa.tpms.service.alarm;

import dev.louisa.tpms.sensor.TireSensor;
import dev.louisa.tpms.service.pressure.PressureResult;
import dev.louisa.tpms.service.pressure.PressureGauge;

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
        return !(pressureGauge.measure(tireSensor) == PressureResult.OPTIMAL);
    }

    public AlarmStatus getAlarmStatus() {
        return alarm.getStatus();
    }
}