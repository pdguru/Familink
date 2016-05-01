package msc.oulu.fi.familink;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.model.QBSession;
import com.quickblox.chat.QBChatService;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBSettings;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.model.QBUser;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.XMPPConnection;

import msc.oulu.fi.familink.chat.ChatListAdapter;
import msc.oulu.fi.familink.utils.ApplicationSingleton;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {
    private static final String TAG = ChatFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private QBChatService chatService;
    private QBUser user;

    private OnFragmentInteractionListener mListener;

    public ChatFragment() {
        // Required empty public constructor
    }

    public static ChatFragment getInstance() {
        ChatFragment fragment = new ChatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize SDKs
        QBSettings.getInstance().init(getActivity().getApplicationContext(), ApplicationSingleton.APP_ID, ApplicationSingleton.AUTH_KEY, ApplicationSingleton.AUTH_SECRET);
        QBSettings.getInstance().setAccountKey(ApplicationSingleton.ACCOUNT_KEY);

        // UI elements
        ListView listView = (ListView) getActivity().findViewById(R.id.chatHistory);
        listView.setAdapter(new ChatListAdapter());

        chatService = QBChatService.getInstance();
        chatService.addConnectionListener(chatConnectionListener);

        user = new QBUser();
        user.setEmail(getActivity().getApplicationContext().getSharedPreferences("familinkPreference", Context.MODE_PRIVATE).getString("user", null));
        user.setPassword(AccessToken.getCurrentAccessToken().getToken());

        login(user, new QBEntityCallback() {
            @Override
            public void onSuccess(Object o, Bundle bundle) {
                Toast.makeText(getActivity().getApplicationContext(), "Logged in to chat", Toast.LENGTH_SHORT);

                //TODO make Chat window
            }

            @Override
            public void onError(QBResponseException e) {
                Toast.makeText(getActivity().getApplicationContext(), "Could not log in to chat", Toast.LENGTH_SHORT);
            }
        });




    }

    public void login(final QBUser user, final QBEntityCallback callback) {

        // Create REST API session
        //
        QBAuth.createSession(user, new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession session, Bundle args) {

                user.setId(session.getUserId());

                // login to Chat
                //
                chatService.login(user, new QBEntityCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, Bundle b) {
                        callback.onSuccess(result, b);
                    }

                    @Override
                    public void onError(QBResponseException errors) {
                        callback.onError(errors);
                    }
                });
            }

            @Override
            public void onError(QBResponseException errors) {
                callback.onError(errors);
            }
        });
    }

    public void logout() {
        chatService.logout(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle bundle) {
                chatService.removeConnectionListener(chatConnectionListener);
            }

            @Override
            public void onError(QBResponseException list) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    ConnectionListener chatConnectionListener = new ConnectionListener() {
        @Override
        public void connected(XMPPConnection connection) {
            Log.i(TAG, "connected");
        }

        @Override
        public void authenticated(XMPPConnection connection, boolean authenticated) {
            Log.i(TAG, "authenticated");
        }

        @Override
        public void connectionClosed() {
            Log.i(TAG, "connectionClosed");
        }

        @Override
        public void connectionClosedOnError(final Exception e) {
            Log.i(TAG, "connectionClosedOnError: " + e.getLocalizedMessage());
        }

        @Override
        public void reconnectingIn(final int seconds) {
            if (seconds % 5 == 0) {
                Log.i(TAG, "reconnectingIn: " + seconds);
            }
        }

        @Override
        public void reconnectionSuccessful() {
            Log.i(TAG, "reconnectionSuccessful");
        }

        @Override
        public void reconnectionFailed(final Exception error) {
            Log.i(TAG, "reconnectionFailed: " + error.getLocalizedMessage());
        }
    };
}
