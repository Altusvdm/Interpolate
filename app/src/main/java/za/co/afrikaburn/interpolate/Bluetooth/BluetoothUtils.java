package za.co.afrikaburn.interpolate.Bluetooth;

import android.bluetooth.BluetoothGattCharacteristic;

import com.bugsnag.android.Bugsnag;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Altus on 2015/04/21.
 */
public class BluetoothUtils {

    public static final String SERVICE_NOT_FOUND = "Unknown Service";

    public static final String SERVICE_UUID = "44656E64726974696353797374656D70";
    public static final Map<String , String> servers = new HashMap<String , String>() {{
        put(SERVICE_UUID,    "Cube Service");
    }};

    public static final String CHAR_NOT_FOUND = "Unknown Characteristic";

    public static final String CHAR_COMMAND_UUID = "44656E64726974696353797374656D71";
    public static final String CHAR_SPEED_UUID = "44656E64726974696353797374656D72";
    public static final String CHAR_PULSE_BRIGHTNESS_UUID = "44656E64726974696353797374656D73";
    public static final String CHAR_TAP_SENS_UUID = "44656E64726974696353797374656D74";
    public static final String CHAR_WAVE_WIDTH_UUID = "44656E64726974696353797374656D75";
    public static final String CHAR_LINEAR_ANGLE_UUID = "44656E64726974696353797374656D76";
    public static final String CHAR_RAD_X_UUID = "44656E64726974696353797374656D77";
    public static final String CHAR_RAD_Y_UUID = "44656E64726974696353797374656D78";
    public static final String CHAR_PULSE_WIDTH_UUID = "44656E64726974696353797374656D79";
    public static final String CHAR_PULSE_SPEED_UUID = "44656E64726974696353797374656D7A";
    public static final String CHAR_TAP_LIMIT_UUID = "44656E64726974696353797374656D7B";
    public static final String CHAR_HUE_UUID = "44656E64726974696353797374656D7C";

    public static final Map<String , String> characteristics = new HashMap<String , String>() {{
        put(CHAR_COMMAND_UUID, "Command (mode/event)");
        put(CHAR_SPEED_UUID, "Speed");
        put(CHAR_PULSE_BRIGHTNESS_UUID, "Pulse Brightness");
        put(CHAR_TAP_SENS_UUID, "Tap Sensitivity");
        put(CHAR_WAVE_WIDTH_UUID, "Wave Width");
        put(CHAR_LINEAR_ANGLE_UUID, "Linear Angle");
        put(CHAR_RAD_X_UUID, "Radial X");
        put(CHAR_RAD_Y_UUID, "Radial Y");
        put(CHAR_PULSE_WIDTH_UUID, "Pulse Width");
        put(CHAR_PULSE_SPEED_UUID, "Pulse Speed");
        put(CHAR_TAP_LIMIT_UUID, "Tap Limit");
    }};

    public static final Map<String, Integer> char_size = new HashMap<String, Integer>() {{
        put(CHAR_COMMAND_UUID, 1);
        put(CHAR_SPEED_UUID, 2);
        put(CHAR_PULSE_BRIGHTNESS_UUID, 2);
        put(CHAR_TAP_SENS_UUID, 1);
        put(CHAR_WAVE_WIDTH_UUID, 1);
        put(CHAR_LINEAR_ANGLE_UUID, 1);
        put(CHAR_RAD_X_UUID, 2);
        put(CHAR_RAD_Y_UUID, 2);
        put(CHAR_PULSE_WIDTH_UUID, 2);
        put(CHAR_PULSE_SPEED_UUID, 2);
        put(CHAR_TAP_LIMIT_UUID, 1);
        put(CHAR_HUE_UUID, 2);
    }};

    public static final Map<Integer, String>  modeNameMap = new HashMap<Integer, String>() {{
       put(0, "Solid");
       put(1, "Radial");
       put(2, "Linear");
       put(3, "Solid Jump");
       put(4, "Set Square Pulse");
       put(5, "Set Cross Pulse");
    }};

    public static final Map<Integer, Integer> pulseValueMap = new HashMap<Integer, Integer>() {{
        put(0, 128); //Square
        put(1, 129); //Cross
    }};

    public static Map<String, BluetoothGattCharacteristic> charaMap = new HashMap<String, BluetoothGattCharacteristic>();

    private static String getKeyUUID(String uuid) {
        if (uuid == null) {
            return "";
        }

        return uuid.replace("-", "").toUpperCase();
    }


    public static String lookupServer(String uuid) {
        String returnString = servers.get(getKeyUUID(uuid));
        returnString = returnString == null ? SERVICE_NOT_FOUND : returnString;
        Bugsnag.leaveBreadcrumb("lookupServer uuid: " + uuid + " server: " + returnString);
        return returnString;
    }

    public static String lookupChar(String uuid) {
        String returnString = characteristics.get(getKeyUUID(uuid));
        returnString = returnString == null ? CHAR_NOT_FOUND : returnString;
        Bugsnag.leaveBreadcrumb("lookupChar uuid: " + uuid + " char: " + returnString);
        return returnString;
    }

    public static void saveChara(BluetoothGattCharacteristic chara) {
        charaMap.put(getKeyUUID(chara.getUuid().toString()), chara);
    }


}
