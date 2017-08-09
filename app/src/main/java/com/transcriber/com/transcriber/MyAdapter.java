package com.transcriber.com.transcriber;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.transcriber.com.transcriber.data.Contract;



// added textviews and some information that was missing

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemHolder> {

    private Cursor cursor;
    private ItemClickListener listener;
    private String TAG = "todolistadapter";

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.item, parent, false);
        ItemHolder holder = new ItemHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(holder, position);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public interface ItemClickListener {
        void onItemClick(int pos, String title, String text, long id, String category);
    }

    public MyAdapter(Cursor cursor, ItemClickListener listener) {
        this.cursor = cursor;
        this.listener = listener;
    }

    public void swapCursor(Cursor newCursor){
        if (cursor != null) cursor.close();
        cursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvText;
        String sTitle;
        String sText;
        long id;
        String category;


        TextView mCategottyTV;


        ItemHolder(View view) {
            super(view);
            mCategottyTV = (TextView) view.findViewById(R.id.tv_categotry);
            tvTitle = (TextView) view.findViewById(R.id.title);
            tvText = (TextView) view.findViewById(R.id.text);

            view.setOnClickListener(this);
        }

        public void bind(ItemHolder holder, int pos) {
            cursor.moveToPosition(pos);
            id = cursor.getLong(cursor.getColumnIndex(Contract.TABLE_TODO._ID));
            Log.d(TAG, "deleting id: " + id);

            sTitle = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_TITLE));
            sText = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_TEXT));
            category = cursor.getString(cursor.getColumnIndex(Contract.TABLE_TODO.COLUMN_NAME_CATEGORY));

            mCategottyTV.setText(category);

            tvTitle.setText(sTitle);
            tvText.setText(sText);
            holder.itemView.setTag(id);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            listener.onItemClick(pos, sTitle, sText, id, category);
        }
    }

}
