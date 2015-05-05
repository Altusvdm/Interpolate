package za.co.afrikaburn.interpolate.ui.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.model.Cube;
import za.co.afrikaburn.interpolate.ui.views.CubeView;

/**
 * Created by Altus on 2015/04/17.
 */
public class BLEDeviceListAdapter extends BaseAdapter {
    private Context context;
    private List<BluetoothDevice> devices;

    public BLEDeviceListAdapter(Context context) {
        super();

        this.context = context;
        devices = new ArrayList<BluetoothDevice>();
    }

    public void clearDevices() {
        devices = new ArrayList<BluetoothDevice>();
        notifyDataSetChanged();
    }

    public void addDevice(BluetoothDevice device) {
        for (BluetoothDevice existDevice : devices) {
            if (existDevice.getAddress().equals(device.getAddress())) {
                return;
            }
        }

        devices.add(device);
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if (devices != null) {
            return devices.size();
        }
        return 0;
    }

    @Override
    public BluetoothDevice getItem(int i) {
        if (devices != null) {
            return devices.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = vi.inflate(R.layout.item_bluetooth_device, null);
        }

        TextView nameView = (TextView) view.findViewById(R.id.device_name);
        TextView addressView = (TextView) view.findViewById(R.id.device_address);

        BluetoothDevice device = getItem(i);

        nameView.setText(device.getName());
        addressView.setText(device.getAddress());


        return view;
    }
}
