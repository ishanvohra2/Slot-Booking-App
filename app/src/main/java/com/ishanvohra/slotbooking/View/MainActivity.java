package com.ishanvohra.slotbooking.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.R;
import com.ishanvohra.slotbooking.ViewModel.SlotListViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivity";
    private String dateString = "8-9-2020";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SlotListViewModel viewModel = new ViewModelProvider(MainActivity.this).get(SlotListViewModel.class);

        //Start date for calendar
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        //End date for calendar
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 10);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.main_activity_calendar_view)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                dateString = date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR);
                Log.d(TAG, "onDateSelected: " + dateString);

                viewModel.init(MainActivity.this, dateString);
                viewModel.getLiveData().observe(MainActivity.this, new Observer<ArrayList<SlotItem>>() {
                    @Override
                    public void onChanged(ArrayList<SlotItem> slotItems) {

                    }
                });
            }
        });


    }
}