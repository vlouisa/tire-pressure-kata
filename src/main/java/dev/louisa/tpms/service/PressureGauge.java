package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;
import lombok.AllArgsConstructor;

import static dev.louisa.tpms.service.Pressure.*;

@AllArgsConstructor
public class PressureGauge {
    private final double lowPressureThreshold;
    private final double highPressureThreshold;

    public Pressure measure(TireSensor tireSensor) {
        try {
            var pressure = tireSensor.measureTirePressure();
            
            if (pressure < lowPressureThreshold) {
                throw new TpmsPressureTooLowException();
            }

            if (pressure > highPressureThreshold) {
                throw new TpmsPressureTooHighException();
            }

            return OPTIMAL;
        } catch (TpmsPressureTooLowException e) {
            return TOO_LOW;   
        } catch (TpmsPressureTooHighException e) {
            return TOO_HIGH;
        }
    }
}
