package com.jonnathan.gallegos.examenfinal_jonnathangallegos;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
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

    private AlertDialog alertDialog;

    private AlertDialog.Builder builder;

    //Views
    private FloatingActionButton addRecordBtn;

    //RecyclerView
    private RecyclerView recordsRv;

    //DB Helper
    private SitemaSQLiteHelper dbHelper;

    //Action Bar
    ActionBar actionBar;

    //Boolean que permite el eliminado del user.
    private boolean deleteUser = false;

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
                Intent intent = new Intent(getApplicationContext(), AgregarRegistro.class);
                intent.putExtra("EDIT_USER", false);
                startActivity(intent);
            }
        });

    }

    private void delete_User(){
        String valorCI =  AdapterRecord.ci;
        if(!(valorCI == null)){
            builder = new AlertDialog.Builder(Menu.this);
            builder.setTitle("Eliminar Usuario"); // set Title
            builder.setMessage("Esta seguro de eliminar el usuario?");  // set message
            builder.setCancelable(true); //  Sets whether the dialog is cancelable or not

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                            eliminaUser(valorCI);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                            AdapterRecord.ci = null;
                        }
                    });
            alertDialog = builder.create();
            alertDialog.show();

        }else{
            //Si el valir me llega null entonces no se mandara ninguna warning..
        }
    }

    private void eliminaUser(String cedulaUser){
        ModeloUsuario usuario = new ModeloUsuario();
        if(usuario.eliminarUsuario(getApplicationContext(), cedulaUser)){
            AdapterRecord.ci = null;
            Toast.makeText(getApplicationContext(), "Correcto al eliminar el user", Toast.LENGTH_SHORT).show();
            loadRecords();
        }else{
            Toast.makeText(getApplicationContext(), "Error al elminar el user", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadRecords(){
        ModeloUsuario modeloUsuario = new ModeloUsuario();
        AdapterRecord adapterRecord = new AdapterRecord(Menu.this,
                modeloUsuario.Listar(getApplicationContext()));
        recordsRv.setAdapter(adapterRecord);

        //Establecer el numero de Registros
        actionBar.setSubtitle("Total: "+modeloUsuario.getRecordsCount(getApplicationContext()));
    }

    private void searchRecords(String query){
        ModeloUsuario modeloUsuario = new ModeloUsuario();
        AdapterRecord adapterRecord = new AdapterRecord(Menu.this,
                modeloUsuario.searchLike(getApplicationContext(), query));
        recordsRv.setAdapter(adapterRecord);
    }


    @Override
    public void onResume(){ // protected
        super.onResume();
        loadRecords();
        delete_User();
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