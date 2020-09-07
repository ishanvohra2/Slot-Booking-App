package com.ishanvohra.slotbooking.ViewModel;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ishanvohra.slotbooking.LiveData.SlotLiveData;
import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.Repository.SlotListRepository;

import java.util.ArrayList;
import java.util.logging.LogRecord;

public class SlotListViewModel extends ViewModel {

    private SlotLiveData slotLiveData = null;
    private SlotListRepository repository = new SlotListRepository();

    public LiveData<ArrayList<SlotItem>> getSlots(String date){
//        if(slotLiveData == null){
//            loadSlots(date);
//        }

        return slotLiveData = repository.getSlotLiveData(date);
    }

    private void loadSlots(final String date){
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                slotLiveData = repository.getSlotLiveData(date);
            }
        },5000);
    }

}
