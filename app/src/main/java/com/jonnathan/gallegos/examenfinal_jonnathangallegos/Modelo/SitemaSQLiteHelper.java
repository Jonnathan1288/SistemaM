package com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SitemaSQLiteHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    public static final String TABLE_USUARIO = "t_usuario";
    public SitemaSQLiteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USUARIO + "(" +
                "id_persona INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ci TEXT," +
                "nombre TEXT," +
                "contrasenia TEXT," +
                "permisos TEXT,"+
                "foto TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + TABLE_USUARIO);
        onCreate(db);
    }

}
