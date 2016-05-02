package msc.oulu.fi.familink;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;
import java.util.Random;

import msc.oulu.fi.familink.chat.Chat;
import msc.oulu.fi.familink.chat.ChatListAdapter;
import msc.oulu.fi.familink.utils.FirebaseHelper;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ChatFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ChatFragment#getInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends ListFragment {
    private static final String TAG = ChatFragment.class.getSimpleName();

    private String mUsername;
    private Firebase myFirebaseRefChat;
    private ValueEventListener mConnectedListener;
    private ChatListAdapter mChatListAdapter;

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

        setupUsername();
        // Initialize SDKs
        myFirebaseRefChat = FirebaseHelper.myFirebaseRef.child("chat");
    }

    @Override
    public void onStart() {
        super.onStart();

        setListViewAdapter();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setListViewAdapter();
    }

    private void setListViewAdapter() {

//        final ListView listView = (ListView) getActivity().findViewById(R.id.list);
        mChatListAdapter = new ChatListAdapter(myFirebaseRefChat.limit(50), getActivity(), R.layout.chat, mUsername);

        // take advantage of ListFragment implementation
        this.setListAdapter(mChatListAdapter);
//        listView.setAdapter(mChatListAdapter);

        mChatListAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                getListView().setSelection(mChatListAdapter.getCount() - 1);
            }
        });

        mConnectedListener = myFirebaseRefChat.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = (Boolean) dataSnapshot.getValue();
                if (connected) {
                    Toast.makeText(getActivity(), "Connected to Firebase", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        myFirebaseRefChat.getRoot().child(".info/connected").removeEventListener(mConnectedListener);
        mChatListAdapter.cleanup();
    }

    private void setupUsername() {
        SharedPreferences mSharedPreferences = getActivity().getSharedPreferences(MainActivity.FAMILINK_PREFERENCES, 0);
        mUsername = mSharedPreferences.getString(LoginActivity.USERNAME, "unknown");
        if (mUsername == null) {
            Random r = new Random();
            // Assign a random user name if we don't have one saved.
            mUsername = "JavaUser" + r.nextInt(100000);
            mSharedPreferences.edit().putString("username", mUsername).commit();
        }

        Log.d(TAG, "Username is " + mUsername);
    }

    private void sendMessage() {
        EditText inputText = (EditText) getActivity().findViewById(R.id.chatMessage);
        String input = inputText.getText().toString();
        if (!input.equals("")) {
            // Create our 'model', a Chat object
            Chat chat = new Chat(input, mUsername, new Date(System.currentTimeMillis()));
            // Create a new, auto-generated child of that chat location, and save our chat data there
            myFirebaseRefChat.push().setValue(chat);
            Log.d(TAG, "Pushed message '" + input + "' by " + mUsername);
            inputText.setText("");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        EditText chatText = (EditText) view.findViewById(R.id.chatMessage);
        chatText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    sendMessage();
                }
                return true;
            }
        });

        view.findViewById(R.id.sendChatMessageIV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });

        return view;
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

}
