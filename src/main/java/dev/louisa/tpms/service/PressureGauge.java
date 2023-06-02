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

            new PressureTooLowCheck().execute(pressure);
            new PressureTooHighCheck().execute(pressure);

            return OPTIMAL;
        } catch (TpmsPressureTooLowException e) {
            return TOO_LOW;   
        } catch (TpmsPressureTooHighException e) {
            return TOO_HIGH;
        }
    }

    private class PressureTooLowCheck {

        public void execute(double pressure) {
            if (pressure < lowPressureThreshold) {
                throw new TpmsPressureTooLowException();
            }
        }
    }

    private class PressureTooHighCheck {
        public void execute(double pressure) {
            if (pressure > highPressureThreshold) {
                throw new TpmsPressureTooHighException();
            }
        }
    }
}
