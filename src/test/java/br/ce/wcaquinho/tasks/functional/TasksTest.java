package br.ce.wcaquinho.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    //Caminho onde fica o executável do chromedrive
    private static final String PATH_CHROME_DRIVER = "/Users/marcos.camara/Devtools/devops_pipeline/chromedriver";
    private static final String URL = "http://localhost:8001/tasks";

    public WebDriver createWebDriver(){
        //O ideal é configurar a variável de ambiente na máquina
        System.setProperty("webdriver.chrome.driver", PATH_CHROME_DRIVER);

        WebDriver driver = new ChromeDriver();
        driver.navigate().to(URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @Test
    public void deveSalvarTaskComSucesso(){

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2022");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Success!", message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao(){

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2022");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the task description", message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData(){

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Fill the due date", message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada(){

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id("addTodo")).click();

            //Escrever a descrição
            driver.findElement(By.id("task")).sendKeys("Teste via Selenium");

            //Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2019");

            //Clicar em salvar
            driver.findElement(By.id("saveButton")).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id("message")).getText();
            Assert.assertEquals("Due date must not be in past", message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }


}
