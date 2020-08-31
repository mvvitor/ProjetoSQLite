package com.example.sqlitetecia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String NOME_BANCO_DE_DADOS = "Estoque_de_TonerBD";

    TextView txtNomeToner, txtNomeModelo, txtNomeDataEntrada, txtNomeSaida, lblVisualizaToners;
    EditText txtCadToner, txtCadEntrada, txtCadSaida;
    Spinner spnCadModeloImpressora;
    Button btnSalvar;

    SQLiteDatabase meuBancoDeDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNomeToner = findViewById(R.id.txtNomeToner);
        txtNomeModelo = findViewById(R.id.txtNomeModelo);
        txtNomeDataEntrada = findViewById(R.id.txtNomeDataEntrada);
        txtNomeSaida = findViewById(R.id.txtNomeSaida);
        lblVisualizaToners = findViewById(R.id.lblVisualizaToners);
        txtCadToner = findViewById(R.id.txtCadToner);
        txtCadEntrada = findViewById(R.id.txtCadEntrada);
        txtCadSaida = findViewById(R.id.txtCadSaida);
        spnCadModeloImpressora = findViewById(R.id.spnCadModeloImpressora);
        btnSalvar = findViewById(R.id.btnSalvar);

        lblVisualizaToners.setOnClickListener(this);
        btnSalvar.setOnClickListener(this);
        //Criando banco de dados

        meuBancoDeDados = openOrCreateDatabase(NOME_BANCO_DE_DADOS, MODE_PRIVATE, null);

        criarTabelaToner();
    }

    //Este método irá validar o nomeToner e o nomeSaida
    private boolean verificarEntrada(String nomeToner, String nomeSaida) {
        if (nomeToner.isEmpty()) {
            txtCadToner.setError("Por favor entre com um modelo de Toner");
            txtCadToner.requestFocus();
            return false;
        }

        if (nomeSaida.isEmpty()) {
            txtCadSaida.setError("Por favor entre com uma data de saída");
            txtCadSaida.requestFocus();
            return false;
        }
        return true;
    }

    //Esse método faz a operação para adicionar os modelos de Toner
    private void adicionarModeloToner() {
        String nomeToner = txtCadToner.getText().toString().trim();
        String nomeEntrada = txtCadEntrada.getText().toString().trim();
        String nomeSaida = txtCadSaida.getText().toString().trim();
        String nomeSpnImpressoras = spnCadModeloImpressora.getSelectedItem().toString();

        //validando entrada
        if (verificarEntrada(nomeToner, nomeSaida)) {

            String insertSQL = "INSERT INTO Toner (" +
                    "nomeToner, " +
                    "nomeSpnImpressoras, " +
                    "nomeEntrada," +
                    "nomeSaida)" +
                    "VALUES(?, ?, ?, ?);";

            // usando o mesmo método execsql para inserir valores
            // desta vez tem dois parâmetros
            // primeiro é a string sql e segundo são os parâmetros que devem ser vinculados à consulta

            meuBancoDeDados.execSQL(insertSQL, new String[]{nomeToner, nomeSpnImpressoras, nomeEntrada, nomeSaida});

            Toast.makeText(getApplicationContext(), "Toner adicionado com sucesso!!!", Toast.LENGTH_SHORT).show();

        }
    }
    // este método irá criar a tabela
    private void criarTabelaToner() {
        meuBancoDeDados.execSQL(
                "CREATE TABLE IF NOT EXISTS Toner (" +
                        "id_toner integer PRIMARY KEY AUTOINCREMENT," +
                        "nomeToner varchar(10) NOT NULL," +
                        "nomeSpnImpressoras varchar(10) NOT NULL," +
                        "nomeEntrada datetime NOT NULL," +
                        "nomeSaida datetime NOT NULL );"
        );
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSalvar:
                adicionarModeloToner();
                break;
            case R.id.lblVisualizaToners:
                startActivity(new Intent(getApplicationContext(), TonerActivity.class));
                break;
        }

    }
}