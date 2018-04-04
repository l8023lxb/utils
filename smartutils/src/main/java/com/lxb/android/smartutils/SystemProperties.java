package com.lxb.android.smartutils;

import android.util.Log;

/**
 * Created by litan on 18/4/3.
 */

public class SystemProperties {

    static {
        System.loadLibrary("smart_utils");
    }

    public static final int PROP_NAME_MAX = 31;

    private static final String TAG = "SystemProperties";
    private static final String KEY_TOO_LARGE_EXCEPTION = "key.length > " + PROP_NAME_MAX;

    private static native String native_get(String key);

    private static native String native_get(String key, String def);

    private static native int native_get_int(String key, int def);

    private static native long native_get_long(String key, long def);

    private static native boolean native_get_boolean(String key, boolean def);


    /**
     * Get the value for the given key.
     *
     * @param key
     * @return an empty string if the key isn't found
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static String get(String key) {
        if (key.length() > PROP_NAME_MAX) {
            smartLog();
        }
        return native_get(key);
    }

    /**
     * Get the value for the given key.
     *
     * @param key
     * @param def
     * @return if the key isn't found, return def if it isn't null, or an empty string otherwise
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static String get(String key, String def) {
        if (key.length() > PROP_NAME_MAX) {
            smartLog();
        }
        return native_get(key, def);
    }

    /**
     * Get the value for the given key, and return as an integer.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as an integer, or def if the key isn't found or
     * cannot be parsed
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static int getInt(String key, int def) {
        if (key.length() > PROP_NAME_MAX) {
            smartLog();
        }
        return native_get_int(key, def);
    }

    /**
     * Get the value for the given key, and return as a long.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a long, or def if the key isn't found or
     * cannot be parsed
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static long getLong(String key, long def) {
        if (key.length() > PROP_NAME_MAX) {
            smartLog();
        }
        return native_get_long(key, def);
    }

    /**
     * Get the value for the given key, returned as a boolean.
     * Values 'n', 'no', '0', 'false' or 'off' are considered false.
     * Values 'y', 'yes', '1', 'true' or 'on' are considered true.
     * (case sensitive).
     * If the key does not exist, or has any other value, then the default
     * result is returned.
     *
     * @param key the key to lookup
     * @param def a default value to return
     * @return the key parsed as a boolean, or def if the key isn't found or is
     * not able to be parsed as a boolean.
     * @throws IllegalArgumentException if the key exceeds 32 characters
     */
    public static boolean getBoolean(String key, boolean def) {
        if (key.length() > PROP_NAME_MAX) {
            smartLog();
        }
        return native_get_boolean(key, def);
    }

    private static void smartLog() {
        if (BuildConfig.DEBUG) {
            throw new IllegalArgumentException(KEY_TOO_LARGE_EXCEPTION);
        } else {
            Log.e(TAG, KEY_TOO_LARGE_EXCEPTION);
        }
    }

}
