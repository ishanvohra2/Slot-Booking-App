package com.ishanvohra.slotbooking.Repository;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ishanvohra.slotbooking.Model.BookingItem;
import com.ishanvohra.slotbooking.Model.SlotItem;

import java.util.ArrayList;
import java.util.Map;

public class SlotListRepository {

    private static SlotListRepository instance;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ArrayList<SlotItem> slotList = new ArrayList<>();

    MutableLiveData<ArrayList<SlotItem>> slots = new MutableLiveData<>();

    private static String TAG = "SlotListRepository";

    public static SlotListRepository getInstance(Context context){
        if(instance == null){
            instance = new SlotListRepository();
        }

        return instance;
    }

    public MutableLiveData<ArrayList<SlotItem>> getSlotLiveData(String date){
        slots = new MutableLiveData<>();

        DocumentReference documentReference = firebaseFirestore.collection("slots").document(date);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot != null && documentSnapshot.exists()){

                        slotList.clear();
                        Map<String, Object> slotItems = documentSnapshot.getData();
                        Log.d(TAG, "onComplete: " + slotItems);

                        for(Map.Entry<String, Object> entry : slotItems.entrySet()){
                            SlotItem slotItem = new SlotItem();
                            slotItem.setTime(entry.getValue().toString());
                            slotList.add(slotItem);
                        }

                        slots.setValue(slotList);
                    }
                }
            }
        });

        return slots;
    }

    public String bookSlot(final String date,final String time){
        final String[] result = {""};

        BookingItem bookingItem = new BookingItem();
        bookingItem.setDate(date);
        bookingItem.setTime(time);

        DocumentReference documentReference = firebaseFirestore.collection("bookedSlots").document(date);
        documentReference.set(bookingItem).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "onComplete: Slot booked " + date + ", " + time);
                    result[0] = "Success";
                }
            }
        });

        return result[0];
    }

}
