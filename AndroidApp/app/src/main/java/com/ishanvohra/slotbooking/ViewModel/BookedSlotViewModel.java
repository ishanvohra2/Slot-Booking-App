package com.ishanvohra.slotbooking.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ishanvohra.slotbooking.Model.BookingItem;
import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.Repository.SlotListRepository;

import java.util.ArrayList;

public class BookedSlotViewModel extends ViewModel {

    private MutableLiveData<ArrayList<BookingItem>> slotLiveData;
    private Context context;

    private static String TAG = "BookedSlotViewModel";

    public void init(Context context){
        this.context = context;
        slotLiveData = SlotListRepository.getInstance(context).getBookedSlots();
    }

    public MutableLiveData<ArrayList<BookingItem>> getLiveData(){
        return slotLiveData;
    }

}
