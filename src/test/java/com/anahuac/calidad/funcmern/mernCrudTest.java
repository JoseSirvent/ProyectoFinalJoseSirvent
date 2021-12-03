package com.anahuac.calidad.funcmern;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class mernCrudTest {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    JavascriptExecutor js;
    @Before
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
        js = (JavascriptExecutor) driver;
    }
    
    String correo_global;

    @Test
    public void testagregarCrud() throws Exception {
        
        int aleatorio1 = new Random().nextInt();

        driver.get("https://mern-crud.herokuapp.com/");
        driver.findElement(By.xpath("/html/body/div/div/div[2]/button")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("jose sirvent");
        driver.findElement(By.name("email")).clear();
        
        correo_global = ("josesirvent" + String.valueOf(aleatorio1) +"@gmail.com");
        
        driver.findElement(By.name("email")).sendKeys(correo_global);
        driver.findElement(By.name("age")).clear();
        driver.findElement(By.name("age")).sendKeys("20");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Gender'])[2]/following::div[1]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Male'])[1]/following::div[2]")).click();
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Woah!'])[1]/following::button[1]")).click();

        wait(5);

        String checar_agregar = driver.findElement(By.xpath("/html/body/div[2]/div/div[2]/form/div[4]/div/div")).getText();
        assertThat("Nice one!", is(checar_agregar));

    }
    
    @Test
    public void testactualizarCrud() throws Exception {
        
        int aleatorio2 = new Random().nextInt();

        driver.get("https://mern-crud.herokuapp.com/");
        driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr/td[5]/button[1]")).click();
        driver.findElement(By.name("name")).click();
        driver.findElement(By.name("name")).clear();
        driver.findElement(By.name("name")).sendKeys("pedro oliva");
        driver.findElement(By.name("email")).clear();
        
        correo_global = ("pedrooliva" + String.valueOf(aleatorio2) +"@gmail.com");
        
        driver.findElement(By.name("email")).sendKeys(correo_global);
        driver.findElement(By.name("age")).click();
        driver.findElement(By.name("age")).clear();
        driver.findElement(By.name("age")).sendKeys("34");
        driver.findElement(By.name("age")).clear();
      

        wait(5);

        String checar_edad = driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr[2]/td[3]")).getText();
        assertThat("20", is(not(checar_edad)));
        
        String checar_nombre = driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr[2]/td[3]")).getText();
        assertThat("jose sirvent", is(not(checar_nombre)));
        
        String checar_correo = driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr/td[2]")).getText();
        assertThat(correo_global, is(not(checar_correo)));

    }
    
    @Test
    public void testeliminarCrud() throws Exception {
        
        driver.get("https://mern-crud.herokuapp.com/");
        String boton_delete = driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr[1]/td[5]/button[2]")).getText();
        
        driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr[1]/td[5]/button[2]")).click();
        wait(5);
        driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/button[1]")).click();
        wait(5);
        assertThat("Delete", is(not(boton_delete)));

    }
    
    @Test
    public void testbuscarCrud() throws Exception {
        driver.get("https://mern-crud.herokuapp.com/");
        String correo_a_buscar = driver.findElement(By.xpath("/html/body/div/div/div[2]/table/tbody/tr[1]/td[2]")).getText();
        assertThat(correo_global, is((correo_a_buscar)));

    }
    
    
    public void wait(int sec) {
        try {
            Thread.currentThread().sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }
}
