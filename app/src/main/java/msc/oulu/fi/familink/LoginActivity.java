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
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    public static final String ACCESS_TOKEN = "access_token";

    SharedPreferences mSharedPreferences;

    private LoginButton mLoginButton;
    private String readPermissions = "email,user_friends";
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FacebookSdk.sdkInitialize(getApplicationContext());

        if (savedInstanceState != null) {
            AccessToken.setCurrentAccessToken((AccessToken) savedInstanceState.get(ACCESS_TOKEN));
        }
        if (AccessToken.getCurrentAccessToken() != null && !AccessToken.getCurrentAccessToken().isExpired()) {
            finish();
        }

        mSharedPreferences = getSharedPreferences(MainActivity.FAMILINK_PREFERENCES, Context.MODE_PRIVATE);
        setContentView(R.layout.activity_login);
        mCallbackManager = CallbackManager.Factory.create();
        mLoginButton = (LoginButton) findViewById(R.id.login_button);

        if (mLoginButton.getText().equals(getString(R.string.com_facebook_loginview_log_out_button))) {
            finish();
        }

        mLoginButton.setReadPermissions(readPermissions);

        mLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "Facebook login successful");
                AccessToken.setCurrentAccessToken(loginResult.getAccessToken());
                FirebaseHelper.onFacebookAccessTokenChange(loginResult.getAccessToken());
                final SharedPreferences.Editor editor = mSharedPreferences.edit();

                // retrieve email
                GraphRequest request = GraphRequest.newMeRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            String mEmail;
                            String mName;

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    mEmail = (String) object.get(EMAIL);
                                    mName = (String) object.get(USERNAME);
                                    editor.putString(EMAIL, mEmail);
                                    editor.putString(USERNAME, mName);

                                    Log.d(TAG, EMAIL + " is " + mEmail);
                                    Log.d(TAG, USERNAME + " is " + mName);

                                    editor.apply();
                                } catch (JSONException e) {
                                    Log.e(TAG, e.getLocalizedMessage());
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email");
                request.setParameters(parameters);
                request.executeAsync();

                // retrieve friends who use app as well
                request = GraphRequest.newMyFriendsRequest(
                        AccessToken.getCurrentAccessToken(),
                        new GraphRequest.GraphJSONArrayCallback() {
                            @Override
                            public void onCompleted(JSONArray array, GraphResponse response) {
                                for (int i = 0; i < array.length(); i++) {
                                    Set<String> friends = new HashSet<String>();

                                    try {
                                        JSONObject friend = array.getJSONObject(i);
                                        String mFriendId = friend.getString(FRIEND_ID);
                                        String mFriendName = friend.getString(FRIEND_NAME);

                                        friends.add(mFriendId + "/" + mFriendName);

                                    } catch (JSONException e) {
                                        Log.e(TAG, e.getLocalizedMessage());
                                    }
                                    editor.putStringSet(FRIENDS, friends);
                                }
                            }
                        });
                parameters = new Bundle();
                parameters.putString("field", "installed");
                request.setParameters(parameters);
                request.executeAsync();

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
        });

        mSharedPreferences = getSharedPreferences(MainActivity.FAMILINK_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(ACCESS_TOKEN, AccessToken.getCurrentAccessToken());

        super.onSaveInstanceState(outState);
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
