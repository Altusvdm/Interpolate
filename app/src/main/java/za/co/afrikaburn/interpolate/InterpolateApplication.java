package za.co.afrikaburn.interpolate;

import android.app.Application;

import com.orm.SugarApp;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Altus on 2015/04/13.
 */
public class InterpolateApplication extends SugarApp {
    public static Bus eventBus;

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
        super.onCreate();
    }

    public void initialise() {
        if (eventBus == null) {
            eventBus = new Bus(ThreadEnforcer.ANY);
            registerOnEventBus(this);
        }
    }
}
