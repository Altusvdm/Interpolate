package za.co.afrikaburn.interpolate.ui.adapters;

import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import za.co.afrikaburn.interpolate.R;

/**
 * Created by Altus on 2015/04/20.
 */
public class MapListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<HashMap<String, String>> characteristics;

    public MapListAdapter(Context context) {
        super();

        this.context = context;
        characteristics = new ArrayList<HashMap<String, String>>();
    }

    public void addChar(HashMap<String, String> characteristic) {
        characteristics.add(characteristic);
    }

//    public void updateCharacteristic(BluetoothGattCharacteristic characteristic) {
//        for (BluetoothGattCharacteristic chara : characteristics) {
//            if (chara.getUuid().equals(characteristic.getUuid())) {
//                chara.setValue(characteristic.getValue());
//                notifyDataSetChanged();
//                return;
//            }
//        }
//    }

    @Override
    public int getCount() {
        if (characteristics != null) {
            return characteristics.size();
        }
        return 0;
    }

    @Override
    public HashMap<String, String> getItem(int i) {
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

        HashMap<String, String> characteristic = getItem(i);

        String names = "";
        String values = "";
        for (String key : characteristic.keySet()) {
            names = names + "; " + key;
            values = values + "; " + characteristic.get(key);
        }

        nameView.setText(names);
        addressView.setText(values);


        return view;
    }
}