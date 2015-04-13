package za.co.afrikaburn.interpolate.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.ui.views.parameters.ColorParameter;
import za.co.afrikaburn.interpolate.ui.views.parameters.SliderParameter;
import za.co.afrikaburn.interpolate.ui.views.parameters.SwitchParameter;

/**
 * Created by Altus on 2015/04/11.
 */
public class BaseModeFragment extends BaseFragment {

    @InjectView(R.id.from_color)
    ColorParameter colorParameter;
    @InjectView(R.id.param_switch)
    SwitchParameter switchParameter;
    @InjectView(R.id.param_slider)
    SliderParameter sliderParameter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_mode, container, false);

            ButterKnife.inject(this, rootView);

            switchParameter.setValue(1);

            sliderParameter.setValue(12);

            colorParameter.setValue(Color.DKGRAY);
        }

        return rootView;
    }
}
