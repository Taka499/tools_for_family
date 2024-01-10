package edu.cmu.interest_calculator;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private Double calcInterestRate(Double original, Double interest, Integer time) {
        double diff = Math.log(original + interest) - Math.log(original);
        return (Math.exp(diff / time) - 1) * 100;
    }

    private boolean isEmptyString(String s) {
        return s == null || s.isEmpty();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText originalValueText = (EditText) findViewById(R.id.original_value);
        EditText interestValueText = (EditText) findViewById(R.id.interest_value);
        EditText timeText = (EditText) findViewById(R.id.time);
        TextView answerView = (TextView) findViewById(R.id.answer);
        Button calcButton = (Button)findViewById(R.id.calculate);

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String originalValueString = originalValueText.getText().toString();
                String interestValueString = interestValueText.getText().toString();
                String timeString = timeText.getText().toString();

                if (isEmptyString(originalValueString) || isEmptyString(interestValueString) || isEmptyString(timeString)) {
                    answerView.setVisibility(View.INVISIBLE);
                }
                else {
                    Double originalValue = Double.parseDouble(originalValueString);
                    Double interestValue = Double.parseDouble(interestValueString);
                    Integer time = Integer.parseInt(timeString);
                    answerView.setText(calcInterestRate(originalValue, interestValue, time).toString() + "%");
                    answerView.setVisibility(View.VISIBLE);
                }
            }
        });

        TextWatcher emptyTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isEmptyString(charSequence.toString().trim())) {
                    calcButton.setEnabled(false);
                    answerView.setVisibility(View.INVISIBLE);
                }
                else {
                    calcButton.setEnabled(true);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (isEmptyString(charSequence.toString().trim())) {
                    calcButton.setEnabled(false);
                    answerView.setVisibility(View.INVISIBLE);
                }
                else {
                    calcButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };

        originalValueText.addTextChangedListener(emptyTextWatcher);
        interestValueText.addTextChangedListener(emptyTextWatcher);
        timeText.addTextChangedListener(emptyTextWatcher);
    }
}