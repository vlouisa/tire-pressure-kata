package dev.louisa.tpms.service.pressure;

import dev.louisa.tpms.sensor.TireSensor;
import lombok.AllArgsConstructor;

import static dev.louisa.tpms.service.pressure.PressureResult.*;

@AllArgsConstructor
public class PressureGauge {
    private final PressureCheck pressureCheck;

    public PressureResult measure(TireSensor tireSensor) {
        try {
            checkPressure(tireSensor);
            return OPTIMAL;
        } catch (PressureTooLowException e) {
            return TOO_LOW;   
        } catch (PressureTooHighException e) {
            return TOO_HIGH;
        }
    }

    private void checkPressure(TireSensor tireSensor) {
        var pressure = tireSensor.measureTirePressure();
        pressureCheck.execute(pressure);
    }
}
