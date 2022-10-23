package com.jonnathan.gallegos.examenfinal_jonnathangallegos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.ModeloUsuario;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;

public class detailUser extends AppCompatActivity {

    private TextView cedula,nombre,contrasenia;
    private TextView permisos;
    private Button btnAceptar;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;

    private CircleImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        AsignacionVariables();
        intent();
        EventButton();
    }

    private void AsignacionVariables(){

        //Metodo que me permite redondear una imagen view--------------------
        Drawable originalDrawable = getResources().getDrawable(R.drawable.mishi);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        //--------------

        cedula = (TextView) findViewById(R.id.cedulaUC);
        nombre = (TextView) findViewById(R.id.nombreUC);
        contrasenia =  (TextView) findViewById(R.id.contraseniaUC);
        permisos = (TextView) findViewById(R.id.permisosUC);
        btnAceptar = (Button) findViewById(R.id.btnActualizar);

        imageView = findViewById(R.id.imageView);

        //  imageView.setImageDrawable(roundedDrawable); set the variable
        cedula.setEnabled(false);

    }

    private void intent(){
        Intent intent = getIntent();
        String cedulaID = intent.getStringExtra("CEDULA");
        String nombres = intent.getStringExtra("NOMBRE");
        String contra = intent.getStringExtra("CONTRA");
        String permiso = intent.getStringExtra("PERMISO");
        String fotoa = intent.getStringExtra("FOTO");
        cedula.setText(cedulaID);
        nombre.setText(nombres);
        contrasenia.setText(contra);
        permisos.setText(permiso);
        if (fotoa.equals("null")){
            imageView.setImageResource(R.drawable.mishi);
        }else {
            imageView.setImageURI(Uri.parse(fotoa));
        }

        Toast.makeText(this, "Lo que me esta llegando-> "+cedulaID, Toast.LENGTH_SHORT).show();
    }

    private void EventButton(){
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void eliminaUser(){

        ModeloUsuario usuario = new ModeloUsuario();
        Toast.makeText(this, "Lo que esta para el eliminar-> "+cedula.getText().toString(), Toast.LENGTH_SHORT).show();
        if(usuario.eliminarUsuario(getApplicationContext(), cedula.getText().toString())){
            Toast.makeText(getApplicationContext(), "Correcto al eliminar el user", Toast.LENGTH_SHORT).show();
            Intent inte = new Intent(getApplicationContext(), Menu.class);
            startActivity(inte);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Error al elminar el user", Toast.LENGTH_SHORT).show();
        }
    }


}