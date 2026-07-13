package model.hub.device;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.hub.HubEvent;
import model.hub.HubEventType;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceAddedEvent extends HubEvent {
    private String id;
    private DeviceType deviceType;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_ADDED;
    }
}
