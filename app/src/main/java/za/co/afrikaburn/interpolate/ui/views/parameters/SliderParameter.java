package za.co.afrikaburn.interpolate.ui.views.parameters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;

/**
 * Created by Altus on 2015/04/12.
 */
public class SliderParameter extends BaseParameter {
    @InjectView(R.id.parameter_slider)
    SeekBar parameterSlider;
    @InjectView(R.id.start_number)
    TextView startNumber;
    @InjectView(R.id.end_number)
    TextView endNumber;
    @InjectView(R.id.current_number)
    TextView currentNumber;

    int mStart;
    int mEnd;

    public SliderParameter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void handleAttrs(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SliderParameter,
                0, 0);

        try {
            mStart = a.getInt(R.styleable.SliderParameter_startNumber, 0);
            mEnd = a.getInt(R.styleable.SliderParameter_endNumber, 255);
        } finally {
            a.recycle();
        }
    }

    @Override
    public int getParameterLayout() {
        return R.layout.parameter_slider;
    }

    @Override
    public void setupParameter() {
        startNumber.setText("" + mStart);
        endNumber.setText("" + mEnd);
        currentNumber.setText("" + mStart);

        parameterSlider.setMax(mEnd - mStart);

        parameterSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentValue = mStart + progress;
                currentNumber.setText("" + currentValue);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void setValue(int value) {
        parameterSlider.setProgress(value - mStart);
    }
}
