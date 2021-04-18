package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    //Caminho onde fica o executável do chromedrive
    private static final String PATH_CHROME_DRIVER = "/Users/marcos.camara/Devtools/devops_pipeline/chromedriver";
    private static final String URL = "http://192.168.15.28:8001/tasks";

    public WebDriver createWebDriver() throws MalformedURLException {
        //O ideal é configurar a variável de ambiente na máquina
        // A solução aqui utiliza o chromedriver local
        //System.setProperty("webdriver.chrome.driver", PATH_CHROME_DRIVER);

        //WebDriver driver = new ChromeDriver();
        //driver.navigate().to(URL);
        //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        /* A solução aqui utiliza um servidor */
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.15.28:4444/wd/hub"), cap);
        driver.navigate().to(URL);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTaskComSucesso() throws MalformedURLException {

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
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException {

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
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException {

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
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException {

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
