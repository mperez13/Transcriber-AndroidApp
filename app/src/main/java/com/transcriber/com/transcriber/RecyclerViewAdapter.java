package com.transcriber.com.transcriber;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView
        .Adapter<RecyclerViewAdapter
        .ItemHolder> {
    private static String LOG_TAG = "MyRecyclerViewAdapter";
    private static rClickListener rClickListener;

    public ArrayList<String> dataList;

    public TextView nameTextView;


    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name = nameTextView;

        public ItemHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            rClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(rClickListener rClickListener) {
        this.rClickListener = rClickListener;
    }

    public RecyclerViewAdapter(ArrayList<String> dataList) {

        dataList = dataList;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        ItemHolder dataObjectHolder = new ItemHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {
        holder.name.setText(dataList.get(position));

    }


    public void addItem(String data) {
        dataList.add(data);
        notifyItemInserted(0);
    }

    public void deleteItem(int i) {
        dataList.remove(i);
        notifyItemRemoved(i);
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }

    public interface rClickListener {
        public void onItemClick(int position, View v);
    }
}


