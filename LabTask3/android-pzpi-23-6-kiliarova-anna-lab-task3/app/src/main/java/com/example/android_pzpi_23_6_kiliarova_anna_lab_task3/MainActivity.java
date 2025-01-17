package com.example.android_pzpi_23_6_kiliarova_anna_lab_task3;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView tvResult;
    private String currentNumber = "";
    private String operator = "";
    private double firstOperand = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupInsets();
        setupListeners();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void initializeViews() {
        tvResult = findViewById(R.id.tvResult);
    }

    private void setupInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void setupListeners() {
        View.OnClickListener numberClickListener = view -> {
            Button button = (Button) view;
            currentNumber += button.getText().toString();
            tvResult.setText(currentNumber);
        };

        View.OnClickListener operatorClickListener = view -> {
            Button button = (Button) view;
            if (!currentNumber.isEmpty()) {
                firstOperand = Double.parseDouble(currentNumber);
                operator = button.getText().toString();
                currentNumber = "";
            }
        };

        findViewById(R.id.btn0).setOnClickListener(numberClickListener);
        findViewById(R.id.btn1).setOnClickListener(numberClickListener);
        findViewById(R.id.btn2).setOnClickListener(numberClickListener);
        findViewById(R.id.btn3).setOnClickListener(numberClickListener);
        findViewById(R.id.btn4).setOnClickListener(numberClickListener);
        findViewById(R.id.btn5).setOnClickListener(numberClickListener);
        findViewById(R.id.btn6).setOnClickListener(numberClickListener);
        findViewById(R.id.btn7).setOnClickListener(numberClickListener);
        findViewById(R.id.btn8).setOnClickListener(numberClickListener);
        findViewById(R.id.btn9).setOnClickListener(numberClickListener);

        findViewById(R.id.btnAdd).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnSubtract).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnMultiply).setOnClickListener(operatorClickListener);
        findViewById(R.id.btnDivide).setOnClickListener(operatorClickListener);

        findViewById(R.id.btnEquals).setOnClickListener(view -> {
            if (!currentNumber.isEmpty() && !operator.isEmpty()) {
                double secondOperand = Double.parseDouble(currentNumber);
                double result = calculateResult(firstOperand, secondOperand, operator);
                if (Double.isNaN(result)) {
                    tvResult.setText("Error");
                } else {
                    tvResult.setText(String.valueOf(result));
                    currentNumber = String.valueOf(result);
                }
                operator = "";
            }
        });

        findViewById(R.id.btnDot).setOnClickListener(view -> {
            if (!currentNumber.contains(".")) {
                currentNumber += ".";
                tvResult.setText(currentNumber);
            }
        });

        findViewById(R.id.btnClear).setOnClickListener(view -> {
            resetCalculator();
        });
    }

    private double calculateResult(double firstOperand, double secondOperand, String operator) {
        switch (operator) {
            case "+":
                return firstOperand + secondOperand;
            case "-":
                return firstOperand - secondOperand;
            case "*":
                return firstOperand * secondOperand;
            case "/":
                return secondOperand != 0 ? firstOperand / secondOperand : Double.NaN;
            default:
                return Double.NaN;
        }
    }

    private void resetCalculator() {
        currentNumber = "";
        operator = "";
        firstOperand = 0;
        tvResult.setText("0");
    }
}