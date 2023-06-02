package dev.louisa.tpms.service;

import dev.louisa.tpms.service.alarm.Alarm;
import dev.louisa.tpms.service.alarm.PressureMonitorService;
import dev.louisa.tpms.service.pressure.PressureCheck;
import dev.louisa.tpms.service.pressure.PressureGauge;
import dev.louisa.tpms.service.pressure.PressureTooHighCheck;
import dev.louisa.tpms.service.pressure.PressureTooLowCheck;

public class PressureMonitorServiceBuilder {
    private int lowThreshold;
    private int highThreshold;
    private Alarm alarm;

    public static PressureMonitorServiceBuilder aPressureMonitorService() {
        return new PressureMonitorServiceBuilder();
    }

    public PressureMonitorServiceBuilder uses(Alarm alarm) {
        this.alarm = alarm;
        return this;
    }

    public PressureMonitorServiceBuilder withSafetyRange(int lowThreshold, int highThreshold) {
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
        return this;
    }

    public PressureMonitorService build() {
        PressureCheck pressureCheck = createChain(lowThreshold, highThreshold);
        return new PressureMonitorService(alarm, new PressureGauge(pressureCheck));
    }

    //TODO: redundant-1
    private PressureCheck createChain(double lowPressureThreshold , double highPressureThreshold) {
        var tooHighCheck = new PressureTooHighCheck(highPressureThreshold);
        var tooLowCheck = new PressureTooLowCheck(lowPressureThreshold);
        tooLowCheck.setNextCheck(tooHighCheck);
        return tooLowCheck;
    }

}
