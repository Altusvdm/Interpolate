package za.co.afrikaburn.interpolate.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.events.MenuItemEvent;
import za.co.afrikaburn.interpolate.ui.views.ModeView;

/**
 * Created by Altus on 2015/04/11.
 */
public class BaseModeFragment extends BaseFragment {

    @InjectView(R.id.mode)
    ModeView modeView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            rootView = inflater.inflate(R.layout.fragment_mode, container, false);

            ButterKnife.inject(this, rootView);

            setMenuItem(R.id.action_radial);
        }

        return rootView;
    }

    @Subscribe
    public void onMenuEvent(MenuItemEvent menuItemEvent) {
        int itemId = menuItemEvent.getId();

        setMenuItem(itemId);
    }

    private void setMenuItem(int itemId) {
        switch (itemId) {
            case R.id.action_radial:
                modeView.setTitle("Radial");
                modeView.setParametersWithLayout(R.layout.mode_radial);
                break;
            case R.id.action_linear:
                modeView.setTitle("Linear");
                modeView.setParametersWithLayout(R.layout.mode_linear);
                break;
            case R.id.action_jump_tap:
                modeView.setTitle("Jump Tap");
                modeView.setParametersWithLayout(R.layout.mode_jump_tap);
                break;
            case R.id.action_solid_tap:
                modeView.setTitle("Solid Tap");
                modeView.setParametersWithLayout(R.layout.mode_solid_tap);
                break;
        }
    }

    public void setupActionBar() {
        super.setupActionBar();
        getMainActivity().setOptionsMenuResource(R.menu.menu_modes);
    }
}
