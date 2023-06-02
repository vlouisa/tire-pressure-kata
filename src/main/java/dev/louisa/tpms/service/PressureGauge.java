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

            PressureCheck pressureCheck = createChain();
            pressureCheck.execute(pressure);

            return OPTIMAL;
        } catch (TpmsPressureTooLowException e) {
            return TOO_LOW;   
        } catch (TpmsPressureTooHighException e) {
            return TOO_HIGH;
        }
    }

    private PressureCheck createChain() {
        var tooHighCheck = new PressureTooHighCheck(highPressureThreshold);
        var tooLowCheck = new PressureTooLowCheck(lowPressureThreshold);
        tooLowCheck.setNextCheck(tooHighCheck);
        return tooLowCheck;
    }

}
