package msc.oulu.fi.familink;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by pramodguruprasad on 18/04/16.
 */
public class ReminderFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    LayoutInflater inflater;

    public ReminderFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LocationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment getInstance() {
        Fragment fragment = new ReminderFragment();
        return fragment;
    }

    RecyclerView rv;
    ArrayList<ReminderAndtodoObject> reminders;
    ReminderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reminders = new ArrayList<ReminderAndtodoObject>();

        ReminderAndtodoObject obj1 = new ReminderAndtodoObject();
        obj1.text = "Get milk and eggs";
        obj1.addedBy = "Claire";
        obj1.checked = false;
        reminders.add(obj1);

        ReminderAndtodoObject obj2 = new ReminderAndtodoObject();
        obj2.text = "Fix kitchen sink";
        obj2.addedBy = "Claire";
        obj2.checked = true;
        reminders.add(obj2);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = (RecyclerView) view.findViewById(R.id.reminder_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(llm);

        adapter = new ReminderAdapter(reminders);
        rv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.reminderAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderAndtodoObject obj = new ReminderAndtodoObject();
                obj.text = "";
                obj.addedBy = "You";
                obj.checked = false;
                reminders.add(obj);

                adapter.notifyItemInserted(reminders.size());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_reminder, container, false);
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

    private class ReminderObject {
        String text;
        boolean checkedOff;
        String addedBy;
    }
}
