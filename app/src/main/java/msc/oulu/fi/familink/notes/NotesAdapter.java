package msc.oulu.fi.familink.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import msc.oulu.fi.familink.R;

/**
 * Created by pramodguruprasad on 29/04/16.
 */
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {


    ArrayList<String> texts;
    Context ctx;
    public NotesAdapter(Context context, ArrayList<String> notesTexts)
    {
        ctx = context;
        texts = notesTexts;
    }

    public static class NotesViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        EditText editText;

        NotesViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.notes_cardview);
            editText = (EditText)itemView.findViewById(R.id.notesText);
        }
    }

    /**
     * Called when RecyclerView needs a new { ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new ViewHolder will be used to display items of the adapter using
     * { #onBindViewHolder(ViewHolder, int, List)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     */
    @Override
    public NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_notes, parent, false);
        NotesViewHolder nvh = new NotesViewHolder(view);
        return nvh;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p/>
     * Override  instead if Adapter can
     * handle effcient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(NotesViewHolder holder, final int position) {
        holder.editText.setText(texts.get(position));

        holder.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//                Firebase myFirebaseRef = new Firebase("https://msc-familink.firebaseio.com/");
//
//                Firebase notesRef = myFirebaseRef.child("notes");
//                Map<String, Object> note = new HashMap<String, Object>();
//                note.put(getUsername(), s.toString());
//                notesRef.updateChildren(note);

            }
        });
    }

    private String getUsername() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences("familinkPrefernce", Context.MODE_PRIVATE);
        String username[] = sharedPreferences.getString("user","").split("@");
        return username[0];
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return texts.size();
    }
}
