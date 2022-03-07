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
                Toast.makeText(this, "Não Selecionou VA", Toast.LENGTH_SHORT).show();
            }else if(vr.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Não Selecionou VR", Toast.LENGTH_SHORT).show();
            }else if(vt.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Não Selecionou VT", Toast.LENGTH_SHORT).show();
        }else {
                double sb, dp, sl, inss, irrf;
                int ps = 0;
                sb = Double.parseDouble(editSB.getText().toString());
                dp = Integer.parseInt(editDep.getText().toString());
                int planosec = plano.getSelectedItemPosition();
                //Calculo do INSS
                if (sb <= 1212.00) {
                    inss = 0.075 * sb - 0.00;
                } else if (sb > 1212.00 && sb <= 2427.35) {
                    inss = 0.09 * sb - 18.18;
                } else if (sb > 2427.35 && sb <= 3641.03) {
                    inss = 0.12 * sb - 91.00;
                } else if (sb > 3641.03 && sb <= 7087.22) {
                    inss = 0.14 * sb - 163.82;
                } else {
                    inss = 713.80 - sb;
                }

                //Calculo do Plano de Saude
                switch (planosec) {
                    case 0:
                        if (sb <= 3000) {
                            ps = 60;
                        } else {
                            ps = 80;
                        }
                        break;
                    case 1:
                        if (sb <= 3000) {
                            ps = 80;
                        } else {
                            ps = 110;
                        }
                        break;
                    case 2:
                        if (sb <= 3000) {
                            ps = 95;
                        } else {
                            ps = 135;
                        }
                        break;
                    case 3:
                        if (sb <= 3000) {
                            ps = 130;
                        } else {
                            ps = 180;
                        }
                        break;
                    default:
                        break;
                }

                //Calculo
                textSB.setText(String.valueOf(ps));
            }
            });
        }
    public void checkButton(View va){
        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }
    }

