package com.example.cs361_bmi_g11;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs361_bmi_g11.R;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<BMIRecord> bmiHistoryList;
    private BMIHistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        bmiHistoryList = getIntent().getParcelableArrayListExtra("historyList");
        Collections.reverse(bmiHistoryList);
        initUI();


        ListView historyListView = findViewById(R.id.history_list_view);
        historyAdapter = new BMIHistoryAdapter(this, R.layout.history_item, bmiHistoryList);
        historyListView.setAdapter(historyAdapter);
    }

    private void initUI() {
        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
