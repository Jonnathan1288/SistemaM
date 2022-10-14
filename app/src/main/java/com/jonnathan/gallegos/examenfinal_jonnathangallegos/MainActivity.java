package com.jonnathan.gallegos.examenfinal_jonnathangallegos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.SitemaSQLiteHelper;

public class MainActivity extends AppCompatActivity {

    private Button btnCamera, btncrud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AsignacionVariables();
        Event();
    }

    private void AsignacionVariables(){
        btnCamera = (Button) findViewById(R.id.btnCamera);
        btncrud = (Button) findViewById(R.id.btnRegistro);
    }

    private void Event(){
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Camara.class);
                startActivity(intent);
            }
        });

        btncrud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
            }
        });
    }
}