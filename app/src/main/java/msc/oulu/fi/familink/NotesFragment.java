package msc.oulu.fi.familink;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by pramodguruprasad on 18/04/16.
 */
public class NotesFragment extends Fragment{
    private OnFragmentInteractionListener mListener;
    LayoutInflater inflater;

    public NotesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocationFragment.
     */

    public static Fragment getInstance() {
        Fragment fragment = new NotesFragment();
        return fragment;
    }

    RecyclerView rv;
    ArrayList<String> notesTexts;
    NotesAdapter adapter;

    Firebase myFirebaseRef;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notesTexts = new ArrayList<String>();

        notesTexts.add("Change car tyres to summer tyres");
        notesTexts.add("Pay Paul 8e for pizza");
        notesTexts.add("Gym timings: Mon-Fri 6pm to 7:30pm");

//        myFirebaseRef = new Firebase("https://msc-familink.firebaseio.com/");
//
//        myFirebaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                notesTexts.add(dataSnapshot.child("notes").getValue().toString());
//            }
//
//            @Override
//            public void onCancelled(FirebaseError firebaseError) {
//                Log.d("Firebase Erroe",firebaseError.getDetails());
//            }
//        });

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.notes_recycler_view);
        GridLayoutManager llm = new GridLayoutManager(getActivity(), 2);

        rv.setLayoutManager(llm);

        adapter = new NotesAdapter(getActivity(), notesTexts);
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.notesAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }
        });

    }

    private void addNewNote() {
        notesTexts.add("");
        adapter.notifyItemInserted(notesTexts.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes,container, false);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
