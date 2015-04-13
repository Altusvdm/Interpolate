package za.co.afrikaburn.interpolate.ui.views.parameters;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Switch;

import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;

/**
 * Created by Altus on 2015/04/12.
 */
public class SwitchParameter extends BaseParameter {
    @InjectView(R.id.parameter_switch)
    Switch parameterSwitch;

    public SwitchParameter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getParameterLayout() {
        return R.layout.parameter_switch;
    }

    @Override
    public void setupParameter() {

    }

    @Override
    public void setValue(int value) {
        parameterSwitch.setChecked(value == 1);
    }
}
