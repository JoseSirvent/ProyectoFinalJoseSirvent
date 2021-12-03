package com.anahuac.calidad.doublesDAO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


import java.util.HashMap;

import static org.junit.Assert.*;

public class Alumnodao_fakeTest {

    private Alumnodao_fake dao;
    public HashMap<String, Alumno> map_a_alumnos;
    
    Alumno new_obj_alum;
    
    String newemail = "sirvent4@gmail.com"; 

    @Before
    public void setUp() throws Exception {
        dao = Mockito.mock(Alumnodao_fake.class);
        map_a_alumnos = new HashMap<String, Alumno>();
        new_obj_alum = new Alumno("Jose", "003", 20, "sirventpepe@gmail.com");
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void agregarAlumTest(){

        int before = map_a_alumnos.size();
        System.out.println("Size before = " + before);
        doAnswer(new Answer() {
		        public Object answer(InvocationOnMock invocation) {
		            Alumno arg = (Alumno) invocation.getArguments()[0];
		            map_a_alumnos.put(anyString(), arg);
		            System.out.println("Size despues = " + map_a_alumnos.size());
		            return null;
		        }
		    }
        )
        
        .when(dao).agregarAlumno(any(Alumno.class));

        dao.agregarAlumno(new_obj_alum);
        int after = map_a_alumnos.size();

        assertThat(before + 1, is(after));
    }

    @Test
    public void borrarAlumnoTest(){
    	map_a_alumnos.put("003", new_obj_alum);
        int before = map_a_alumnos.size();
        System.out.println("Size antes = " +  before);
       
		when(dao.borrarAlumno(any(Alumno.class))).thenAnswer(new Answer<Boolean>() {

			public Boolean answer(InvocationOnMock invocation) throws Throwable{

				Alumno arg = (Alumno) invocation.getArguments()[0]; 
				map_a_alumnos.remove(arg.getId(), arg); 
				System.out.println("Size despues=" + map_a_alumnos.size() + "\n"); 

				return true; 
				}
			}
		);
		dao.borrarAlumno(new_obj_alum);
		int after = map_a_alumnos.size();
		
		assertThat(before - 1, is(after));
			
    }
    
    @Test
    public void actualizarCorreoTest() {
    	map_a_alumnos.put("003", new_obj_alum);
        String actualEmail = new_obj_alum.getEmail();
    	System.out.println("actualemail = " + actualEmail);
    	
    	when(dao.actualizarEmail(any(Alumno.class))).thenAnswer(new Answer<Boolean>() {

			public Boolean answer(InvocationOnMock invocation) throws Throwable{

				Alumno arg = (Alumno) invocation.getArguments()[0]; 
				map_a_alumnos.replace(arg.getId(), arg); 

				return true; 
				}
			}
		);
    	
    	new_obj_alum.setEmail(newemail);
    	dao.actualizarEmail(new_obj_alum);
    	
    	assertThat(actualEmail, is(not(newemail)));
    	System.out.println("El nuevo correo es = " + newemail + "\n");
    }
    
    @Test
    public void buscarAlumnoTest() {
		when(dao.consultarAlumno(anyString())).thenAnswer(new Answer<Alumno>() {
			public Alumno answer(InvocationOnMock invocation) throws Throwable{
				String arg = (String) invocation.getArguments()[0]; 
				return new_obj_alum; 
				}
			}
		);
		
		Alumno res = dao.consultarAlumno("003");
		
		assertThat(new_obj_alum.getId(), is(res.getId()));
		assertThat(new_obj_alum.getEdad(), is(res.getEdad()));
		assertThat(new_obj_alum.getNombre(), is(res.getNombre()));
		assertThat(new_obj_alum.getEmail(), is(res.getEmail()));
		
		System.out.println("Busqueda Completada \n");
    }
}