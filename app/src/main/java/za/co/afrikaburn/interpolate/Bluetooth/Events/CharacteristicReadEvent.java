package za.co.afrikaburn.interpolate.Bluetooth.Events;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Created by Altus on 2015/04/17.
 */
public class CharacteristicReadEvent {
    public BluetoothGattCharacteristic characteristic;

    public CharacteristicReadEvent (BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}
