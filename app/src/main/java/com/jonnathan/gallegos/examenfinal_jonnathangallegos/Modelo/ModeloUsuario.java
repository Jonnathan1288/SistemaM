package com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


import java.util.ArrayList;


public class ModeloUsuario extends Usuario{
    public ModeloUsuario() {
    }

    public ModeloUsuario(String cedula, String nombre, String contrasenia, String permisos, String foto) {
        super(cedula, nombre, contrasenia, permisos, foto);
    }

    public ModeloUsuario(int id_usuario, String cedula, String nombre, String contrasenia, String permisos, String foto) {
        super(id_usuario, cedula, nombre, contrasenia, permisos, foto);
    }

   public boolean guardarUsuario(Context person, String nombreBD, SQLiteDatabase.CursorFactory valor, int version){
        try {
            SitemaSQLiteHelper cp = new SitemaSQLiteHelper(person, nombreBD, valor, version);
            String sql = "INSERT INTO t_usuario(ci,nombre,contrasenia, permisos, foto) VALUES ('"+getCedula()+"','"+getNombre()+"','"+getContrasenia()+"','"+getPermisos()+"', '"+getFoto()+"')";
            cp.getWritableDatabase().execSQL(sql);
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }
    }

    public ArrayList<Usuario> Listar(Context person, String nombreBD, SQLiteDatabase.CursorFactory valor, int version){
        ArrayList<Usuario> listarUsuario = new ArrayList<>();
        SitemaSQLiteHelper cp = new SitemaSQLiteHelper(person, nombreBD, valor, version);
        Cursor cursobd = cp.getReadableDatabase().rawQuery("select * from t_usuario", null);

        if(cursobd.moveToFirst()){
            do {
                Usuario user = new Usuario();
                user.setId_usuario(cursobd.getInt(0));
                user.setCedula(cursobd.getString(1));
                user.setNombre(cursobd.getString(2));
                user.setContrasenia(cursobd.getString(3));
                user.setPermisos(cursobd.getString(4));
                user.setFoto(cursobd.getString(5));
                listarUsuario.add(user);
            }while (cursobd.moveToNext());
        }
        return listarUsuario;
    }

    public ArrayList<Usuario> searchWithCI(Context person, String cedulau){
        ArrayList<Usuario> listarUsuario = new ArrayList<>();
        SitemaSQLiteHelper cp = new SitemaSQLiteHelper(person, "t_usuario", null, 1);
        Cursor cursobd = cp.getReadableDatabase().rawQuery("select * from t_usuario where cedula = '"+cedulau+"'", null);

        if(cursobd.moveToFirst()){

            do {
                Usuario user = new Usuario();
                user.setId_usuario(cursobd.getInt(0));
                user.setCedula(cursobd.getString(1));
                user.setNombre(cursobd.getString(2));
                user.setContrasenia(cursobd.getString(3));
                user.setPermisos(cursobd.getString(4));
                user.setFoto(cursobd.getString(5));
                listarUsuario.add(user);
            }while (cursobd.moveToNext());
        }
        return listarUsuario;
    }

    public boolean modificarPersona(Context user, String ci){
        try {
            SitemaSQLiteHelper cp = new SitemaSQLiteHelper(user, "bd_usuario", null, 1);
            String sql = "UPDATE t_usuario set nombre='"+getNombre()+"', contrasenia='"+getContrasenia()+"', permisos='"+getPermisos()+"' where ci = '"+ci+"'";
            cp.getWritableDatabase().execSQL(sql);
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }
    }

    public boolean eliminarUsuario(Context person, String nombreBD, SQLiteDatabase.CursorFactory valor, int versionString, String ci){
        try {
            SitemaSQLiteHelper cp = new SitemaSQLiteHelper(person, nombreBD, valor, versionString);
            SQLiteDatabase db = cp.getWritableDatabase();
            String sql = "DELETE FROM t_usuario WHERE ci = '" + ci + "'";
            Toast.makeText(person, ""+sql, Toast.LENGTH_SHORT).show();
            db.execSQL(sql);
            return true;
        }catch (Exception e){
            e.toString();
            return false;
        }
    }


    public ArrayList<Usuario> searchLike(Context person, String id){
        ArrayList<Usuario> listarUsuario = new ArrayList<>();
        SitemaSQLiteHelper cp = new SitemaSQLiteHelper(person, "bd_usuario", null, 1);
        Cursor cursobd = cp.getReadableDatabase().rawQuery("select * from t_usuario where ci like '"+id+"%' or nombre like '"+id+"%'", null);

        if(cursobd.moveToFirst()){
            do {
                Usuario user = new Usuario();
                user.setId_usuario(cursobd.getInt(0));
                user.setCedula(cursobd.getString(1));
                user.setNombre(cursobd.getString(2));
                user.setContrasenia(cursobd.getString(3));
                user.setPermisos(cursobd.getString(4));
                user.setFoto(cursobd.getString(5));
                listarUsuario.add(user);
            }while (cursobd.moveToNext());
        }
        return listarUsuario;
    }

}
