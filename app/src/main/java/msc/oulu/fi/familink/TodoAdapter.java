package msc.oulu.fi.familink;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pramodguruprasad on 30/04/16.
 */
public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {


    ArrayList<String> texts;
    public TodoAdapter(ArrayList<String> todoTexts) {
        texts = todoTexts;
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        EditText editText;
        CheckBox checkBox;
        TextView textView;

        TodoViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.todo_cardview);
            editText = (EditText)itemView.findViewById(R.id.todoText);
            checkBox = (CheckBox) itemView.findViewById(R.id.todoCB);
            textView = (TextView) itemView.findViewById(R.id.todoAddedBy);

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
    public TodoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_todo, parent, false);
        TodoViewHolder tvh = new TodoViewHolder(view);
        return tvh;
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
    public void onBindViewHolder(TodoViewHolder holder, int position) {
        holder.editText.setText(texts.get(position));
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
