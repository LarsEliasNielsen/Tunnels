package dk.lndesign.tunnels;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.iid.FirebaseInstanceId;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!TunnelsApplication.getInstance().checkPlayServices()) {
            Timber.e("Google Play services is not available");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.test_notification_channel_id);
            String channelName = getString(R.string.test_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        Button getFcmTokenButton = findViewById(R.id.get_fcm_token);
        getFcmTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timber.i(FirebaseInstanceId.getInstance().getToken());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!TunnelsApplication.getInstance().checkPlayServices()) {
            Timber.e("Google Play services is not available");
        }
    }
}
