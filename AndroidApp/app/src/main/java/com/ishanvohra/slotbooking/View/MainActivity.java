package com.ishanvohra.slotbooking.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ishanvohra.slotbooking.Adapter.AvailableSlotsAdapter;
import com.ishanvohra.slotbooking.Model.SlotItem;
import com.ishanvohra.slotbooking.R;
import com.ishanvohra.slotbooking.ViewModel.SlotListViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class MainActivity extends AppCompatActivity implements AvailableSlotsAdapter.BookSlotListener {

    private static String TAG = "MainActivity";
    private String dateString = "8-9-2020";
    private SlotListViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ProgressBar progressBar = findViewById(R.id.progressbar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();

                if(id == R.id.view_booked_item){
                    startActivity(new Intent(MainActivity.this, ViewBookingsActivity.class));
                }

                return false;
            }
        });

        //Initializing View model class to load slots
        viewModel = new ViewModelProvider(MainActivity.this).get(SlotListViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.main_activity_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AvailableSlotsAdapter adapter = new AvailableSlotsAdapter(this, new ArrayList<SlotItem>(), this);
        recyclerView.setAdapter(adapter);

        //Start date for calendar
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, 0);

        //End date for calendar
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.main_activity_calendar_view)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                progressBar.setVisibility(View.VISIBLE);

                dateString = date.get(Calendar.DAY_OF_MONTH) + "-" + (date.get(Calendar.MONTH) + 1) + "-" + date.get(Calendar.YEAR);
                Log.d(TAG, "onDateSelected: " + dateString);

                viewModel.init(MainActivity.this, dateString);
                viewModel.getLiveData().observe(MainActivity.this, new Observer<ArrayList<SlotItem>>() {
                    @Override
                    public void onChanged(ArrayList<SlotItem> slotItems) {
                        progressBar.setVisibility(View.GONE);
                        Collections.reverse(slotItems);
                        adapter.setSlots(slotItems);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    @Override
    public void bookSlot(final String time) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Confirm booking");
        builder.setMessage("Are you sure you want to book this slot? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.bookSlot(time);
                Dialog dialog = onCreateDialog(time);
                dialog.show();
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;
                dialog.getWindow().setLayout(width, height * 2 / 3);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    private Dialog onCreateDialog(String time){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.slot_booked_dialog);

        TextView dateTimeTv = dialog.findViewById(R.id.slot_booked_dialog_time_tv);
        dateTimeTv.setText(dateString + ", " + time);

        Button doneBtn = dialog.findViewById(R.id.slot_booked_dialog_submit_btn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        return dialog;
    }
}