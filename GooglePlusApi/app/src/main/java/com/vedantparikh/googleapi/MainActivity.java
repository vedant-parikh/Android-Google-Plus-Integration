package com.vedantparikh.googleapi;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;


public class MainActivity extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener, View.OnClickListener {

    private static final int RC_SIGN_IN = 0;
    private boolean SignIn_Click;
    private GoogleApiClient GoogleApi;
    private boolean Intent_Progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        GoogleApi = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        findViewById(R.id.sign_in_button).setOnClickListener(this);
    }
    protected void onStart() {
        super.onStart();
        GoogleApi.connect();
    }

    protected void onStop() {
        super.onStop();
        if (GoogleApi.isConnected()) {
            GoogleApi.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!Intent_Progress) {
            if (SignIn_Click && result.hasResolution()) {
                try {
                    result.startResolutionForResult(this, RC_SIGN_IN);
                    Intent_Progress = true;
                } catch (IntentSender.SendIntentException e) {
                    Intent_Progress = false;
                    GoogleApi.connect();
                }
            }
        }
    }

    public void onClick(View view) {
        if (view.getId() == R.id.sign_in_button && !GoogleApi.isConnecting()) {
            SignIn_Click = true;
            GoogleApi.connect();
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        SignIn_Click = false;
        Toast.makeText(this, "connected", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,ShareActivity.class));
    }

    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                SignIn_Click = false;
            }
            Intent_Progress = false;
            if (!GoogleApi.isConnected()) {
                GoogleApi.reconnect();
            }
        }
    }

    public void onConnectionSuspended(int cause) {
        GoogleApi.connect();
    }
}
