package br.com.unipar.alissonchagasaaresegundobimestre;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.GregorianCalendar;


public class MainActivity extends AppCompatActivity {

    private EditText edCpf, edRenda, edDataNascimento;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recuperar valores
        edCpf = findViewById(R.id.editTextCpf);
        edRenda = findViewById(R.id.rendaMensal);
        edDataNascimento = findViewById(R.id.editTextDataNascimento);

        //Máscara para o CPF
        SimpleMaskFormatter cpfSmf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher cpfNtw = new MaskTextWatcher(edCpf, cpfSmf);
        edCpf.addTextChangedListener(cpfNtw);

        //Máscara para Data Nascimento
        SimpleMaskFormatter dataSmf = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher dataNtw = new MaskTextWatcher(edDataNascimento, dataSmf);
        edDataNascimento.addTextChangedListener(dataNtw);

    }

    public void validar(View view) {

        Double renda = Double.parseDouble(edRenda.getText().toString());

        if(edCpf.getText().toString().equals("")){
            erro("Campo Cpf está Vazio!");
        } else if (edCpf.getText().length() != 14){
            erro("Cpf está inválido");
        }
        if(edRenda.getText().toString().equals("")) {
            erro("Campo Renda Mensal está Vazio!");
        }
        if(edDataNascimento.getText().toString().equals("")){
            erro("Campo Data Nascimento está Vazio!");
        } else {
            if (calcularIdade(converterData(edDataNascimento.getText().toString())) < 18 || renda > 5000){
                erro("Não possui direito ao auxílio!");
            } else {
                segundaTela(view, calculaDataPagamento(converterData(edDataNascimento.getText().toString())),
                        saldoReceber(renda));
            }

        }

    }

    public void erro (String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
    }

    public int calcularIdade(Date dataNascimento){

        Calendar dataUsuario = new GregorianCalendar();
        dataUsuario.setTime(dataNascimento);
        Calendar today = Calendar.getInstance();
        int idade = today.get(Calendar.YEAR) - dataUsuario.get(Calendar.YEAR);
        dataUsuario.add(Calendar.YEAR, idade);

        return idade;
    }

    public String calculaDataPagamento(Date dataNascimento) {

        String dataPagamentoConvertido, diaString, mesString, anoString;
        int dia, mes, ano;

        Calendar dataAtual = Calendar.getInstance();
        Calendar dataPagamento = Calendar.getInstance();
        dataPagamento.setTime(dataNascimento);
        dataPagamento.add(Calendar.DATE, +20);
        dataPagamento.set(Calendar.YEAR, dataAtual.get(Calendar.YEAR));


        dia = dataPagamento.get(Calendar.DAY_OF_MONTH);
        mes = dataPagamento.get(Calendar.MONTH);
        ano = dataPagamento.get(Calendar.YEAR);

        diaString = Integer.toString(dia);
        mesString = Integer.toString(mes);
        anoString = Integer.toString(ano);

        dataPagamentoConvertido = diaString + "/" + mesString + "/" + anoString;

        return  dataPagamentoConvertido;
    }

    public Date converterData(String data){
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date dataConvertida = null;
        try {
            dataConvertida = format.parse(String.valueOf(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataConvertida;
    }

    public double saldoReceber(double valor){

        double saldoTotal = (valor * 70) / 100;

        if(saldoTotal >= 475){
            saldoTotal = 475;
        }
        return saldoTotal;
    }

    public void segundaTela(View view, String dataPagamento, Double valroReceber) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(3);

        Intent intent = new Intent(this,Result.class);
        Bundle b = new Bundle();
        b.putString("dataPagamento", dataPagamento);
        b.putDouble("renda", valroReceber);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}

