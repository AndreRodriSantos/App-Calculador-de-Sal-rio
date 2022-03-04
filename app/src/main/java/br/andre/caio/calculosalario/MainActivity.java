package br.andre.caio.calculosalario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textSL, textDesconto, textSB;
    private Button buttonCalc;
    private Spinner plano;
    private EditText editSB, editDep;
    private RadioGroup va, vt, vr;
    private RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //referenciando
        editSB = findViewById(R.id.sal_Bruto);
        editDep = findViewById(R.id.numDependentes);
        plano = (Spinner) findViewById(R.id.spin);
        va = findViewById(R.id.va);
        vt = findViewById(R.id.vt);
        buttonCalc = findViewById(R.id.calc);
        vr = findViewById(R.id.vr);
        textSB = findViewById(R.id.sal_brutoResult);


        plano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        //buttton calc
        Button bt_calc = findViewById(R.id.calc);
        bt_calc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int radioId = va.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                textSB.setText("Teste:" + radioButton.getText());
            }
        });

        buttonCalc.setOnClickListener(v -> {
            if (editSB.getText().toString().isEmpty()) {
                editSB.setError(getString(R.string.valida));
            } else if (editDep.getText().toString().isEmpty()) {
                editDep.setError(getString(R.string.valida));
            }else if(va.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();
            }else if(vr.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();
            }else if(vt.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "teste", Toast.LENGTH_SHORT).show();
        }else {
                double sb, dp, sl, inss, irrf;
                sb = Double.parseDouble(editSB.getText().toString());
                dp = Integer.parseInt(editDep.getText().toString());
                if (sb <= 1045) {
                    inss = 0.075 * sb - 0.00;
                } else if (sb > 1045 && sb <= 2089.60) {
                    inss = 0.09 * sb - 15.68;
                } else if (sb > 2089.61 && sb <= 3134.40) {
                    inss = 0.12 * sb - 78.38;
                } else if (sb > 3134.41 && sb <= 6101.06) {
                    inss = 0.14 * sb - 141.07;
                } else {
                    inss = 713.80 - sb;
                }
                textSB.setText("");
            }
            });
        }
    public void checkButton(View va){
        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
    }

