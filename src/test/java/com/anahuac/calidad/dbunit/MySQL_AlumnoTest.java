package com.anahuac.calidad.dbunit;

import com.anahuac.calidad.doublesDAO.Alumno;
import org.dbunit.Assertion;
import org.dbunit.DBTestCase;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.dbunit.database.QueryDataSet;

import java.io.File;

public class MySQL_AlumnoTest extends DBTestCase {

    String newEmail = "sirvent1827@gmail.com";
    private MySQL_Alumno daoSQL; 
	
	public MySQL_AlumnoTest(){
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, "com.mysql.cj.jdbc.Driver");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, "jdbc:mysql://127.0.0.1:3306/Alumnos_db");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, "root");
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, "");
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/resources/inidb.xml"));
    }

    @Before
    public void setUp() throws Exception{
        
    	daoSQL = new MySQL_Alumno();
    	
    	IDatabaseConnection connection = getConnection();
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, getDataSet());
        } catch (Exception e){
            fail("Error in setup: " + e.getMessage());
        } finally {
            connection.close();
        }
    }

    @After
    public void tearDown() throws Exception{
    }

    @Test
    public void testAgregarAlumno() {
        Alumno alumno = new Alumno("Pedro", "008", 29, "pedro29@gmail.com");

        daoSQL.agregarAlumno(alumno);

        try {
            IDataSet databaseDataSet = getConnection().createDataSet(); 
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/insertar_resultado.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e) {
            fail("Error in insert test: " + e.getMessage());
        }

    }
    
    @Test
    public void testEliminarAlumno() {
        Alumno alumno = new Alumno("Santiago", "398", 22, "santiago22@gmail.com");

        daoSQL.borrarAlumno(alumno);

        try {
            IDataSet databaseDataSet = getConnection().createDataSet(); 
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/eliminar_resultado.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e) {
            fail("Error in insert test: " + e.getMessage());
        }
    }
    
    @Test
    public void testActualizarAlumno() {
        Alumno alumno = new Alumno("Santiago", "398", 22, "santiago22@gmail.com");
        
        alumno.setEmail(newEmail);
        
        daoSQL.actualizarEmail(alumno);

        try {
            IDataSet databaseDataSet = getConnection().createDataSet(); 
            ITable actualTable = databaseDataSet.getTable("alumnos_tbl");

            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/actualizar_resultado.xml"));
            ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");

            Assertion.assertEquals(expectedTable, actualTable);
        } catch (Exception e) {
            fail("Error in insert test: " + e.getMessage());
        }
    }

    @Test
    public void testBuscarAlumno() {
		Alumno alum_encontrado = daoSQL.consultarAlumno("783");
		String query = "SELECT * FROM alumnos_tbl WHERE id = \"783\"";
		try {
			IDataSet databaseDataSet = getConnection().createDataSet();
			QueryDataSet actualTable = new QueryDataSet(getConnection());
			actualTable.addTable("alumnosTemp_tbl", query);

			IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(new File("src/resources/buscar_resultado.xml"));
			ITable expectedTable = expectedDataSet.getTable("alumnos_tbl");
			Assertion.assertEquals(expectedTable, actualTable.getTable("alumnosTemp_tbl"));

		} catch (Exception e) {
			fail("Error in insert test: " + e.getMessage());
		}
	}
}