package com.ishanvohra.slotbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.R;

import java.util.ArrayList;

public class AvailableSlotsAdapter extends RecyclerView.Adapter<AvailableSlotsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<SlotItem> dataSet;

    public void setSlots(ArrayList<SlotItem> dataSet){
        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTv;

        public MyViewHolder(View itemView){
            super(itemView);
            timeTv = itemView.findViewById(R.id.slot_item_time_tv);
        }
    }

    public AvailableSlotsAdapter(Context context, ArrayList<SlotItem> dataSet){
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_slots_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SlotItem item = dataSet.get(position);
        holder.timeTv.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
