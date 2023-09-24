package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;
import dev.louisa.tpms.service.pressure.PressureCheck;
import dev.louisa.tpms.service.pressure.PressureGauge;
import dev.louisa.tpms.service.pressure.PressureTooHighCheck;
import dev.louisa.tpms.service.pressure.PressureTooLowCheck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.louisa.tpms.service.pressure.PressureState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PressureStateGaugeTest {
    @Mock
    private TireSensor tireSensor;
    private PressureGauge pressureGauge;

    @BeforeEach
    void setUp() {
        PressureCheck pressureCheck = createChain(20.0, 25.0);
        pressureGauge = new PressureGauge(pressureCheck);
    }

    //TODO: redundant-1
    private static PressureCheck createChain(double lowPressureThreshold , double highPressureThreshold) {
        var tooHighCheck = new PressureTooHighCheck(highPressureThreshold);
        var tooLowCheck = new PressureTooLowCheck(lowPressureThreshold);
        tooLowCheck.setNextCheck(tooHighCheck);
        return tooLowCheck;
    }

    @Test
    void should_inform_that_pressure_is_optimal() {
        when(tireSensor.measureTirePressure()).thenReturn(22.6);
        assertThat(pressureGauge.check(tireSensor)).isEqualTo(OPTIMAL);
    }

    @Test
    void should_inform_that_pressure_is_too_low() {
        when(tireSensor.measureTirePressure()).thenReturn(19.4);
        assertThat(pressureGauge.check(tireSensor)).isEqualTo(TOO_LOW);
    }

    @Test
    void should_inform_that_pressure_is_too_high() {
        when(tireSensor.measureTirePressure()).thenReturn(27.8);
        assertThat(pressureGauge.check(tireSensor)).isEqualTo(TOO_HIGH);
    }
}