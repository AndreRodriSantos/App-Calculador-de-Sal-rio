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

import com.google.android.material.snackbar.Snackbar;

import java.time.Clock;

public class MainActivity extends AppCompatActivity {
    private Button buttonCalc, buttonLimpar;
    private Spinner plano;
    private EditText editSB, editDep;
    private RadioGroup va, vt, vr;
    private RadioButton radioButton;
    private TextView sb_result, sl_result, inss_result, va_result, vr_result, vt_result, plano_result, irrf_result, desconto_result;
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
        sb_result = findViewById(R.id.sb_result);
        plano_result = findViewById(R.id.plano_result);
        inss_result = findViewById(R.id.inss_result);
        sl_result = findViewById(R.id.sl_result);
        va_result = findViewById(R.id.va_result);
        vt_result = findViewById(R.id.vt_result);
        vr_result = findViewById(R.id.vr_result);
        irrf_result = findViewById(R.id.irrf_result);
        desconto_result = findViewById(R.id.desconto_result);
        buttonLimpar = findViewById(R.id.limpar);

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

            }
        });
        Button bt_limpar = findViewById(R.id.limpar);
        bt_limpar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int radioId = va.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);

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
                double sb, dp, sl, inss, irrf, ps = 0, vaValor = 0, vrValor = 0, vtValor= 0;
                sb = Double.parseDouble(editSB.getText().toString());
                dp = Integer.parseInt(editDep.getText().toString());

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
                switch (plano.getSelectedItemPosition()) {
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

                // Calculo do desconto de vale Transporte

                switch (vt.getCheckedRadioButtonId()) {
                    case R.id.vtsim:
                        vtValor = sb * 0.06;
                        break;
                    case R.id.vtnao:
                        break;
                }
                // Calculo do desconto de vale Alimentação.
                switch (va.getCheckedRadioButtonId()) {
                    case R.id.vasim:
                        if (sb <= 3000) {
                            vaValor = 15;
                        } else if (sb >= 3001 && sb <= 5000) {
                            vaValor = 25;
                        } else {
                            vaValor = 35;
                        }
                        break;
                    case R.id.vanao:
                        break;
                }
                // Calculo do desconto de vale Refeição.
                switch (vr.getCheckedRadioButtonId()) {
                    case R.id.vrsim:
                        if (sb <= 3000) {
                            vrValor = 2.60 * 22;
                        } else if (sb >= 3001 && sb <= 5000) {
                            vrValor = 3.65 * 22;
                        } else {
                            vrValor = 6.50 * 22;
                        }
                        break;
                    case R.id.vrnao:
                        break;
                }
                // Calcula o IRRF base.
                double irrfBase = sb - inss - (189.59 * dp);
                // Calcula o IRRF.
                if (irrfBase <= 1903.98) {
                    irrf = 0;
                } else if (irrfBase <= 2826.65) {
                    irrf = 0.075 * irrfBase - 142.80;
                } else if (irrfBase <= 3751.05) {
                    irrf = 0.015 * irrfBase - 354.80;
                } else if (irrfBase <= 4664.68) {
                    irrf = 0.225 * irrfBase - 636.13;
                } else {
                    irrf = 0.275 * irrfBase - 869.36;
                }
                //Calculo do Salário Liquido
                sl = sb - inss - vtValor - vrValor - vaValor - irrf - ps;

                //calculo dos descontos
                double descontos =  (sb / sl) * 100;

                //exibição
                sb_result.setText(getString(R.string.sb_result, sb));
                inss_result.setText(getString(R.string.inss_result, inss));
                plano_result.setText(getString(R.string.plano_result, ps));
                va_result.setText(getString(R.string.va_result, vaValor));
                vt_result.setText(getString(R.string.vt_result, vtValor));
                vr_result.setText(getString(R.string.vr_result, vrValor));
                irrf_result.setText(getString(R.string.irrf_result, irrf));
                sl_result.setText(getString(R.string.sl_result, sl));
                desconto_result.setText(getString(R.string.desconto, descontos) + "R$");
            }
            });
        buttonLimpar.setOnClickListener(v -> {
            editSB.setText(null);
            editDep.setText(null);
            va.clearCheck();
            vt.clearCheck();
            vr.clearCheck();
            sb_result.setText("");
            inss_result.setText("");
            plano_result.setText("");
            va_result.setText("");
            vt_result.setText("");
            vr_result.setText("");
            irrf_result.setText("");
            sl_result.setText("");
            desconto_result.setText("");

        });
        }
    public void checkButton(View va){
        Toast.makeText(this, "Selected Radio Button: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }


}


