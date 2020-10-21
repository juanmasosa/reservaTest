package com.example.reservatest;

public class Usuario {

    String usuario;
    String contraseña;
    String telefono;
    String email;

    public Usuario(){
    }

    public Usuario(String usuario, String contraseña, String telefono, String email) {
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
