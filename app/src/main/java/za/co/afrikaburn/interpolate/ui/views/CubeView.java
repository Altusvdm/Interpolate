package za.co.afrikaburn.interpolate.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.model.Cube;
import za.co.afrikaburn.interpolate.ui.views.utils.Border;

/**
 * Created by Altus on 2015/02/01.
 */
public class CubeView extends RelativeLayout {
    public Cube cube;

    @InjectView(R.id.cube_mode_icon_view)
    TextView modeIconView;
    @InjectView(R.id.cube_selected_radio_button)
    RadioButton selectedRadioButton;
    @InjectView(R.id.cube_battery_view)
    View batterView;

    public CubeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupView();
    }

    private void setupView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_cube, this, true);

        ButterKnife.inject(view);
    }

    public void setCube(Cube cube) {
        this.cube = cube;

        switch(cube.cubeMode) {
            case BLINKING:
                modeIconView.setText("B");
                break;
            case RAINBOW:
                modeIconView.setText("R");
                break;
            default:
                modeIconView.setText("");
                break;
        }

        batterView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, cube.battery));
        if (cube.battery < 20.0f) {
            batterView.setBackgroundColor(Color.RED);
        } else {
            batterView.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (getLayoutParams() != null && w != h) {
            getLayoutParams().height = w;
            setLayoutParams(getLayoutParams());
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Border.onDraw(this, canvas);
    }
}
