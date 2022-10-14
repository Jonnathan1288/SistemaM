package com.jonnathan.gallegos.examenfinal_jonnathangallegos.Modelo;

public class Usuario {
    private int id_usuario;
    private String cedula;
    private String nombre;
    private String contrasenia;
    private String permisos;
    private String foto;

    public Usuario() {
    }

    public Usuario(String cedula, String nombre, String contrasenia, String permisos, String foto) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.foto= foto;
    }

    public Usuario(int id_usuario, String cedula, String nombre, String contrasenia, String permisos, String foto) {
        this.id_usuario = id_usuario;
        this.cedula = cedula;
        this.nombre = nombre;
        this.contrasenia = contrasenia;
        this.permisos = permisos;
        this.foto= foto;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

}
