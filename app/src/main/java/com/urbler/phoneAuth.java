package com.urbler;

import android.app.Activity;
import android.content.Intent;

import android.content.SharedPreferences;

import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;

import com.firebase.ui.auth.ErrorCodes;

import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Arrays;
import java.util.List;

public class phoneAuth extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;
    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {

            SharedPreferences sharedPreferences=getSharedPreferences("reg",MODE_PRIVATE);

            Boolean b= sharedPreferences.getBoolean("firstStart",false);

            // already signed in

            if (!b){

                startActivity(new Intent(phoneAuth.this, MainActivity.class));
                finish();


            }

            else
                startActivity(new Intent(phoneAuth.this, register.class));
        } else {
            List<AuthUI.IdpConfig> providers = Arrays.asList(

                    new AuthUI.IdpConfig.PhoneBuilder().build()
                  );
// Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
    }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.

        if (requestCode == RC_SIGN_IN) {

            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in

            if (resultCode ==RESULT_OK) {

                startActivity(new Intent(phoneAuth.this, register.class));

                finish();

            } else {

                // Sign in failed

                if (response == null) {

                    // User pressed back button
                    Log.e("Login", "Login canceled by User");

                    return;

                }



                if (response.getError().getErrorCode()== ErrorCodes.NO_NETWORK) {

                    Log.e("Login", "No Internet Connection");

                    return;

                }



                if (response.getError().getErrorCode()== ErrorCodes.UNKNOWN_ERROR) {

                    Log.e("Login", "Unknown Error");



                    return;

                }

            }



            Log.e("Login", "Unknown sign in response");

        }

    }



    @Override

    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }}
