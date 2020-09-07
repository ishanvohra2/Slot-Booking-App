package com.ishanvohra.slotbooking.Repository;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ishanvohra.slotbooking.LiveData.SlotLiveData;

public class SlotListRepository {

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    public SlotLiveData getSlotLiveData(String date){
        DocumentReference documentReference = firebaseFirestore.collection("slots").document(date);

        return new SlotLiveData(documentReference);
    }

}
