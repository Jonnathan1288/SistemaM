package com.jonnathan.gallegos.examenfinal_jonnathangallegos;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.ModeloUsuario;
import com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo.Usuario;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AgregarRegistro extends AppCompatActivity {

    //View
    private CircleImageView profileIv;
    private EditText cedula,nombre,contrasenia;
    private Spinner permisos;

    private FloatingActionButton saveBtn, calcelBtn;

    //Actionbar
    private ActionBar actionBar;
    //Permiso de la clase Constants
    private  static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 101;
    //selección de imagen Constants
    private static final int IMAGE_PICK_CAMERA_CODE = 102;
    private static final int IMAGE_PICK_GALLERY_CODE = 103;
    // matrices de permisos
    private String[] cameraPermissions; // cámara y almacenamiento
    private String [] storagePermissions;// solo almacenamiento
    // variables (constain datos para guardar)
    private Uri imageUri;

    private AlertDialog alertDialog;
    private AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_registro);
        actionBar = getSupportActionBar();
        //Titulo
        actionBar.setTitle("Agregar Registro");
        //Boton Negro
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        AsignacionVariables();
        stringOfSpinner();
        EventButton();

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    private void stringOfSpinner(){
        String[] opc ={"Seleccione", "Administrador", "Coordinador", "Jefe de Área", "Solicitante"};
        //ArrayAdapter <String> adapter =new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opc);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this, R.layout.spinner_item_gt, opc);
        permisos.setAdapter(adapter);
    }

    private void AsignacionVariables(){
        profileIv = findViewById(R.id.profileIv);
        cedula = (EditText) findViewById(R.id.cedulaU);
        nombre = (EditText) findViewById(R.id.nombreU);
        contrasenia =  (EditText) findViewById(R.id.contraseniaU);
        permisos = (Spinner) findViewById(R.id.permisosU);
        saveBtn = findViewById(R.id.saveBtn);
        calcelBtn = findViewById(R.id.btnCancelarSave);
    }

    private void EventButton(){
        profileIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // muestra el cuadro de diálogo de selección de imagen
                imagePickDialog();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cedula.getText().toString().isEmpty() || nombre.getText().toString().isEmpty() || contrasenia.getText().toString().isEmpty() || permisos.getSelectedItem().toString().equals("Seleccione")) {
                    builder = new AlertDialog.Builder(AgregarRegistro.this);
                    builder.setTitle("Datos incompletos"); // set Title
                    builder.setMessage("Debe ingresar todos los campos solicitados.");  // set message
                    builder.setCancelable(true); //  Sets whether the dialog is cancelable or not
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog = builder.create();
                    alertDialog.show();
                } else {
                    if(imageUri == null) {
                        verificationImageNull();
                    }else{
                        CrearUser();
                    }
                }
            }
        });

        calcelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cedula.getText().toString().isEmpty()) {
                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                    startActivity(intent);
                } else {
                    builder = new AlertDialog.Builder(AgregarRegistro.this);
                    builder.setTitle("Cancelar"); // set Title
                    builder.setMessage("Seguro en cancelar el registro?");  // set message
                    builder.setCancelable(true); //  Sets whether the dialog is cancelable or not

                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.cancel();
                                    Intent intent = new Intent(getApplicationContext(), Menu.class);
                                    startActivity(intent);
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
            }
        });


    }


    private void verificationImageNull(){
        builder = new AlertDialog.Builder(AgregarRegistro.this);
        builder.setMessage("Seguro en no ingresar la foto?");  // set message
        builder.setCancelable(true); //  Sets whether the dialog is cancelable or not

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.cancel();
                        CrearUser();
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

    private void CrearUser(){
        ModeloUsuario usuario = new ModeloUsuario();
        usuario.setCedula(cedula.getText().toString());
        usuario.setNombre(nombre.getText().toString());
        usuario.setContrasenia(contrasenia.getText().toString());
        usuario.setPermisos(permisos.getSelectedItem().toString());
        if(imageUri == null){
            usuario.setFoto(null);
        }else{
            usuario.setFoto(imageUri.toString());
        }

        if(cedulaRepetida(cedula.getText().toString())){
            Toast.makeText(getApplicationContext(), "La cedula ya esta en la BD", Toast.LENGTH_SHORT).show();
        }else{
            if(usuario.guardarUsuario(getApplicationContext(), "bd_usuario", null, 1)){
                Toast.makeText(getApplicationContext(), "La persona se creo correctamente", Toast.LENGTH_SHORT).show();
                Intent inte = new Intent(getApplicationContext(), Menu.class);
                startActivity(inte);
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "No se pudo crear la persona.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean cedulaRepetida(String ci){
        ModeloUsuario usuario = new ModeloUsuario();
        List<Usuario> listap = usuario.Listar(getApplicationContext(), "bd_usuario", null, 1);
        for (int i = 0; i < listap.size(); i++){
            if(listap.get(i).getCedula().equals(ci)){
                return true;
            }
        }
        return false;
    }

    private void imagePickDialog(){
        // opciones para mostrar en el diálogo
        String[] options = {"Camara", "Galeria"};
        //dialogo
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Titulo
        builder.setTitle("Seleccionar imagen");
        // establecer elementos / opciones
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // manejar clicks
                if (which==0){
                    //click en camara
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromCamera();
                    }

                }
                else if (which==1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }
                    else{
                        // permiso ya otorgado
                        PickFromGallery();
                    }
                }
            }
        });

        // Crear / mostrar diálogo
        builder.create().show();
    }

    private void PickFromGallery() {
        // intento de elegir la imagen de la galería, la imagen se devolverá en el método onActivityResult
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_CODE);
    }

    private void PickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Titulo de la Imagen");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Descripción de la imagen");
        //put image Uri
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        // Intento de abrir la cámara para la imagen
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);
    }

    private boolean checkStoragePermission(){
        //comprobar si el permiso de almacenamiento está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result;
    }

    private  void requestStoragePermission(){
        // solicita el permiso de almacenamiento
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }

    private boolean checkCameraPermission(){
        // verifica si el permiso de la cámara está habilitado o no
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result && result1;
    }

    private void requestCameraPermission(){
        // solicita el permiso de la cámara
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed(); //regrese haciendo clic en el botón de barra de acción
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // resultado del permiso permitido / denegado

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length>0){

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if(cameraAccepted && storageAccepted){
                        // ambos permisos permitidos
                        PickFromCamera();
                    }
                    else{
                        Toast.makeText(this, "Se requieren permisos de cámara y almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE:{
                if (grantResults.length>0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted){
                        PickFromGallery();
                    }
                    else{
                        Toast.makeText(this, "Se requiere permiso de almacenamiento", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //image picked from camera or gallery will be received hare
        if (resultCode == RESULT_OK){
            //Image is picked
            if(requestCode == IMAGE_PICK_GALLERY_CODE){
                //Picked from gallery

                //crop image
                CropImage.activity(data.getData())
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if(requestCode == IMAGE_PICK_CAMERA_CODE){
                //Picked from camera
                //crop Image
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);

            }
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                //Croped image received
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK){
                    Uri resultUri = result.getUri();
                    imageUri = resultUri;
                    //set Image
                    profileIv.setImageURI(resultUri);
                }
                else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                    //ERROR
                    Exception error = result.getError();
                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show();
                }

            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}