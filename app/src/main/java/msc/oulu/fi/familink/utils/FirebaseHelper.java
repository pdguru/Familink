package msc.oulu.fi.familink.utils;

import android.util.Log;

import com.facebook.AccessToken;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

/**
 * Created by Simon on 01. Mai. 2016.
 */
public class FirebaseHelper {

    private static final String TAG = FirebaseHelper.class.getSimpleName();

    public static final Firebase myFirebaseRef = new Firebase(ApplicationSingleton.FIREBASE_URL);
    private static AuthData mAuthData;

    public static AuthData getmAuthData() {
        if (mAuthData == null) {
            try {
                onFacebookAccessTokenChange(AccessToken.getCurrentAccessToken());
            } catch (Exception e) {
                Log.e(TAG, "Login not possible: " + e.getMessage());
            }
        }
        return mAuthData;
    }

    public static void onFacebookAccessTokenChange(AccessToken token) {
        if (token != null) {
            myFirebaseRef.authWithOAuthToken("facebook", token.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    // The Facebook user is now authenticated with your Firebase app
                    mAuthData = authData;
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    // there was an error
                    Log.e(TAG, firebaseError.getMessage());
                }
            });
        } else {
        /* Logged out of Facebook so do a logout from the Firebase app */
            myFirebaseRef.unauth();
        }
    }
}
