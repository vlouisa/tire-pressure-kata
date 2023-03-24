package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;
import lombok.AllArgsConstructor;

import static dev.louisa.tpms.service.Pressure.*;

@AllArgsConstructor
public class PressureGauge {
    private final double lowPressureThreshold;
    private final double highPressureThreshold;

    public Pressure measure(TireSensor tireSensor) {
        var pressure = tireSensor.measureTirePressure();
        if (pressure < lowPressureThreshold) {
            return TOO_LOW;
        }
        
        if (pressure > highPressureThreshold) {
            return TOO_HIGH;
        }
        
        return OPTIMAL;
    }
}
