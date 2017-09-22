package dk.lndesign.tunnels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import timber.log.Timber;

/**
 * Application class for Tunnels app.
 * @author Lars Nielsen <lars@lndesign.dk>.
 */
public class TunnelsApplication extends Application {

    private static TunnelsApplication sInstance;

    public static TunnelsApplication getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    public boolean checkPlayServices() {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);

        return (resultCode == ConnectionResult.SUCCESS);
    }

    private static class CrashReportingTree extends Timber.Tree {
        @Override protected void log(int priority, String tag, String message, Throwable t) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            switch (priority) {
                case Log.WARN:
                    if (t != null) {
                        Log.w(tag, message, t);
                    } else {
                        Log.w(tag, message);
                    }
                    break;
                case Log.ERROR:
                    if (t != null) {
                        Log.e(tag, message, t);
                    } else {
                        Log.e(tag, message);
                    }
                    break;
                default:
                    Log.println(priority, tag, message);
                    break;
            }
        }
    }
}
