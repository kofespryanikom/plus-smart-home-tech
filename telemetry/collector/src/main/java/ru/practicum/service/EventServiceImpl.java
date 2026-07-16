package ru.practicum.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.EventMapper;
import ru.practicum.model.hub.HubEvent;
import ru.practicum.model.sensor.SensorEvent;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final KafkaEventProducer producer;
    private final ObjectMapper objectMapper;
    private static final String HUBS_TOPIC = "telemetry.hubs.v1";
    private static final String SENSORS_TOPIC = "telemetry.sensors.v1";

    @Override
    public void collectHubEvent(HubEvent event) {
        try {
            log.info("json: {}", objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.warn("Error occurred while processing HubEvent", e);
        }
        producer.send(HUBS_TOPIC, EventMapper.toHubEventAvro(event));
    }

    @Override
    public void collectSensorEvent(SensorEvent event) {
        try {
            log.info("json: {}", objectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.warn("Error occurred while processing SensorEvent", e);
        }
        producer.send(SENSORS_TOPIC, EventMapper.toSensorEventAvro(event));
    }
}