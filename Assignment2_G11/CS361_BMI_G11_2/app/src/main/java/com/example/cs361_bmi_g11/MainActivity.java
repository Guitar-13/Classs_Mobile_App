package com.example.cs361_bmi_g11;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cs361_bmi_g11.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private ArrayList<BMIRecord> bmiHistoryList;
    private TextView BMIType;
    private TextView BMIResult;
    private Button CalculateButton;
    private Button ClearButton;
    private TextView HistoryButton;
    private EditText WeightInput;
    private EditText HeightInput;
    private ImageView headerPicture;
    private DecimalFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WeightInput = findViewById(R.id.weight_input);
        HeightInput = findViewById(R.id.height_input);
        BMIType = findViewById(R.id.bmi_type);
        BMIResult = findViewById(R.id.bmi_result);
        CalculateButton = findViewById(R.id.calculate_button);
        ClearButton = findViewById(R.id.clear_button);
        ClearButton.setText(Html.fromHtml("<u>" + getString(R.string.clear_button) + "</u>"));
        headerPicture = findViewById(R.id.header_picture);
        HistoryButton = findViewById(R.id.history);


        formatter = new DecimalFormat("#,###.##");

        WeightInput.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});
        HeightInput.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(8, 2)});


        bmiHistoryList = new ArrayList<>();

        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WeightInput.setText("");
                HeightInput.setText("");
                BMIResult.setText("");
                BMIType.setText("");
                headerPicture.setImageResource(R.drawable.bmi);
            }
        });

        CalculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateBMI();
            }
        });

        HistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHistoryActivity();
            }
        });
    }

    private void openHistoryActivity() {
        Intent intent = new Intent(this, HistoryActivity.class);
        intent.putParcelableArrayListExtra("historyList", bmiHistoryList);
        startActivity(intent);
    }

    private void calculateBMI() {
        String weightstr = WeightInput.getText().toString();
        String heightstr = HeightInput.getText().toString();

        if (!weightstr.isEmpty() && !heightstr.isEmpty()) {
            float weight = Float.parseFloat(weightstr);
            float height = Float.parseFloat(heightstr);
            float height_cm = (height / 100) * (height / 100);
            float bmi = weight / height_cm;
            String formattedBMI = formatter.format(bmi);
            BMIResult.setText(formattedBMI);

            String classification = getBMIType(bmi);


            BMIRecord bmiRecord = new BMIRecord(getCurrentDate(), weight, bmi, classification);
            bmiHistoryList.add(bmiRecord);

            BMIType.setText(classification);

            int color = getColorForClassification(classification);
            BMIType.setTextColor(color);


            displayHeaderPicture(classification);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    private String getBMIType(float bmi) {
        if (bmi < 16) {
            return getResources().getString(R.string.severe_thinness);
        } else if (bmi >= 16 && bmi < 17) {
            return getResources().getString(R.string.moderate_thinness);
        } else if (bmi >= 17 && bmi < 18.5) {
            return getResources().getString(R.string.mild_thinness);
        } else if (bmi >= 18.5 && bmi < 25) {
            return getResources().getString(R.string.normal);
        } else if (bmi >= 25 && bmi < 30) {
            return getResources().getString(R.string.overweight);
        } else if (bmi >= 30 && bmi < 35) {
            return getResources().getString(R.string.obese_class1);
        } else if (bmi >= 35 && bmi < 40) {
            return getResources().getString(R.string.obese_class2);
        } else {
            return getResources().getString(R.string.obese_class3);
        }
    }

    private int getColorForClassification(String classification) {
        switch (classification) {
            case "Severe Thinness":
                return Color.parseColor(getString(R.string.ff0000));
            case "Moderate Thinness":
                return Color.parseColor(getString(R.string.ffa500));
            case "Mild Thinness":
                return Color.parseColor(getString(R.string.ffff00));
            case "Normal":
                return Color.parseColor(getString(R.string._3dec55));
            case "Overweight":
                return Color.parseColor(getString(R.string.ffff00));
            case "Obese Class 1":
                return Color.parseColor(getString(R.string.ffa500));
            case "Obese Class 2":
                return Color.parseColor(getString(R.string.ff0000));
            default:
                return Color.parseColor(getString(R.string._800000));
        }
    }

    private void displayHeaderPicture(String classification) {

        int imageResource;
        switch (classification) {
            case "Severe Thinness":
            case "Moderate Thinness":
            case "Mild Thinness":
                imageResource = R.drawable.underweight;
                break;
            case "Normal":
                imageResource = R.drawable.normal;
                break;
            case "Overweight":
                imageResource = R.drawable.overweight;
                break;
            case "Obese Class 1":
            case "Obese Class 2":
            case "Obese Class 3":
                imageResource = R.drawable.obese;
                break;
            default:
                imageResource = R.drawable.bmi; // Default image
                break;
        }
        headerPicture.setImageResource(imageResource);
    }

    class DecimalDigitsInputFilter implements InputFilter {
        private Pattern mPattern;

        DecimalDigitsInputFilter(int digits, int digitsAfterZero) {
            mPattern = Pattern.compile("[0-9]{0," + (digits - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher matcher = mPattern.matcher(dest);
            if (!matcher.matches()) return "";
            return null;
        }
    }
}
