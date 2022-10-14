package com.jonnathan.gallegos.examenfinal_jonnathangallegos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.ModeloUsuario;

import de.hdodenhof.circleimageview.CircleImageView;

public class detailUser extends AppCompatActivity {

    private EditText cedula,nombre,contrasenia,permisos;
    private Button btnActualiza, btnElimina;

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
        Drawable originalDrawable = getResources().getDrawable(R.drawable.mishi);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        cedula = (EditText) findViewById(R.id.cedulaUC);
        nombre = (EditText) findViewById(R.id.nombreUC);
        contrasenia =  (EditText) findViewById(R.id.contraseniaUC);
        permisos = (EditText) findViewById(R.id.permisosUC);
        btnActualiza = (Button) findViewById(R.id.btnActualizar);
        btnElimina = (Button) findViewById(R.id.btnElimiar);
        imageView = findViewById(R.id.imageView);

        //  imageView.setImageDrawable(roundedDrawable);
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
        imageView.setImageURI(Uri.parse(fotoa));

        Toast.makeText(this, "Lo que me esta llegando-> "+cedulaID, Toast.LENGTH_SHORT).show();
    }

    private void EventButton(){
        btnActualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(detailUser.this);
                builder.setTitle("Modificar"); // set Title
                builder.setMessage("Confirmar cambios");  // set message
                builder.setCancelable(true); //  Sets whether the dialog is cancelable or not

                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();
                                modificarUser();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();

                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });


        btnElimina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                builder = new AlertDialog.Builder(detailUser.this);
                builder.setTitle("Eliminar Usuario"); // set Title
                builder.setMessage("Esta seguro de eliminar el usuario?");  // set message
                builder.setCancelable(true); //  Sets whether the dialog is cancelable or not

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();
                                eliminaUser();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.cancel();

                            }
                        });
                alertDialog = builder.create();
                alertDialog.show();
            }
        });

    }

    private void eliminaUser(){

        ModeloUsuario usuario = new ModeloUsuario();
        Toast.makeText(this, "Lo que esta para el eliminar-> "+cedula.getText().toString(), Toast.LENGTH_SHORT).show();
        if(usuario.eliminarUsuario(getApplicationContext(), "bd_usuario", null, 1, cedula.getText().toString())){
            Toast.makeText(getApplicationContext(), "Correcto al eliminar el user", Toast.LENGTH_SHORT).show();
            Intent inte = new Intent(getApplicationContext(), Menu.class);
            startActivity(inte);
            finish();
        }else{
            Toast.makeText(getApplicationContext(), "Error al elminar el user", Toast.LENGTH_SHORT).show();
        }
    }

    private void modificarUser(){
        ModeloUsuario usuario = new ModeloUsuario();
        usuario.setCedula(cedula.getText().toString());
        usuario.setNombre(nombre.getText().toString());
        usuario.setContrasenia(contrasenia.getText().toString());
        usuario.setPermisos(permisos.getText().toString());

        if(usuario.modificarPersona(getApplicationContext(), cedula.getText().toString())){
            Toast.makeText(getApplicationContext(), "El usuario se modifico satisfactoriamente", Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(getApplicationContext(), Menu.class);
                startActivity(inte);
                finish();
        }else{
            Toast.makeText(getApplicationContext(), "No se pudo modificar el usuario", Toast.LENGTH_SHORT).show();
        }

    }


}