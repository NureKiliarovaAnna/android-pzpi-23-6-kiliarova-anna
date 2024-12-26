package nure.kiliarova.anna.android_pzpi_23_6_kiliarova_anna_pract_task2_part2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;
    private StringBuilder input = new StringBuilder();
    private double firstOperand = 0;
    private String operator = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        setButtonListeners();
    }

    private void setButtonListeners() {
        int[] numberButtons = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        };

        View.OnClickListener numberClickListener = v -> {
            Button button = (Button) v;
            input.append(button.getText());
            tvResult.setText(input.toString());
        };

        for (int id : numberButtons) {
            findViewById(id).setOnClickListener(numberClickListener);
        }

        findViewById(R.id.btnAdd).setOnClickListener(v -> handleOperator("+"));
        findViewById(R.id.btnSubtract).setOnClickListener(v -> handleOperator("-"));
        findViewById(R.id.btnMultiply).setOnClickListener(v -> handleOperator("*"));
        findViewById(R.id.btnDivide).setOnClickListener(v -> handleOperator("/"));

        findViewById(R.id.btnClear).setOnClickListener(v -> {
            input.setLength(0);
            firstOperand = 0;
            operator = "";
            tvResult.setText("0");
        });

        findViewById(R.id.btnEquals).setOnClickListener(v -> calculateResult());
    }

    private void handleOperator(String op) {
        if (input.length() > 0) {
            firstOperand = Double.parseDouble(input.toString());
            operator = op;
            input.setLength(0);
        }
    }

    private void calculateResult() {
        if (input.length() > 0 && !operator.isEmpty()) {
            double secondOperand = Double.parseDouble(input.toString());
            double result = 0;

            switch (operator) {
                case "+":
                    result = firstOperand + secondOperand;
                    break;
                case "-":
                    result = firstOperand - secondOperand;
                    break;
                case "*":
                    result = firstOperand * secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result = firstOperand / secondOperand;
                    } else {
                        tvResult.setText("Error");
                        return;
                    }
                    break;
            }

            tvResult.setText(String.valueOf(result));
            input.setLength(0);
            operator = "";
        }
    }
}