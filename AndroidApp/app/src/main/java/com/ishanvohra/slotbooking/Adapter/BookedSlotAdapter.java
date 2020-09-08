package com.ishanvohra.slotbooking.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ishanvohra.slotbooking.Model.BookingItem;
import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.R;

import java.util.ArrayList;

public class BookedSlotAdapter extends RecyclerView.Adapter<BookedSlotAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<BookingItem> dataSet;

    public void setSlots(ArrayList<BookingItem> dataSet){
        this.dataSet = dataSet;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView timeTv, dateTv;

        public MyViewHolder(View item){
            super(item);
            timeTv = item.findViewById(R.id.booked_item_time_tv);
            dateTv = item.findViewById(R.id.booked_item_date_tv);
        }
    }

    public BookedSlotAdapter(Context context, ArrayList<BookingItem> dataSet){
        this.dataSet = dataSet;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.booked_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingItem item = dataSet.get(position);

        holder.timeTv.setText(item.getTime());
        holder.dateTv.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }


}
