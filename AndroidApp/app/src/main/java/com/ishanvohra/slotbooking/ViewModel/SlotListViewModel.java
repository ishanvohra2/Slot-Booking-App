package com.ishanvohra.slotbooking.ViewModel;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.Repository.SlotListRepository;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class SlotListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<SlotItem>> slotLiveData;
    private Context context;
    private String date;

    public void init(Context context, String date){
        this.context = context;
        this.date = date;
        slotLiveData = SlotListRepository.getInstance(context).getSlotLiveData(date);
    }

    public MutableLiveData<ArrayList<SlotItem>> getLiveData(){
        return slotLiveData;
    }

    public String bookSlot(String time){
        return SlotListRepository.getInstance(context).bookSlot(date, time);
    }

}
