package service;

import lombok.RequiredArgsConstructor;
import mapper.EventMapper;
import model.hub.HubEvent;
import model.sensor.SensorEvent;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final KafkaEventProducer producer;
    private static final String HUBS_TOPIC = "telemetry.hubs.v1";
    private static final String SENSORS_TOPIC = "telemetry.sensors.v1";

    @Override
    public void collectHubEvent(HubEvent event) {
        producer.send(HUBS_TOPIC, EventMapper.toHubEventAvro(event));
    }

    @Override
    public void collectSensorEvent(SensorEvent event) {
        producer.send(SENSORS_TOPIC, EventMapper.toSensorEventAvro(event));
    }
}