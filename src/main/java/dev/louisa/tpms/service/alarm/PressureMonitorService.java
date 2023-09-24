package dev.louisa.tpms.service.alarm;

import dev.louisa.tpms.sensor.TireSensor;
import dev.louisa.tpms.service.pressure.PressureState;
import dev.louisa.tpms.service.pressure.PressureGauge;

public class PressureMonitorService {
    private final Alarm alarm;
    private final PressureGauge pressureGauge;

    public PressureMonitorService(Alarm alarm, PressureGauge pressureGauge) {
        this.pressureGauge = pressureGauge;
        this.alarm = alarm;
    }

    public void check(TireSensor tireSensor) {
        if (pressureIsNotOptimal(tireSensor)) {
            activateAlarm();  
        }
    }

    private void activateAlarm() {
        alarm.activate();
    }

    private boolean pressureIsNotOptimal(TireSensor tireSensor) {
        return !(pressureGauge.check(tireSensor) == PressureState.OPTIMAL);
    }
}