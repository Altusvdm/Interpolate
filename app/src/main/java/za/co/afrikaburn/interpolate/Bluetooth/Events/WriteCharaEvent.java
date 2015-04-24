package za.co.afrikaburn.interpolate.Bluetooth.Events;

/**
 * Created by Altus on 2015/04/24.
 */
public class WriteCharaEvent {
    public String uuid;
    public int value;

    public WriteCharaEvent(String uuid, int value) {
        this.uuid = uuid;
        this.value = value;
    }
}
