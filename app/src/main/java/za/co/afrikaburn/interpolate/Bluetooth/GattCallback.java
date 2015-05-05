package za.co.afrikaburn.interpolate.Bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.util.Log;
import android.widget.Toast;

import za.co.afrikaburn.interpolate.Bluetooth.Events.CharacteristicReadEvent;
import za.co.afrikaburn.interpolate.Bluetooth.Events.ServicesDiscoveredEvent;
import za.co.afrikaburn.interpolate.InterpolateApplication;

/**
 * Created by Altus on 2015/04/17.
 */
public class GattCallback extends android.bluetooth.BluetoothGattCallback {
    private final static String TAG = "INTERPOLATE_GATT_CALLBACK";

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                        int newState) {
        String intentAction;
        if (newState == BluetoothProfile.STATE_CONNECTED) {
//                        intentAction = ACTION_GATT_CONNECTED;
//                        mConnectionState = STATE_CONNECTED;
//                        broadcastUpdate(intentAction);
            Log.i(TAG, "Connected to GATT server.");
            Log.i(TAG, "Attempting to start service discovery:" +
                    gatt.discoverServices());

        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                        intentAction = ACTION_GATT_DISCONNECTED;
//                        mConnectionState = STATE_DISCONNECTED;
            Log.i(TAG, "Disconnected from GATT server.");
//                        broadcastUpdate(intentAction);
        }
    }

    @Override
    // New services discovered
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
//                        broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
            Log.i(TAG, "onServicesDiscovered SUCCESSS");
            InterpolateApplication.postOnEventBus(new ServicesDiscoveredEvent(gatt.getServices()));
        } else {

            Log.w(TAG, "onServicesDiscovered received: " + status);
        }
    }

    @Override
    // Result of a characteristic read operation
    public void onCharacteristicRead(BluetoothGatt gatt,
                                     BluetoothGattCharacteristic characteristic,
                                     int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            InterpolateApplication.postOnEventBus(new CharacteristicReadEvent(characteristic));
            Log.i(TAG, "Charactiristic available: " + characteristic);
//                        broadcastUpdate(ACTION_DATA_AVAILABLE, characteristic);
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt,
                                      BluetoothGattCharacteristic characteristic,
                                      int status) {
        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.d("SUCCESS", "Wrote success");
//            Toast.makeText(InterpolateApplication.getSugarContext(), "Wrote Success", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("FAIL", "Fail wrote");
//            Toast.makeText(InterpolateApplication.getSugarContext(), "Wrote Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
