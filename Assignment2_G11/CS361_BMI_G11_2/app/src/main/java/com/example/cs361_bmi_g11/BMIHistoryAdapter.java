package com.example.cs361_bmi_g11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cs361_bmi_g11.R;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BMIHistoryAdapter extends ArrayAdapter<BMIRecord> {

    private Context context;
    private int resource;
    private ArrayList<BMIRecord> bmiHistoryList;
    private DecimalFormat bmiFormatter;

    public BMIHistoryAdapter(Context context, int resource, ArrayList<BMIRecord> bmiHistoryList) {
        super(context, resource, bmiHistoryList);
        this.context = context;
        this.resource = resource;
        this.bmiHistoryList = bmiHistoryList;
        this.bmiFormatter = new DecimalFormat("0.00");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;

        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(resource, parent, false);
        }

        BMIRecord bmiRecord = bmiHistoryList.get(position);

        TextView dateTextView = listItem.findViewById(R.id.date_text_view);
        TextView weightTextView = listItem.findViewById(R.id.weight_text_view);
        TextView bmiTextView = listItem.findViewById(R.id.bmi_text_view);
        TextView classificationTextView = listItem.findViewById(R.id.classification_text_view);


        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdfInput.parse(bmiRecord.getDate());
            String formattedDate = sdfOutput.format(date);
            dateTextView.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        String formattedBMI = bmiFormatter.format(bmiRecord.getBmi());
        bmiTextView.setText(formattedBMI);

        weightTextView.setText(String.valueOf(bmiRecord.getWeight()));
        classificationTextView.setText(bmiRecord.getClassification());

        return listItem;
    }
}
