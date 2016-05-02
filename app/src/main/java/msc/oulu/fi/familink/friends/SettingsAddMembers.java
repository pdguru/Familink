package msc.oulu.fi.familink.friends;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import msc.oulu.fi.familink.LoginActivity;
import msc.oulu.fi.familink.MainActivity;
import msc.oulu.fi.familink.R;

/**
 * Created by pramodguruprasad on 01/05/16.
 */
public class SettingsAddMembers extends Activity {

    public static final String TAG = SettingsAddMembers.class.getSimpleName();

    SharedPreferences mSharedPreferences;

    AddMembersListAdapter mAddMembersListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_contacts_list);

        // get friends from shared preferences
        mSharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.FAMILINK_PREFERENCES, Context.MODE_PRIVATE);

        Set<String> tmp2 = mSharedPreferences.getStringSet(LoginActivity.FRIENDS, new HashSet<String>(5));
        Object[] friends = mSharedPreferences.getStringSet(LoginActivity.FRIENDS, new HashSet<String>(5)).toArray();

        List<Map<String, String>> data = new ArrayList<>();
        for (int i = 0; i < friends.length; i++) {
            Map<String, String> tmp = new HashMap<>();
            String name = friends[i].toString();
            name = name.substring(name.indexOf("/")+1);
            tmp.put("" + i, name);
            Log.d(TAG, name);
            data.add(tmp);
        }

        String[] from = {"#", "Name"};
        int[] to = {1};

        mAddMembersListAdapter = new AddMembersListAdapter(getApplicationContext(), data);

        ((ListView)findViewById(R.id.friendsList)).setAdapter(mAddMembersListAdapter);

//        mAddMembersListAdapter.registerDataSetObserver();

    }

    public class AddMembersListAdapter extends BaseAdapter {
        Context ctx;
        List<Map<String,String>> data;

        AddMembersListAdapter(Context ctx, List<Map<String,String>> data){
            this.ctx = ctx;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.friend,null);
            ((TextView)view.findViewById(R.id.friends_name)).setText(data.get(position).get(position));
            return view;
        }
    }
}
