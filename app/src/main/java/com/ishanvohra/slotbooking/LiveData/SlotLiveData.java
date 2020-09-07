package com.ishanvohra.slotbooking.LiveData;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.ishanvohra.slotbooking.Model.SlotItem;

import java.util.ArrayList;
import java.util.Map;

import javax.annotation.Nullable;

public class SlotLiveData extends LiveData<ArrayList<SlotItem>> implements EventListener<DocumentSnapshot> {

    private ArrayList<SlotItem> slotsTemp = new ArrayList<>();
    public MutableLiveData<ArrayList<SlotItem>> slotList = new MutableLiveData<>();
    private DocumentReference documentReference;

    private static String TAG = "SlotLiveData";

    public SlotLiveData(DocumentReference documentReference) {
        this.documentReference = documentReference;
    }

    @Override
    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
        if(documentSnapshot != null && documentSnapshot.exists()){

            Map<String, Object> slotItems = documentSnapshot.getData();
            slotsTemp.clear();

            for(Map.Entry<String, Object> entry : slotItems.entrySet()){
                SlotItem slotItem = new SlotItem();
                slotItem.setTime(entry.getValue().toString());
                slotsTemp.add(slotItem);
            }

            slotList.setValue(slotsTemp);
        }
        else
            Log.d(TAG, "onEvent: Error loading slots");
    }
}
