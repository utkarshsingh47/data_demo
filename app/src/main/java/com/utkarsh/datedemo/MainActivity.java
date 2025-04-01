package com.utkarsh.datedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private DateAdapter adapter;
    private List<DateModel> dateList;

    private Button btnSave, btnClear, btnAddMonth;

    private int currentYear = 2025;       // Start year
    private int currentMonthIndex = 0;    // Start from January

    // Firebase Database
    private DatabaseReference dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        btnAddMonth = findViewById(R.id.btnAddMonth);

        dateList = new ArrayList<>();

        // Firebase initialization
        dbRef = FirebaseDatabase.getInstance().getReference("months");

        // Load data from Firebase
        loadDataFromFirebase();

        adapter = new DateAdapter(this, dateList);
        listView.setAdapter(adapter);

        btnSave.setOnClickListener(v -> saveToFirebase());
        btnClear.setOnClickListener(v -> clearSelection());
        btnAddMonth.setOnClickListener(v -> addNextMonth());
    }

    // ✅ Load data from Firebase
    private void loadDataFromFirebase() {
        dbRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult().exists()) {
                dateList.clear();
                for (DataSnapshot snapshot : task.getResult().getChildren()) {
                    DateModel model = snapshot.getValue(DateModel.class);
                    dateList.add(model);
                }
                adapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(e -> Log.e("Firebase", "Failed to load data", e));
    }

    // ✅ Save the selections to Firebase
    private void saveToFirebase() {
        dbRef.removeValue(); // Clear old data

        for (int i = 0; i < dateList.size(); i++) {
            DateModel model = dateList.get(i);
            dbRef.child("month_" + i).setValue(model);
        }
    }

    // ✅ Add the next month
    private void addNextMonth() {
        currentMonthIndex++;

        if (currentMonthIndex >= 12) {
            currentMonthIndex = 0;  // Reset to January
            currentYear++;
        }

        generateDates(getMonthName(currentMonthIndex), currentYear);

        adapter.notifyDataSetChanged();
    }

    // ✅ Generate month dates
    private void generateDates(String month, int year) {
        int daysInMonth = getDaysInMonth(month, year);

        for (int i = 1; i <= daysInMonth; i++) {
            String date = String.format("%02d %s, %d", i, month, year);
            dateList.add(new DateModel(date, false, false));
        }
    }

    // ✅ Clear selections
    private void clearSelection() {
        for (DateModel model : dateList) {
            model.setChecked(false);
            model.setImageClicked(false);
        }
        adapter.notifyDataSetChanged();
    }

    // ✅ Get month name by index
    private String getMonthName(int index) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[index % 12];
    }

    // ✅ Get days in a month (leap year handling)
    private int getDaysInMonth(String month, int year) {
        switch (month) {
            case "Feb":
                return (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) ? 29 : 28;
            case "Apr": case "Jun": case "Sep": case "Nov":
                return 30;
            default:
                return 31;
        }
    }
}
