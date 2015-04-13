package za.co.afrikaburn.interpolate.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import za.co.afrikaburn.interpolate.InterpolateApplication;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.ui.activities.MainActivity;

/**
 * Created by Altus on 2015/04/11.
 */
public class BaseFragment extends Fragment {

    protected  View rootView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InterpolateApplication.registerOnEventBus(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        InterpolateApplication.unregisterOnEventBus(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupActionBar();
    }

    public MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }

    public void setupActionBar() {
        getMainActivity().setOptionsMenuResource(R.menu.menu_blank);
    }
}
