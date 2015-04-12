package za.co.afrikaburn.interpolate.ui.views.parameters;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;

/**
 * Created by Altus on 2015/04/12.
 */
public abstract class BaseParameter extends LinearLayout {

    @InjectView(R.id.title_view)
    public TextView titleView;
    @InjectView(R.id.parameter_container)
    public LinearLayout container;

    public String mTitle;

    public BaseParameter(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.BaseParameter,
                0, 0);

        try {
            mTitle = a.getString(R.styleable.BaseParameter_parameterTitle);
        } finally {
            a.recycle();
        }

        setupView();
    }

    private void setupView() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.parameter_base, this, true);

        inflater.inflate(getParameterLayout(), (ViewGroup)view.findViewById(R.id.parameter_container), true);

        ButterKnife.inject(view);

        titleView.setText(mTitle);
        setupParameter();
    }

    public abstract int getParameterLayout();

    public abstract void setupParameter();

}
