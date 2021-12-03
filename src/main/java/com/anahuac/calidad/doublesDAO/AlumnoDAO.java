package com.anahuac.calidad.doublesDAO;

public interface AlumnoDAO {
    public boolean agregarAlumno(Alumno a);
    public boolean borrarAlumno(Alumno a);
    public boolean actualizarEmail(Alumno a);
    public Alumno consultarAlumno(String id);

}
