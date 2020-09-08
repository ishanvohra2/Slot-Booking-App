package com.ishanvohra.slotbooking.ViewModel;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.Repository.SlotListRepository;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class SlotListViewModel extends ViewModel {

    private MutableLiveData<ArrayList<SlotItem>> slotLiveData;
    private String date;

    public void init(Context context){
        if(slotLiveData != null){
            return;
        }

        slotLiveData = SlotListRepository.getInstance(context).getSlotLiveData(date);
    }

    public void setDate(String date){
        this.date = date;
    }

    public MutableLiveData<ArrayList<SlotItem>> getLiveData(){
        return slotLiveData;
    }

}
