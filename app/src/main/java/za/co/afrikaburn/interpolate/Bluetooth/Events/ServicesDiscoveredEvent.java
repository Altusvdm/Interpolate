package za.co.afrikaburn.interpolate.Bluetooth.Events;

import android.bluetooth.BluetoothGattService;

import java.util.List;

/**
 * Created by Altus on 2015/04/17.
 */
public class ServicesDiscoveredEvent {

    public List<BluetoothGattService> services;

    public ServicesDiscoveredEvent(List<BluetoothGattService> services) {
        this.services = services;
    }
}
