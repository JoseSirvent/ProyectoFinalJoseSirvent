package com.anahuac.calidad.doublesDAO;

public class Alumno {

    private String nombre, id, email;
    private int edad;

    public Alumno() {}

    public Alumno(String nombre, String id, int edad, String email) {
        this.nombre = nombre;
        this.id     = id;
        this.email  = email;
        this.edad   = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

}
