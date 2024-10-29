package com.appfueled.twilio;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
import android.os.Handler;
import android.os.Looper;
import com.getcapacitor.PluginCall;
import com.twilio.voice.Call;
import com.twilio.voice.CallInvite;
import com.twilio.voice.Voice;
import com.twilio.voice.CallException;
import com.twilio.voice.ConnectOptions;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;

public class AppfueledTwilioCapacitor {

    private Call incomingCallConnection = null;  // Incoming call connection
    private Call outgoingCallConnection = null;  // Outgoing call connection
    private boolean isCallActive = false;  // Tracks if a call is active
    private AudioManager audioManager;

    // Method to register the device with Twilio Voice SDK for handling incoming calls
    public void registerIncomingCallConnection(PluginCall call) {
        String token = call.getString("incomingToken");

        if (token != null) {
            Voice.register(token, new RegistrationListener() {
                @Override
                public void onRegistered(String accessToken, String fcmToken) {
                    Log.i("Twilio Registration", "Successfully registered for incoming calls.");
                    sendLogToMainApp("Registered for incoming calls successfully.");
                    resolveOnMainThread(call);
                }

                @Override
                public void onError(CallException e) {
                    String errorMessage = "Error during incoming registration: " + e.getMessage();
                    Log.e("Twilio Registration", errorMessage);
                    sendErrorToMainApp(errorMessage);
                    rejectOnMainThread(call, errorMessage);
                }
            });
        } else {
            rejectOnMainThread(call, "Missing access token for incoming registration");
        }
    }

    // Method to register the device for outgoing calls
    public void registerOutgoingCallConnection(PluginCall call) {
        String token = call.getString("outgoingToken");

        if (token != null) {
            Voice.register(token, new RegistrationListener() {
                @Override
                public void onRegistered(String accessToken, String fcmToken) {
                    Log.i("Twilio Registration", "Successfully registered for outgoing calls.");
                    sendLogToMainApp("Registered for outgoing calls successfully.");
                    resolveOnMainThread(call);
                }

                @Override
                public void onError(CallException e) {
                    String errorMessage = "Error during outgoing registration: " + e.getMessage();
                    Log.e("Twilio Registration", errorMessage);
                    sendErrorToMainApp(errorMessage);
                    rejectOnMainThread(call, errorMessage);
                }
            });
        } else {
            rejectOnMainThread(call, "Missing access token for outgoing registration");
        }
    }

    // Handle incoming call (this will be invoked when the CallInvite is received)
    public void onIncomingCall(CallInvite callInvite) {
        if (callInvite != null) {
            incomingCallConnection = callInvite.accept();  // Accept the incoming call invite
            Log.i("Twilio", "Incoming call accepted.");
            sendLogToMainApp("Incoming call accepted.");
        } else {
            String errorMessage = "Call invite no longer valid.";
            Log.e("Twilio", errorMessage);
            sendErrorToMainApp(errorMessage);
        }
    }

    // Method to accept an incoming call
    public void acceptIncomingCallConnection(PluginCall call) {
        if (incomingCallConnection != null) {
            incomingCallConnection.accept();  // Accept the incoming call
            Log.i("Twilio", "Incoming call accepted.");
            sendLogToMainApp("Incoming call accepted.");
            resolveOnMainThread(call);
        } else {
            String errorMessage = "No incoming call to accept.";
            Log.e("Twilio", errorMessage);
            sendErrorToMainApp(errorMessage);
            rejectOnMainThread(call, errorMessage);
        }
    }

    // Method to reject an incoming call
    public void rejectIncomingCallConnection(PluginCall call) {
        if (incomingCallConnection != null) {
            incomingCallConnection.reject();  // Reject the incoming call
            Log.i("Twilio", "Incoming call rejected.");
            sendLogToMainApp("Incoming call rejected.");
            resolveOnMainThread(call);
        } else {
            String errorMessage = "No incoming call to reject.";
            Log.e("Twilio", errorMessage);
            sendErrorToMainApp(errorMessage);
            rejectOnMainThread(call, errorMessage);
        }
    }

    // Method to initiate an outgoing call with dynamic options
    public void startOutgoingCallConnection(PluginCall call) {
        String toPhoneNumber = call.getString("phoneNumber");  // Required phone number
        JSONObject options = call.getObject("options", new JSONObject());  // Dynamic options

        if (toPhoneNumber != null && !isCallActive) {
            ConnectOptions.Builder connectOptionsBuilder = new ConnectOptions.Builder()
                .params("To", toPhoneNumber);  // Always include the phone number

            try {
                // Iterate over the JSONObject to add all dynamic options
                Iterator<String> keys = options.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    String value = options.getString(key);  // Treat all values as Strings for simplicity
                    connectOptionsBuilder.params(key, value);  // Add dynamic options
                }

                ConnectOptions connectOptions = connectOptionsBuilder.build();

                outgoingCallConnection = Voice.connect(getContext(), connectOptions, new Call.Listener() {
                    @Override
                    public void onConnected(Call call) {
                        Log.i("Twilio", "Outgoing call connected to " + toPhoneNumber);
                        sendLogToMainApp("Outgoing call connected.");
                        isCallActive = true;
                    }

                    @Override
                    public void onDisconnected(Call call, CallException error) {
                        Log.i("Twilio", "Outgoing call disconnected.");
                        sendLogToMainApp("Outgoing call disconnected.");
                        isCallActive = false;
                        if (error != null) {
                            Log.e("Twilio", "Call error: " + error.getMessage());
                            sendErrorToMainApp("Call error: " + error.getMessage());
                        }
                    }

                    @Override
                    public void onConnectFailure(Call call, CallException error) {
                        Log.e("Twilio", "Outgoing call connection failed: " + error.getMessage());
                        sendErrorToMainApp("Outgoing call connection failed: " + error.getMessage());
                        isCallActive = false;
                    }
                });

                resolveOnMainThread(call);
            } catch (JSONException e) {
                Log.e("Twilio", "Failed to parse dynamic options: " + e.getMessage());
                rejectOnMainThread(call, "Failed to parse options: " + e.getMessage());
            }
        } else if (isCallActive) {
            rejectOnMainThread(call, "A call is already active.");
        } else {
            rejectOnMainThread(call, "Missing phone number for outgoing call.");
        }
    }

    // Method to terminate an outgoing call
    public void terminateOutgoingCallConnection(PluginCall call) {
        if (outgoingCallConnection != null) {
            outgoingCallConnection.disconnect();  // Disconnect the outgoing call
            Log.i("Twilio", "Outgoing call terminated.");
            sendLogToMainApp("Outgoing call terminated.");
            isCallActive = false;
            resolveOnMainThread(call);
        } else {
            String errorMessage = "No active outgoing call to terminate.";
            Log.e("Twilio", errorMessage);
            sendErrorToMainApp(errorMessage);
            rejectOnMainThread(call, errorMessage);
        }
    }

    // Helper method to resolve PluginCall on the main thread
    private void resolveOnMainThread(PluginCall call) {
        new Handler(Looper.getMainLooper()).post(() -> call.resolve());
    }

    // Helper method to reject PluginCall on the main thread
    private void rejectOnMainThread(PluginCall call, String message) {
        new Handler(Looper.getMainLooper()).post(() -> call.reject(message));
    }

    // Helper method to send log messages to the Capacitor main app
    private void sendLogToMainApp(String message) {
        Log.i("AppfueledTwilio", message);
        // You can call Capacitor methods to send this log to the frontend (e.g., using Capacitor.Events)
    }

    // Helper method to send error messages to the Capacitor main app
    private void sendErrorToMainApp(String error) {
        Log.e("AppfueledTwilio", error);
        // You can call Capacitor methods to send this error to the frontend (e.g., using Capacitor.Events)
    }

    // Constructor to initialize AudioManager
    public AppfueledTwilioCapacitor(Context context) {
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    // Method to toggle the speaker on or off
    public void toggleSpeaker(PluginCall call) {
        boolean enable = call.getBoolean("enable", false);
        if (audioManager != null) {
            audioManager.setSpeakerphoneOn(enable);
            if (enable) {
                audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
            } else {
                audioManager.setMode(AudioManager.MODE_NORMAL);
            }
            sendLogToMainApp("Speakerphone " + (enable ? "enabled" : "disabled"));
            resolveOnMainThread(call);
        } else {
            sendErrorToMainApp("AudioManager not initialized");
            rejectOnMainThread(call, "AudioManager not initialized");
        }
    }

    // Helper method to resolve PluginCall on the main thread
    private void resolveOnMainThread(PluginCall call) {
        new Handler(Looper.getMainLooper()).post(call::resolve);
    }

    // Helper method to reject PluginCall on the main thread
    private void rejectOnMainThread(PluginCall call, String message) {
        new Handler(Looper.getMainLooper()).post(() -> call.reject(message));
    }

    // Helper method to send log messages to the Capacitor main app
    private void sendLogToMainApp(String message) {
        Log.i("AppfueledTwilio", message);
        // Optionally, use Capacitor.Events to send logs to the frontend
    }

    // Helper method to send error messages to the Capacitor main app
    private void sendErrorToMainApp(String error) {
        Log.e("AppfueledTwilio", error);
        // Optionally, use Capacitor.Events to send errors to the frontend
    }
}
