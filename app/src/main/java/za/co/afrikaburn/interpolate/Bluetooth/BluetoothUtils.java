package za.co.afrikaburn.interpolate.Bluetooth;

import com.bugsnag.android.Bugsnag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Altus on 2015/04/21.
 */
public class BluetoothUtils {

    public static final String SERVICE_NOT_FOUND = "Unknown Service";
    public static final String SERVICE_UUID = "44656e64726974696353797374656d73";
    public static final Map<String , String> servers = new HashMap<String , String>() {{
        put(SERVICE_UUID,    "Cube Service");
    }};

    public static final String CHAR_NOT_FOUND = "Unknown Characteristic";
    public static final String CHAR_COMMAND_UUID = "44656e64726974696353797374656d73";
    public static final String CHAR_SPEED_UUID = "44656e64726974696353797374656d74";
    public static final String CHAR_MAX_BRIGHTNESS_UUID = "44656e64726974696353797374656d75";
    public static final String CHAR_TAP_SENS_UUID = "44656e64726974696353797374656d76";
    public static final String CHAR_WAVE_WIDTH_UUID = "44656e64726974696353797374656d77";
    public static final String CHAR_LINEAR_ANGLE_UUID = "44656e64726974696353797374656d78";
    public static final String CHAR_RAD_X_UUID = "44656e64726974696353797374656d79";
    public static final String CHAR_RAD_Y_UUID = "44656e64726974696353797374656d7A";
    public static final String CHAR_PULSE_RISE_TIME_UUID = "44656e64726974696353797374656d7B";
    public static final String CHAR_PULSE_FALL_TIME_UUID = "44656e64726974696353797374656d";
    public static final String CHAR_PULSE_SPEED_UUID = "44656e64726974696353797374656d7D";
    public static final Map<String , String> characteristics = new HashMap<String , String>() {{
        put(CHAR_COMMAND_UUID, "Command (mode/event)");
        put(CHAR_SPEED_UUID, "Speed");
        put(CHAR_MAX_BRIGHTNESS_UUID, "Max Brightness");
        put(CHAR_TAP_SENS_UUID, "Tap Sensitivity");
        put(CHAR_WAVE_WIDTH_UUID, "Wave Width");
        put(CHAR_LINEAR_ANGLE_UUID, "Linear Angle");
        put(CHAR_RAD_X_UUID, "Radial X");
        put(CHAR_RAD_Y_UUID, "Radial Y");
        put(CHAR_PULSE_RISE_TIME_UUID, "Pulse Rise Time");
        put(CHAR_PULSE_FALL_TIME_UUID, "Pulse Fall Time");
        put(CHAR_PULSE_SPEED_UUID, "Pulse Speed");
    }};
    public static final Map<String, Integer> char_size = new HashMap<String, Integer>() {{
        put(CHAR_COMMAND_UUID, 1);
        put(CHAR_SPEED_UUID, 2);
        put(CHAR_MAX_BRIGHTNESS_UUID, 2);
        put(CHAR_TAP_SENS_UUID, 1);
        put(CHAR_WAVE_WIDTH_UUID, 1);
        put(CHAR_LINEAR_ANGLE_UUID, 1);
        put(CHAR_RAD_X_UUID, 2);
        put(CHAR_RAD_Y_UUID, 2);
        put(CHAR_PULSE_RISE_TIME_UUID, 2);
        put(CHAR_PULSE_FALL_TIME_UUID, 2);
        put(CHAR_SPEED_UUID, 1);
    }};

    public static String lookupServer(String uuid) {
        String returnString = servers.get(uuid);
        returnString = returnString == null ? SERVICE_NOT_FOUND : returnString;
        Bugsnag.leaveBreadcrumb("lookupServer uuid: " + uuid + " server: " + returnString);
        return returnString;
    }

    public static String lookupChar(String uuid) {
        String returnString = characteristics.get(uuid);
        returnString = returnString == null ? CHAR_NOT_FOUND : returnString;
        Bugsnag.leaveBreadcrumb("lookupChar uuid: " + uuid + " char: " + returnString);
        return returnString;
    }
}
