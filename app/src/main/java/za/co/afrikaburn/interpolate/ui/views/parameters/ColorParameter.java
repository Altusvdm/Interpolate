package za.co.afrikaburn.interpolate.ui.views.parameters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.ui.views.colorPicker.ColorPickerDialog;

/**
 * Created by Altus on 2015/04/12.
 */
public class ColorParameter extends BaseParameter {

    @InjectView(R.id.preview_color)
    View previewColor;

    public ColorParameter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getParameterLayout() {
        return R.layout.parameter_color;
    }

    @Override
    public void setupParameter() {
        previewColor.setBackgroundColor(Color.GREEN);
    }

    @OnClick(R.id.select_button)
    public void selectColor() {
        ColorDrawable drawable = (ColorDrawable) previewColor.getBackground();
        int color = drawable.getColor();

        ColorPickerDialog pickerDialog = new ColorPickerDialog(getContext(), color);
        pickerDialog.setOnColorChangedListener(new ColorPickerDialog.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                previewColor.setBackgroundColor(color);
            }
        });
        pickerDialog.show();
    }
}
