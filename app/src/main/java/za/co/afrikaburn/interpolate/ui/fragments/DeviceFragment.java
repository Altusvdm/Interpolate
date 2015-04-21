package za.co.afrikaburn.interpolate.ui.fragments;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import za.co.afrikaburn.interpolate.Bluetooth.BluetoothUtils;
import za.co.afrikaburn.interpolate.Bluetooth.Events.CharacteristicReadEvent;
import za.co.afrikaburn.interpolate.Bluetooth.Events.ServicesDiscoveredEvent;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.ui.adapters.AttributeAdapter;
import za.co.afrikaburn.interpolate.ui.adapters.BLEDeviceListAdapter;
import za.co.afrikaburn.interpolate.ui.adapters.MapListAdapter;

/**
 * Created by Altus on 2015/04/17.
 */
public class DeviceFragment extends BaseFragment {

    @InjectView(R.id.attributes_list)
    ListView attributeList;
//    @InjectView(R.id.cont)
//    LinearLayout container;

    AttributeAdapter attributeAdapter;

    public BluetoothGatt bluetoothGatt;
    public BluetoothDevice device;

    BluetoothGattCharacteristic characteristic;
    BluetoothGattService service;

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_device, container, false);

            ButterKnife.inject(this, rootView);

            attributeAdapter = new AttributeAdapter(getActivity());
            attributeList.setAdapter(attributeAdapter);



            attributeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    writeToChar(attributeAdapter.getItem(position));
//                    bluetoothGatt.readCharacteristic(attributeAdapter.getItem(position));
                }
            });
        }

        return rootView;
    }

    @Subscribe
    public void displayGattServices(ServicesDiscoveredEvent gattServices) {
        if (gattServices == null) return;

        boolean isRightService = false;
        String uuid = null;
        String unknownServiceString = "Unknown Service String";
        String unknownCharaString = "Unknown charactiristic";

        ArrayList<HashMap<String, String>> gattServiceData = new ArrayList<HashMap<String, String>>();
        ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData = new ArrayList<ArrayList<HashMap<String, String>>>();
        mGattCharacteristics = new ArrayList<ArrayList<BluetoothGattCharacteristic>>();

        // Loops through available GATT Services.
        int i = 0;
        for (BluetoothGattService gattService : gattServices.services) {
            String listName = "Name: " + i;
            String listUUID = "UUID: " + i;
            HashMap<String, String> currentServiceData = new HashMap<String, String>();
            uuid = gattService.getUuid().toString();

            if (gattService.getUuid().toString().equals(BluetoothUtils.SERVICE_UUID)) {
                isRightService = true;
            } else {
                Log.d("SERVICE_UUID", gattService.getUuid().toString());
            }
            currentServiceData.put(listName, lookupAttributes(uuid, unknownServiceString));
            currentServiceData.put(listUUID, uuid);
            gattServiceData.add(currentServiceData);

            ArrayList<HashMap<String, String>> gattCharacteristicGroupData = new ArrayList<HashMap<String, String>>();
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            ArrayList<BluetoothGattCharacteristic> charas = new ArrayList<BluetoothGattCharacteristic>();
            // Loops through available Characteristics.
            for (BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                charas.add(gattCharacteristic);
                HashMap<String, String> currentCharaData = new HashMap<String, String>();
                uuid = gattCharacteristic.getUuid().toString();
                currentCharaData.put(listName, lookupAttributes(uuid, unknownCharaString));
                currentCharaData.put(listUUID, uuid);
                gattCharacteristicGroupData.add(currentCharaData);

                if (isRightService) {
                    if(gattCharacteristic.getUuid().toString().equals(BluetoothUtils.CHAR_PULSE_RISE_TIME_UUID)) {
                        characteristic = gattCharacteristic;
                    }
                }
                attributeAdapter.addChar(gattCharacteristic);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);

            i++;
            isRightService = false;
        }

        final ArrayList<ArrayList<HashMap<String, String>>> gattFinalCharacteristicData = gattCharacteristicData;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                attributeAdapter.notifyDataSetChanged();
//                addStuffs(gattFinalCharacteristicData);
            }
        });
    }

//    public void addStuffs(ArrayList<ArrayList<HashMap<String, String>>> gattCharacteristicData) {
//        int i = 0;
//        for (ArrayList<HashMap<String, String>> list : gattCharacteristicData) {
//            i++;
//            addTextViewWithString("List " + i + ":\n\n");
//            for (HashMap<String, String> map : list) {
//                for (String key : map.keySet()) {
//                    addTextViewWithString("Key: " + key + "\nValue: " + map.get(key));
//                }
//            }
//        }
//    }

//    public void addTextViewWithString(String string) {
//        TextView textView = new TextView(getActivity());
//        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        textView.setText(string);
//
//        container.addView(textView);
//    }

    @Subscribe
    public void readCharacteristic(final CharacteristicReadEvent event) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                attributeAdapter.updateCharacteristic(event.characteristic);
            }
        });
    }

    private String lookupAttributes(String uuid, String unknown) {
        return uuid + " : " + unknown;
    }

    @OnClick(R.id.close_button)
    public void onClosePressed() {
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.close();
        bluetoothGatt = null;

        getMainActivity().showFragment(new FindCubesFragment());
    }

    @OnClick(R.id.write)
    public void writePressed() {
        byte[] val = {2};
        if (characteristic != null) {
            characteristic.setValue(val);
            bluetoothGatt.writeCharacteristic(characteristic);
        } else {
            Toast.makeText(getActivity(), "NO CHARACTERISTIC TO WRITE TOO", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.write_reliable)
    public void writeReliable() {
        byte[] val = {2};
        if (characteristic != null) {
            bluetoothGatt.beginReliableWrite();
            characteristic.setValue(val);
            bluetoothGatt.writeCharacteristic(characteristic);
            bluetoothGatt.executeReliableWrite();
        } else {
            Toast.makeText(getActivity(), "NO CHARACTERISTIC TO WRITE TOO", Toast.LENGTH_SHORT).show();
        }
    }

    public void writeToChar(BluetoothGattCharacteristic chara) {
        if (chara.getUuid() != null) {
            Integer size = BluetoothUtils.char_size.get(chara.getUuid().toString());
            if (size != null) {
                byte[] val;
                if (size == 1) {
                    val = new byte[]{2};
                } else {
                    val = new byte[]{2, 2};
                }

                chara.setValue(val);
                bluetoothGatt.writeCharacteristic(chara);
            }
        }
    }
}
