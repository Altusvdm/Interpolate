package za.co.afrikaburn.interpolate.ui.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.bugsnag.android.Bugsnag;

import butterknife.ButterKnife;
import butterknife.InjectView;
import za.co.afrikaburn.interpolate.InterpolateApplication;
import za.co.afrikaburn.interpolate.R;
import za.co.afrikaburn.interpolate.events.BackPressedEvent;
import za.co.afrikaburn.interpolate.events.MenuItemEvent;
import za.co.afrikaburn.interpolate.model.Cube;
import za.co.afrikaburn.interpolate.model.CubeMode;
import za.co.afrikaburn.interpolate.ui.adapters.CubeGridAdapter;
import za.co.afrikaburn.interpolate.ui.fragments.BaseModeFragment;
import za.co.afrikaburn.interpolate.ui.fragments.FindCubesFragment;
import za.co.afrikaburn.interpolate.ui.fragments.GridFragment;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.fragment_container)
    RelativeLayout fragmentContainer;

    protected boolean isShowingFragment = false;
    int mOptionsMenuResource = R.menu.menu_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bugsnag.init(this);

        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        if (savedInstanceState == null) {
            showFragment(new FindCubesFragment());
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            isShowingFragment = savedInstanceState.getBoolean("isShowingFragment");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("isShowingFragment", isShowingFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(mOptionsMenuResource, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        boolean isHandled = super.onOptionsItemSelected(item);
        if (!isHandled) {
            InterpolateApplication.postOnEventBus(new MenuItemEvent(item));
        }

        return isHandled;
    }

    @Override
    public void onBackPressed() {
        InterpolateApplication.postOnEventBus(new BackPressedEvent());
        super.onBackPressed();
    }

    public void showFragment(Fragment fragment) {
        showFragment(fragment, null, null);
    }

    public void showFragment(Fragment fragment, String tag, String backstack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isShowingFragment) {
            transaction.replace(R.id.fragment_container, fragment, tag);
        } else {
            transaction.add(R.id.fragment_container, fragment, tag);
        }
        if (backstack != null && backstack.length() > 0) {
            transaction.addToBackStack(backstack);
        }

        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();

        isShowingFragment = true;
    }

    public void setOptionsMenuResource (int optionsMenuResource) {
        mOptionsMenuResource = optionsMenuResource;
        supportInvalidateOptionsMenu();
    }
}
