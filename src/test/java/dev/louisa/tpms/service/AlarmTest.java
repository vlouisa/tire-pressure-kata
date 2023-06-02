package dev.louisa.tpms.service;

import dev.louisa.tpms.service.alarm.Alarm;
import dev.louisa.tpms.service.alarm.AlarmStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AlarmTest {
    private Alarm alarm;

    @BeforeEach
    void setUp() {
        alarm = new Alarm();
    }

    @Test
    void should_be_deactivated_when_alarm_is_initialized() {
        assertThat(alarm.getStatus()).isEqualTo(AlarmStatus.OFF);
    }

    @Test
    void should_be_on_when_activated() {
        alarm.activate();
        assertThat(alarm.getStatus()).isEqualTo(AlarmStatus.ON);
    }
}