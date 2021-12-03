package com.anahuac.calidad.doublesDAO;

public class Alumnodao_fake implements AlumnoDAO {

    @Override
    public boolean agregarAlumno (Alumno a) {
        return false;
    }

    @Override
    public boolean borrarAlumno(Alumno a) {
        return false;
    }

    @Override
    public boolean actualizarEmail (Alumno a){
        return false;
    }

    @Override
    public Alumno consultarAlumno (String id) {
        return null;
    }
}
