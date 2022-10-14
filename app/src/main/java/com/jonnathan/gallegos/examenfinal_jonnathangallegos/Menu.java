package com.jonnathan.gallegos.examenfinal_jonnathangallegos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Adapter.AdapterRecord;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.ModeloUsuario;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.SitemaSQLiteHelper;

public class Menu extends AppCompatActivity {

    //Views
    private FloatingActionButton addRecordBtn;

    //RecyclerView
    private RecyclerView recordsRv;

    //DB Helper
    private SitemaSQLiteHelper dbHelper;

    //Action Bar
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        addRecordBtn = findViewById(R.id.addRecordBtn);
        recordsRv = findViewById(R.id.recordsRv);
        dbHelper = new SitemaSQLiteHelper(getApplicationContext(), "bd_usuario", null, 1);

        actionBar = getSupportActionBar();
        actionBar.setTitle("Registros");

        // Cargando Registros
        loadRecords();

        // Click para Iniciar a añadir y grabar en la activity
        addRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Iniciar la Activity
                startActivity(new Intent(getApplicationContext(), AgregarRegistro.class));
            }
        });
    }
    private void loadRecords(){
        ModeloUsuario modeloUsuario = new ModeloUsuario();
        AdapterRecord adapterRecord = new AdapterRecord(Menu.this,
                modeloUsuario.Listar(getApplicationContext(), "bd_usuario", null, 1));
        recordsRv.setAdapter(adapterRecord);

        //Establecer el numero de Registros
       // actionBar.setSubtitle("Total: "+modeloUsuario.getRecordsCount(getApplicationContext()));
    }

    private void searchRecords(String query){
        ModeloUsuario modeloUsuario = new ModeloUsuario();
        AdapterRecord adapterRecord = new AdapterRecord(Menu.this,
                modeloUsuario.searchLike(getApplicationContext(), query));
        recordsRv.setAdapter(adapterRecord);
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadRecords();
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecords(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchRecords(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}