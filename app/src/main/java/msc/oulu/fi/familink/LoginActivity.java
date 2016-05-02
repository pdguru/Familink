package msc.oulu.fi.familink;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import msc.oulu.fi.familink.utils.FirebaseHelper;

public class LoginActivity extends AppCompatActivity {

    private final static String TAG = LoginActivity.class.getSimpleName();

    public static final String EMAIL = "email";
    public static final String USERNAME = "name";
    public static final String FRIEND_ID = "id";
    public static final String FRIEND_NAME = "name";
    public static final String FRIENDS = "friends";
    public static final String PROFILE_PICTURE = "profile_picture";

    SharedPreferences mSharedPreferences;

    private LoginButton mLoginButton;
    private String readPermissions = "email,user_friends";
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        mSharedPreferences = getSharedPreferences(MainActivity.FAMILINK_PREFERENCES, Context.MODE_PRIVATE);
        mCallbackManager = CallbackManager.Factory.create();

        FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Facebook login successful");
                AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                FirebaseHelper.onFacebookAccessTokenChange(loginResult.getAccessToken());
                final SharedPreferences.Editor editor = mSharedPreferences.edit();
                requestFacebookInfo(editor);

                finish();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "Facebook login not successful");

            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "Facebook aborted due to error");
            }
        };

        if (AccessToken.getCurrentAccessToken() != null) {
            if (AccessToken.getCurrentAccessToken().isExpired()) {
                LoginManager.getInstance().registerCallback(mCallbackManager, mFacebookCallback);
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_friends"));
            }
            requestFacebookInfo(mSharedPreferences.edit());
            Log.d(TAG, "AccessToken retrieved as " + AccessToken.getCurrentAccessToken().toString());
            finish();
        } else {
            mLoginButton = (LoginButton) findViewById(R.id.login_button);

            mLoginButton.setReadPermissions(readPermissions);
            mLoginButton.registerCallback(mCallbackManager, mFacebookCallback);
        }
    }

    private void requestFacebookInfo(final SharedPreferences.Editor editor) {
        // retrieve email
        GraphRequest request = GraphRequest.newMeRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    String mEmail;
                    String mName;
                    String mProfilePicUrl;

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            mEmail = object.getString(EMAIL);
                            Log.d(TAG, EMAIL + " is " + mEmail);
                            mName = object.getString(USERNAME);
                            Log.d(TAG, USERNAME + " is " + mName);
                            mProfilePicUrl = object.getJSONObject("picture").getJSONObject("data").getString("url");
                            Log.d(TAG, PROFILE_PICTURE + " is " + mProfilePicUrl);

                            editor.putString(EMAIL, mEmail);
                            editor.putString(USERNAME, mName);
                            editor.putString(PROFILE_PICTURE, mProfilePicUrl);

                            editor.apply();
                        } catch (JSONException e) {
                            Log.e(TAG, e.getLocalizedMessage());
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,picture{url}");
        request.setParameters(parameters);
        request.executeAsync();

        // retrieve friends who use app as well
        request = GraphRequest.newMyFriendsRequest(
                AccessToken.getCurrentAccessToken(),
                new GraphRequest.GraphJSONArrayCallback() {
                    @Override
                    public void onCompleted(JSONArray array, GraphResponse response) {
                        Set<String> friends = new HashSet<String>();

                        for (int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject friend = array.getJSONObject(i);
                                String mFriendId = friend.getString(FRIEND_ID);
                                String mFriendName = friend.getString(FRIEND_NAME);

                                Log.d(TAG, FRIEND_ID + ": " + mFriendId);
                                Log.d(TAG, FRIEND_NAME + ": " + mFriendName);

                                friends.add(mFriendId + "/" + mFriendName);

                            } catch (JSONException e) {
                                Log.e(TAG, e.getLocalizedMessage());
                            }

                        }
                        Log.d(TAG, "# of friends: " + friends.size());
                        editor.putStringSet(FRIENDS, friends);
                        editor.apply();
                    }
                });
        parameters = new Bundle();
        parameters.putString("field", "installed");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onBackPressed() {
        // end application
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
