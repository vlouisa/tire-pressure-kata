package dev.louisa.tpms.service;

import dev.louisa.tpms.sensor.TireSensor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.louisa.tpms.service.Pressure.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PressureGaugeTest {
    @Mock
    private TireSensor tireSensor;
    private PressureGauge pressureGauge;

    @BeforeEach
    void setUp() {
        pressureGauge = new PressureGauge(20.0, 25.0);
    }

    @Test
    void should_inform_that_pressure_is_optimal() {
        when(tireSensor.measureTirePressure()).thenReturn(22.6);
        assertThat(pressureGauge.measure(tireSensor)).isEqualTo(OPTIMAL);
    }

    @Test
    void should_inform_that_pressure_is_too_low() {
        when(tireSensor.measureTirePressure()).thenReturn(19.4);
        assertThat(pressureGauge.measure(tireSensor)).isEqualTo(TOO_LOW);
    }

    @Test
    void should_inform_that_pressure_is_too_high() {
        when(tireSensor.measureTirePressure()).thenReturn(27.8);
        assertThat(pressureGauge.measure(tireSensor)).isEqualTo(TOO_HIGH);
    }
}