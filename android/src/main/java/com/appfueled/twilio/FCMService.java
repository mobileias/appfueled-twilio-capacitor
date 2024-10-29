import android.content.Intent;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FCMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().containsKey("call")) {
            Log.i("FCMService", "Incoming call notification received.");

            // Start IncomingCallService to display the incoming call UI
            Intent serviceIntent = new Intent(this, IncomingCallService.class);
            serviceIntent.putExtra("callData", remoteMessage.getData().get("call"));
            ContextCompat.startForegroundService(this, serviceIntent);
        }
    }
}
