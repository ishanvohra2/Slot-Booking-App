package com.ishanvohra.slotbooking.Repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ishanvohra.slotbooking.Model.SlotItem;

import java.util.ArrayList;
import java.util.Map;

public class SlotListRepository {

    private static SlotListRepository instance;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private ArrayList<SlotItem> slotList = new ArrayList<>();

    MutableLiveData<ArrayList<SlotItem>> slots = new MutableLiveData<>();

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

                        Map<String, Object> slotItems = documentSnapshot.getData();
                        slotList.clear();

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

}
