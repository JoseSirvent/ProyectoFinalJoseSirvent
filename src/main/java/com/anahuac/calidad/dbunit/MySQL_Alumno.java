package com.anahuac.calidad.dbunit;

import com.anahuac.calidad.doublesDAO.Alumno;
import com.anahuac.calidad.doublesDAO.AlumnoDAO;

import java.sql.*;

public class MySQL_Alumno implements AlumnoDAO{

    public Connection getConnectionMySQL() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Para cuando conecte en travis cambiar localhost por 127.0.0.1, cambiar el puerto por 3306
            //Quitar contraseña "" **cambiar "secret"
            conn = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/Alumnos_db", "root", ""
            );
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    @Override
    public boolean agregarAlumno(Alumno a){
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        boolean result = false;
        try {
            preparedStatement = connection.prepareStatement(
                    "insert INTO alumnos_tbl(nombre, id, edad, email) values (?, ?, ?, ?)"
            );
            preparedStatement.setString(1, a.getNombre());
            preparedStatement.setString(2, a.getId());
            preparedStatement.setInt(3, a.getEdad());
            preparedStatement.setString(4, a.getEmail());

            if(preparedStatement.executeUpdate() >= 1){
                result = true;
            }

            connection.close();

        } catch(Exception e){
            System.out.println(e);
        }
        return result;
    }

    @Override
    public boolean borrarAlumno(Alumno a){
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        boolean result = false;

        try {
            preparedStatement = connection.prepareStatement("Delete from alumnos_tbl WHERE id = ?");

            preparedStatement.setString(1, a.getId());

            if(preparedStatement.executeUpdate() >= 1){
                result = true;
            }

            connection.close();
        } catch(SQLException e) {
            System.out.println(e);
        }
        return result;
    }

    @Override
    public boolean actualizarEmail(Alumno a) {
        Connection connection = getConnectionMySQL();
        boolean result = false;
        PreparedStatement preparedStatement;

        try {
            preparedStatement = connection.prepareStatement("UPDATE alumnos_tbl SET email = ? WHERE id = ?");
            preparedStatement.setString(1, a.getEmail());
            preparedStatement.setString(2, a.getId());
            if(preparedStatement.executeUpdate() >= 1){
                result = true;
            }

            connection.close();
        } catch(SQLException e) {
            System.out.println(e);
        }

        return result;
    }

    @Override
    public Alumno consultarAlumno(String id){
        Connection connection = getConnectionMySQL();
        PreparedStatement preparedStatement;
        ResultSet rs;

        Alumno alum_encontrado = null;

        try{
            preparedStatement = connection.prepareStatement("SELECT * FROM alumnos_tbl WHERE id = ?");
            preparedStatement.setString(1, id);
            rs = preparedStatement.executeQuery();

            rs.next();

            String nombre_encontrado = rs.getString(1);
            String id_encontrado = rs.getString(2);
            int edad_encontrada = rs.getInt(3);
            String email_encontrado = rs.getString(4);

            alum_encontrado = new Alumno(nombre_encontrado, id_encontrado, edad_encontrada, email_encontrado);

            rs.close();
            connection.close();
        } catch(SQLException e){
            System.out.println(e);
        }
        return alum_encontrado;
    }

}
