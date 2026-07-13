package model.hub.device;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.hub.HubEvent;
import model.hub.HubEventType;

@Data
@EqualsAndHashCode(callSuper = true)
public class DeviceRemovedEvent extends HubEvent {
    private String id;

    @Override
    public HubEventType getType() {
        return HubEventType.DEVICE_REMOVED;
    }

}
