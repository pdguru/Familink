package msc.oulu.fi.familink.location;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;

import msc.oulu.fi.familink.MainActivity;
import msc.oulu.fi.familink.chat.ChatFragment;
import msc.oulu.fi.familink.R;


public class LocationFragment extends Fragment implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private OnFragmentInteractionListener mListener;
    private MapFragment fragment;
    private GoogleMap gMap;
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private double lastLat, lastLong;

    TextView dateTV;
    ImageView profilePic;

    Firebase myFirebaseRef;
    String FIREBASE_URL = "https://msc-familink.firebaseio.com/";
    private ValueEventListener mConnectedListener;

    public LocationFragment() {
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
        Fragment fragment = new LocationFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        myFirebaseRef = new Firebase(FIREBASE_URL);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FragmentManager fm = getChildFragmentManager();
        fragment = (MapFragment) fm.findFragmentById(R.id.location_map);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                setupMap(googleMap);
            }
        });

    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dateTV = (TextView) view.findViewById(R.id.locDate);
        view.findViewById(R.id.locCallButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:+918951821648"));
                startActivity(callIntent);
            }
        });

        view.findViewById(R.id.locMsgButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment chatFragment = ChatFragment.getInstance();
                getFragmentManager().beginTransaction().replace(R.id.navigationRootRL, chatFragment) .commit();
            }
        });

        profilePic = (ImageView) view.findViewById(R.id.contactProfilePic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(),v);
                popupMenu.getMenu().add(Menu.NONE, 1, Menu.NONE, "Simon Stemper" );
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Log.d("item id",""+item.getItemId());
                        return false;
                    }
                });
            }
        });
    }

    private void setupMap(GoogleMap googleMap) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap = googleMap;
        gMap.setMyLocationEnabled(true);

        //get map points from firebase and move map
        myFirebaseRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println("There are " + dataSnapshot.getChildrenCount() + " data");
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
//                    Log.d("snapshot",dataSnapshot.child("Simon Stemper").getValue().toString());
                    LocationObject locObj = postSnapshot.getValue(LocationObject.class);
                    Log.d("Time", locObj.getDate().toString());
                    Log.d("LatLong", locObj.getLatitude().toString() + ";" + locObj.getLongitude().toString());
                    gMap.clear();
                    gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locObj.getLatitude(), locObj.getLongitude()), 14));
                    gMap.addMarker(new MarkerOptions()
                            .position(new LatLng(locObj.getLatitude(), locObj.getLongitude()))
                            .title(locObj.getUsername()));

                    if(dateTV!=null){
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                        dateTV.setText(sdf.format(locObj.getDate()));
                    }

                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {

        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
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

    @Override
    public void onConnected(Bundle connectionHint) {
        getLastKnownLocation();
    }

    private void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lastLat = mLastLocation.getLatitude();
            lastLong = mLastLocation.getLongitude();
            Log.d("Last known location",mLastLocation.toString());
            if (mLastLocation != null) {
                gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLat, lastLong), 14));
                //save my location to firebase
                putOnFirebase(lastLat,lastLong);
            }
        }
        else Log.d("Last known location","Unknown");
    }

    private void putOnFirebase(Double lat, double lng) {
        mConnectedListener = myFirebaseRef.getRoot().child(".info/connected").addValueEventListener(new ValueEventListener() {
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

        LocationObject obj = new LocationObject(getUsername(), lat, lng, new Date(System.currentTimeMillis()));
        // Create a new, auto-generated child of that chat location, and save our chat data there
//        Log.d(TAG, "Pushed message '" + input + "' by " + mUsername);
//        myFirebaseRef.push().setValue(obj);
        myFirebaseRef.child("/userLocation").child(getUsername()).setValue(obj);

    }

    private String getUsername() {
         SharedPreferences sharedPreferences = getActivity().getSharedPreferences("familink_preferences", Context.MODE_PRIVATE);
//        String username[] = sharedPreferences.getString("email","").split("@");
//        return username[0];

        return sharedPreferences.getString("name","");
    }


    @Override
    public void onConnectionSuspended(int i) {
        Log.d("*******************","Connection suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d("*******************","Connection failed");

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
