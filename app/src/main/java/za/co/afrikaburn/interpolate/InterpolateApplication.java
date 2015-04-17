package za.co.afrikaburn.interpolate;

import android.content.pm.PackageManager;
import android.widget.Toast;

import com.orm.SugarApp;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import za.co.afrikaburn.interpolate.Bluetooth.GattCallback;

/**
 * Created by Altus on 2015/04/13.
 */
public class InterpolateApplication extends SugarApp {
    public static Bus eventBus;
    private static GattCallback gattCallback;

    public static void registerOnEventBus(Object registerMe) {
        if (eventBus != null) {
            eventBus.register(registerMe);
        }
    }

    public static void unregisterOnEventBus(Object unregisterMe) {
        if (eventBus != null) {
            eventBus.unregister(unregisterMe);
        }
    }

    public static void postOnEventBus(Object postMe) {
        if (eventBus != null) {
            eventBus.post(postMe);
        }
    }

    @Override
    public void onCreate() {
        initialise();

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE not supported", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "BLE supported", Toast.LENGTH_SHORT).show();
        }

        super.onCreate();
    }

    public static GattCallback getGattCallback() {
        if (gattCallback == null) {
            gattCallback = new GattCallback();
        }

        return gattCallback;
    }

    public void initialise() {
        if (eventBus == null) {
            eventBus = new Bus(ThreadEnforcer.ANY);
            registerOnEventBus(this);
        }
    }
}
