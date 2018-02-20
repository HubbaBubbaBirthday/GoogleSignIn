package com.blitz.GoogleSignIn;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


public class DetailsActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignInActivity";
    private TextView userName;
    private TextView email_id;
    private ImageView dp;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        findViewById(R.id.sign_out_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this /* Context */)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent receiveData = getIntent();

        userName = findViewById(R.id.username);
        userName.setText(receiveData.getStringExtra("username"));

        email_id = findViewById(R.id.email_id);
        email_id.setText(receiveData.getStringExtra("email_id"));

        /*dp = findViewById(R.id.dp);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(receiveData.getStringExtra("dp"));
                    InputStream IS = url.openConnection().getInputStream();
                    final Bitmap bmp = BitmapFactory.decodeStream(IS);
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          dp.setImageBitmap(bmp);
                                      }
                                  }
                    );

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // When we get here in an automanager activity the error is likely not
        // resolvable - meaning Google Sign In and other Google APIs will be
        // unavailable.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    // [START signOut]
    private void signOut() {
        Intent prevPage = new Intent( this, MainActivity.class );
        startActivity( prevPage );
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);

    }
    // [END signOut]

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                signOut();
                break;
        }
    }
}
