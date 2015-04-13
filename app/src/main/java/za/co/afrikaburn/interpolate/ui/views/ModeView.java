package za.co.afrikaburn.interpolate.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;

/**
 * Created by Altus on 2015/04/13.
 */
public class ModeView extends LinearLayout {

    @InjectView(R.id.mode_title)
    TextView modeTitle;
    @InjectView(R.id.parameters_container)
    LinearLayout parametersContainer;

    String mTitle;

    public ModeView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ModeView,
                0, 0);

        try {
            mTitle = a.getString(R.styleable.ModeView_modeTitle);
        } finally {
            a.recycle();
        }

        setupView();
    }

    private void setupView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.view_mode, this, true);

        ButterKnife.inject(view);

        setTitle(mTitle);
    }

    public void setTitle(String title) {
        mTitle = title;
        modeTitle.setText(mTitle);
    }

    public void setParametersWithLayout(int layout) {
        parametersContainer.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layout, parametersContainer, true);
    }
}
