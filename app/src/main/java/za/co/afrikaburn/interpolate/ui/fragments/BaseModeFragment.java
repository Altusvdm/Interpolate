package za.co.afrikaburn.interpolate.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.ui.views.colorPicker.ColorPickerDialog;

/**
 * Created by Altus on 2015/04/11.
 */
public class BaseModeFragment extends BaseFragment {

    @InjectView(R.id.select_color)
    Button selectColor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_mode, container, false);

            ButterKnife.inject(this, rootView);
        }

        return rootView;
    }

    @OnClick(R.id.select_color)
    public void onSelectColor() {
        ColorDrawable drawable = (ColorDrawable) selectColor.getBackground();
        int color = drawable.getColor();

        ColorPickerDialog pickerDialog = new ColorPickerDialog(getActivity(), color);
        pickerDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                selectColor.setBackgroundColor(color);
            }
        });
        pickerDialog.show();
    }
}
