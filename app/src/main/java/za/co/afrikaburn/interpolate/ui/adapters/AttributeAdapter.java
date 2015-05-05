package za.co.afrikaburn.interpolate.ui.adapters;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
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

import za.co.afrikaburn.interpolate.Bluetooth.BluetoothUtils;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.model.Cube;
import za.co.afrikaburn.interpolate.ui.views.CubeView;

/**
 * Created by Altus on 2015/04/17.
 */
public class AttributeAdapter extends BaseAdapter {
    private Context context;
    private List<BluetoothGattCharacteristic> characteristics;

    public AttributeAdapter(Context context) {
        super();

        this.context = context;
        characteristics = new ArrayList<BluetoothGattCharacteristic>();
    }

    public void addChar(BluetoothGattCharacteristic characteristic) {
        characteristics.add(characteristic);
    }

    public void updateCharacteristic(BluetoothGattCharacteristic characteristic) {
        for (BluetoothGattCharacteristic chara : characteristics) {
            if (chara.getUuid().equals(characteristic.getUuid())) {
                chara.setValue(characteristic.getValue());
                notifyDataSetChanged();
                return;
            }
        }
    }

    @Override
    public int getCount() {
        if (characteristics != null) {
            return characteristics.size();
        }
        return 0;
    }

    @Override
    public BluetoothGattCharacteristic getItem(int i) {
        if (characteristics != null) {
            return characteristics.get(i);
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
            view = vi.inflate(R.layout.item_attribute, null);
        }

        TextView nameView = (TextView) view.findViewById(R.id.attribute_name);
        TextView addressView = (TextView) view.findViewById(R.id.attribute_value);

        BluetoothGattCharacteristic characteristic = getItem(i);

        if (characteristic != null) {
            String name = "Service: ";
            if (characteristic.getService() != null && characteristic.getService().getUuid() != null) {
                name = name + BluetoothUtils.lookupServer(characteristic.getService().getUuid().toString());
            }
            name = name + "\nChar: ";
            if (characteristic.getUuid() != null) {
                name = name + BluetoothUtils.lookupChar(characteristic);
            } else {
                name = name + "NULL CHAR UUID";
            }
            nameView.setText(name);
        } else {
            nameView.setText("NULL CHAR");
        }

        return view;
    }
}
