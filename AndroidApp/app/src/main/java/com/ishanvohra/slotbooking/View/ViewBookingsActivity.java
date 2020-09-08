package com.ishanvohra.slotbooking.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ishanvohra.slotbooking.Adapter.BookedSlotAdapter;
import com.ishanvohra.slotbooking.Model.BookingItem;
import com.ishanvohra.slotbooking.R;
import com.ishanvohra.slotbooking.ViewModel.BookedSlotViewModel;

import java.util.ArrayList;

public class ViewBookingsActivity extends AppCompatActivity {

    private BookedSlotAdapter adapter = new BookedSlotAdapter(this, new ArrayList<BookingItem>());
    private BookedSlotViewModel viewModel;

    private static String TAG = "ViewBookingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bookings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.booked_activity_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(ViewBookingsActivity.this).get(BookedSlotViewModel.class);
        viewModel.init(this);

        viewModel.getLiveData().observe(this, new Observer<ArrayList<BookingItem>>() {
            @Override
            public void onChanged(ArrayList<BookingItem> bookingItems) {
                Log.d(TAG, "onChanged: Booked slots loaded");
                adapter.setSlots(bookingItems);
                adapter.notifyDataSetChanged();
            }
        });

        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.booked_activity_swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.getLiveData().observe(ViewBookingsActivity.this, new Observer<ArrayList<BookingItem>>() {
                    @Override
                    public void onChanged(ArrayList<BookingItem> bookingItems) {
                        swipeRefreshLayout.setRefreshing(false);
                        Log.d(TAG, "onChanged: Booked slots loaded");
                        adapter.setSlots(bookingItems);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}