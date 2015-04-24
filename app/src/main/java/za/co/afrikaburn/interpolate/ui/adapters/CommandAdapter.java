package za.co.afrikaburn.interpolate.ui.adapters;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import za.co.afrikaburn.interpolate.Bluetooth.BluetoothUtils;
import za.co.afrikaburn.interpolate.R;

/**
 * Created by Altus on 2015/04/24.
 */
public class CommandAdapter extends BaseAdapter {
    public Context context;
    public boolean isPulse = false;

    public CommandAdapter(Context context) {
        super();

        this.context = context;
    }

    @Override
    public int getCount() {
        if (isPulse) {
            return 2;
        } else {
            if (BluetoothUtils.modeNameMap != null) {
                return BluetoothUtils.modeNameMap.size();
            }
            return 0;
        }
    }

    @Override
    public String getItem(int i) {
        if (BluetoothUtils.modeNameMap != null) {
            return BluetoothUtils.modeNameMap.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (isPulse) {
            if (view == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.item_action_layout, null);
            }

            TextView nameView = (TextView) view.findViewById(R.id.action_text);

            switch (i) {
                case 0: nameView.setText("Square Pulse");
                    break;
                case 1: nameView.setText("Cross Pulse");
                    break;
            }

            return view;
        } else {
            if (view == null) {
                LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = vi.inflate(R.layout.item_action_layout, null);
            }

            TextView nameView = (TextView) view.findViewById(R.id.action_text);

            nameView.setText(getItem(i));

            return view;
        }
    }
}
