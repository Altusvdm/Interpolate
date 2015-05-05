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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import za.co.afrikaburn.interpolate.Bluetooth.BluetoothUtils;
import za.co.afrikaburn.interpolate.Bluetooth.Events.ServicesDiscoveredEvent;
import za.co.afrikaburn.interpolate.Bluetooth.Events.WriteCharaEvent;
import za.co.afrikaburn.interpolate.InterpolateApplication;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.events.BackPressedEvent;
import za.co.afrikaburn.interpolate.ui.adapters.AttributeAdapter;
import za.co.afrikaburn.interpolate.ui.adapters.CommandAdapter;
import za.co.afrikaburn.interpolate.ui.views.parameters.SliderParameter;

/**
 * Created by Altus on 2015/04/17.
 */
public class DeviceFragment extends BaseFragment {

    @InjectView(R.id.scroll_view)
    ScrollView scrollView;
    @InjectView(R.id.mode_spinner)
    Spinner modeSpinner;
    @InjectView(R.id.pulse_spinner)
    Spinner pulseSpinner;
    @InjectView(R.id.speed_param)
    SliderParameter speedParam;
    @InjectView(R.id.pulse_bright_param)
    SliderParameter pulseBrightParam;
    @InjectView(R.id.tap_sense_param)
    SliderParameter tapSenseParam;
    @InjectView(R.id.wave_width_param)
    SliderParameter waveWidthParam;
    @InjectView(R.id.linear_angle_param)
    SliderParameter linearAngleParam;
    @InjectView(R.id.radial_x_param)
    SliderParameter radXParam;
    @InjectView(R.id.radial_y_param)
    SliderParameter radYParam;
    @InjectView(R.id.pulse_speed_param)
    SliderParameter pulseSpeedParam;
    @InjectView(R.id.hue_param)
    SliderParameter hueParam;
    @InjectView(R.id.loader)
    ProgressBar loader;

    public BluetoothGatt bluetoothGatt;
    public BluetoothDevice device;

    public CommandAdapter commandAdapter;
    public CommandAdapter pulseAdapter;

    public boolean hasLoaded = false;

    private ArrayList<ArrayList<BluetoothGattCharacteristic>> mGattCharacteristics;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_device, container, false);

            ButterKnife.inject(this, rootView);

            speedParam.uuid = BluetoothUtils.CHAR_SPEED_UUID;
            speedParam.setMultiplier(1, 100);
            pulseBrightParam.uuid = BluetoothUtils.CHAR_PULSE_BRIGHTNESS_UUID;
            tapSenseParam.uuid = BluetoothUtils.CHAR_TAP_SENS_UUID;
            waveWidthParam.uuid = BluetoothUtils.CHAR_WAVE_WIDTH_UUID;
            linearAngleParam.uuid = BluetoothUtils.CHAR_LINEAR_ANGLE_UUID;
            radXParam.uuid = BluetoothUtils.CHAR_RAD_X_UUID;
            radYParam.uuid = BluetoothUtils.CHAR_RAD_Y_UUID;
            pulseSpeedParam.uuid = BluetoothUtils.CHAR_PULSE_SPEED_UUID;
            hueParam.uuid = BluetoothUtils.CHAR_HUE_UUID;
            hueParam.setHideNumbers();

            commandAdapter = new CommandAdapter(getActivity());
            modeSpinner.setAdapter(commandAdapter);

            pulseAdapter = new CommandAdapter(getActivity());
            pulseAdapter.isPulse = true;
            pulseSpinner.setAdapter(pulseAdapter);

            modeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    InterpolateApplication.postOnEventBus(new WriteCharaEvent(BluetoothUtils.CHAR_COMMAND_UUID, position));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

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
                BluetoothUtils.lookupChar(gattCharacteristic);
            }
            mGattCharacteristics.add(charas);
            gattCharacteristicData.add(gattCharacteristicGroupData);

            i++;
        }

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
            }
        });
        hasLoaded = true;

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
        bluetoothGatt.disconnect();
        bluetoothGatt = null;

        getMainActivity().showFragment(new FindCubesFragment());
    }

    @OnClick(R.id.send_pulse)
    public void sendPulse() {
        InterpolateApplication.postOnEventBus(new WriteCharaEvent(BluetoothUtils.CHAR_COMMAND_UUID, BluetoothUtils.pulseValueMap.get(pulseSpinner.getSelectedItemPosition())));
    }

    @Subscribe
    public void onBackPressed(BackPressedEvent event) {
        if (bluetoothGatt == null) {
            return;
        }
        bluetoothGatt.close();
        bluetoothGatt.disconnect();
        bluetoothGatt = null;
    }

    @Subscribe
    public void writeChar(WriteCharaEvent event) {
        String toast = "Write to: " + event.uuid + " with: " + event.value;
        if (hasLoaded) {
            BluetoothGattCharacteristic chara = BluetoothUtils.charaMap.get(event.uuid);
            if (chara != null) {

                Integer size = BluetoothUtils.char_size.get(event.uuid);
                byte[] val;
                if (size == 1) {
                    val = new byte[]
                            {
                                    (byte) event.value
                            };
                } else {
                    val = new byte[]
                            {
                                    (byte) event.value,
                                    (byte) (event.value >> 8 & 0xFF)
                            };
                }
                toast = toast + "\nFound char byte is: " + val;
                chara.setValue(val);
                chara.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
                bluetoothGatt.writeCharacteristic(chara);
            } else {
                toast = toast + "\nNo characteristic :(";
                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
