package za.co.afrikaburn.interpolate.ui.views.parameters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.InjectView;
import za.co.afrikaburn.interpolate.Bluetooth.Events.WriteCharaEvent;
import za.co.afrikaburn.interpolate.InterpolateApplication;
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

    @InjectView(R.id.mul_start_number)
        TextView mulStartNumber;
    @InjectView(R.id.mul_end_number)
        TextView mulEndNumber;
    @InjectView(R.id.mul_parameter_slider)
        SeekBar mulParameterSlider;
    @InjectView(R.id.mul_current_number)
        TextView mulCurrentNumber;
    @InjectView(R.id.mul_total_number)
        TextView mulTotalNumber;
    @InjectView(R.id.mul_container)
    LinearLayout mulContainer;

    int mStart;
    int mEnd;
    int mCurrent;

    boolean hasMult = false;
    int mulStart;
    int mulEnd;
    int mulCurrent;
    int mulTotal;

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
            mCurrent = mStart;
        } finally {
            a.recycle();
        }
    }

    public void setHideNumbers() {
        this.startNumber.setVisibility(View.GONE);
        this.endNumber.setVisibility(View.GONE);
        this.parameterSlider.setPadding(0, 0, 0, 0);
    }

    public void setMultiplier(int start, int end) {
        hasMult = true;
        mulStart = start;
        mulEnd = end;
        mulCurrent = start;

        mulContainer.setVisibility(View.VISIBLE);

        mulParameterSlider.setMax(mulEnd - mulStart);
        mulStartNumber.setText("" + mulStart);
        mulEndNumber.setText("" + mulEnd);
        mulCurrentNumber.setText("Multiplier: " + mulCurrent);
        mulTotal = mCurrent * mulCurrent;
        mulTotalNumber.setText("Total: " + mulTotal);

        mulParameterSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mulCurrent = mulStart + progress;
                mulCurrentNumber.setText("Multiplier: " + mulCurrent);

                mulTotal = mCurrent * mulCurrent;

                mulTotalNumber.setText("Total: " + mulTotal);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (hasMult) {
                    InterpolateApplication.postOnEventBus(new WriteCharaEvent(uuid, mulTotal));
                } else {
                    InterpolateApplication.postOnEventBus(new WriteCharaEvent(uuid, mCurrent));
                }
            }
        });
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
                mCurrent = mStart + progress;
                currentNumber.setText("" + mCurrent);

                if (hasMult) {
                    mulTotal = mulCurrent * mCurrent;
                    mulTotalNumber.setText("" + mulTotal);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                InterpolateApplication.postOnEventBus(new WriteCharaEvent(uuid, mCurrent));
            }
        });
    }

    @Override
    public void setValue(int value) {
        parameterSlider.setProgress(value - mStart);
    }
}
