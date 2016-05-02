package msc.oulu.fi.familink.TodoAndReminder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import msc.oulu.fi.familink.R;

/**
 * Created by pramodguruprasad on 30/04/16.
 */
public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {


    ArrayList<ReminderAndtodoObject> texts;
    public ReminderAdapter(ArrayList<ReminderAndtodoObject> reminderTexts) {
        texts = reminderTexts;
    }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        EditText editText;
        CheckBox checkBox;
        TextView textView;

        ReminderViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.reminder_cardview);
            editText = (EditText)itemView.findViewById(R.id.reminderText);
            checkBox = (CheckBox) itemView.findViewById(R.id.reminderCB);
            textView = (TextView) itemView.findViewById(R.id.reminderAddedBy);

        }
    }


    @Override
    public ReminderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_reminders, parent, false);
        ReminderViewHolder rvh = new ReminderViewHolder(view);
        return rvh;
    }


    @Override
    public void onBindViewHolder(ReminderViewHolder holder, int position) {
        holder.editText.setText(texts.get(position).text);
        holder.textView.setText(texts.get(position).addedBy);
    }

    @Override
    public int getItemCount() {
        return texts.size();
    }

}
