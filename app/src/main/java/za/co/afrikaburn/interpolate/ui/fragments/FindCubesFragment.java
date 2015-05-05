package za.co.afrikaburn.interpolate.ui.fragments;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bugsnag.android.Bugsnag;
import com.squareup.otto.Subscribe;

import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import za.co.afrikaburn.interpolate.Bluetooth.BluetoothUtils;
import za.co.afrikaburn.interpolate.InterpolateApplication;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.events.BackPressedEvent;
import za.co.afrikaburn.interpolate.events.MenuItemEvent;
import za.co.afrikaburn.interpolate.ui.adapters.BLEDeviceListAdapter;
import za.co.afrikaburn.interpolate.ui.views.ModeView;
import za.co.afrikaburn.interpolate.ui.views.parameters.SliderParameter;

/**
 * Created by Altus on 2015/04/17.
 */
public class FindCubesFragment extends BaseFragment {

    @InjectView(R.id.cubes_list)
    ListView deviceList;
    @InjectView(R.id.search_spinner)
    ProgressBar searchSpinner;
    @InjectView(R.id.stop_button)
    Button stopButton;

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    private Handler mHandler;

    private BLEDeviceListAdapter listAdapter;

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi,
                                     byte[] scanRecord) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                              listAdapter.addDevice(device);
                        }
                    });
                }
            };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_find_cubes, container, false);

            ButterKnife.inject(this, rootView);

            listAdapter = new BLEDeviceListAdapter(getActivity());
            deviceList.setAdapter(listAdapter);

            deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);

                    BluetoothDevice device = listAdapter.getItem(position);
                    if (device != null) {
                        DeviceFragment deviceFragment = new DeviceFragment();
                        deviceFragment.device = device;
                        deviceFragment.bluetoothGatt = device.connectGatt(getActivity(), false, InterpolateApplication.getGattCallback());
                        getMainActivity().showFragment(deviceFragment, null, "Device");
                    }
                }
            });
        }

        startDetecting();

        return rootView;
    }

    @Subscribe
    public void backPressed(BackPressedEvent event) {
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
    }

    private void startDetecting() {
        searchSpinner.setVisibility(View.VISIBLE);
        mScanning = true;
        stopButton.setText("Stop Scanning");
        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getActivity().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Toast.makeText(getActivity(), "BLE supported", Toast.LENGTH_SHORT).show();
            return;
        }

        mHandler = new Handler();
        // Stops scanning after a pre-defined scan period.
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopScanning();
            }
        }, 10 * 10000);

        mScanning = true;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
    }

    public void stopScanning() {
        mScanning = false;
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                searchSpinner.setVisibility(View.GONE);
                stopButton.setText("Start Scanning");
            }
        });
    }

    @OnClick(R.id.stop_button)
    public void onStopPressed() {
        if (mScanning) {
            stopScanning();
        } else {
            startDetecting();
        }
    }

    @OnClick(R.id.clear_list)
    public void clearPressed() {
        if (mScanning) {
            stopScanning();
        }

        listAdapter.clearDevices();
    }
}
