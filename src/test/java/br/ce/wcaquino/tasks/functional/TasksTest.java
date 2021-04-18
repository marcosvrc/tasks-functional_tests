package br.ce.wcaquino.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    //Caminho onde fica o executável do chromedrive
    private static final String PATH_CHROME_DRIVER = "/Users/marcos.camara/Devtools/devops_pipeline/chromedriver";
    private static final String URL = "http://192.168.15.28:8001/tasks";

    //Fields e Buttons
    private static final String ID_FIELD_TASK = "task";
    private static final String ID_FIELD_DATE = "dueDate";
    private static final String ID_BUTTON_SAVE = "saveButton";
    private static final String ID_BUTTON_ADD = "addTodo";
    private static final String ID_FIELD_MESSAGE = "message";

    //PATH
    private static final String PATH_BUTTON_DELETE = "//a[@class='btn btn-outline-danger btn-sm";

    //Messages
    private static final String MSG_SUCCESS = "Success!";
    private static final String MSG_ERROR_FIELD_TASK_EMPTY = "Fill the task description";
    private static final String MSG_ERROR_FIELD_DATE_EMPTY = "Fill the due date";
    private static final String MSG_ERROR_DATE_PAST = "Due date must not be in past";


    public WebDriver createWebDriver() throws MalformedURLException {
        /*O ideal é configurar a variável de ambiente na máquina
         A solução aqui utiliza o chromedriver local
        System.setProperty("webdriver.chrome.driver", PATH_CHROME_DRIVER);

        WebDriver driver = new ChromeDriver();
        driver.navigate().to(URL);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);*/

        /* A solução aqui utiliza um servidor */
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.15.28:4444/wd/hub"), cap);
        driver.navigate().to(URL);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return driver;
    }

    @Test
    public void deveSalvarTaskComSucesso() throws MalformedURLException, InterruptedException {

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id(ID_BUTTON_ADD)).click();

            //Escrever a descrição
            driver.findElement(By.id(ID_FIELD_TASK)).sendKeys("Teste via Selenium");

            //Escrever a data
            driver.findElement(By.id(ID_FIELD_DATE)).sendKeys("10/10/2022");

            //Clicar em salvar
            driver.findElement(By.id(ID_BUTTON_SAVE)).click();
            Thread.sleep(2000);

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id(ID_FIELD_MESSAGE)).getText();
            Assert.assertEquals(MSG_SUCCESS, message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() throws MalformedURLException, InterruptedException {

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id(ID_BUTTON_ADD)).click();

            //Escrever a data
            driver.findElement(By.id(ID_FIELD_DATE)).sendKeys("10/10/2022");

            //Clicar em salvar
            driver.findElement(By.id(ID_BUTTON_SAVE)).click();
            Thread.sleep(2000);

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id(ID_FIELD_MESSAGE)).getText();
            Assert.assertEquals(MSG_ERROR_FIELD_TASK_EMPTY, message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() throws MalformedURLException, InterruptedException {

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id(ID_BUTTON_ADD)).click();

            //Escrever a descrição
            driver.findElement(By.id(ID_FIELD_TASK)).sendKeys("Teste via Selenium");

            //Clicar em salvar
            driver.findElement(By.id(ID_BUTTON_SAVE)).click();
            Thread.sleep(2000);
            //Validar mensagem de sucesso
            String message = driver.findElement(By.id(ID_FIELD_MESSAGE)).getText();
            Assert.assertEquals(MSG_ERROR_FIELD_DATE_EMPTY, message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() throws MalformedURLException, InterruptedException {

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id(ID_BUTTON_ADD)).click();

            //Escrever a descrição
            driver.findElement(By.id(ID_FIELD_TASK)).sendKeys("Teste via Selenium");

            //Escrever a data
            driver.findElement(By.id(ID_FIELD_DATE)).sendKeys("10/10/2019");

            //Clicar em salvar
            driver.findElement(By.id(ID_BUTTON_SAVE)).click();
            Thread.sleep(2000);

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id(ID_FIELD_MESSAGE)).getText();
            Assert.assertEquals(MSG_ERROR_DATE_PAST, message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }

    /*@Test
    public void deveRemoverTarefaComSucesso() throws MalformedURLException {

        WebDriver driver = createWebDriver();

        try{

            //Clicar em AddTodo
            driver.findElement(By.id(ID_BUTTON_ADD)).click();
            //Escrever a descrição
            driver.findElement(By.id(ID_FIELD_TASK)).sendKeys("Teste via Selenium");
            //Escrever a data
            driver.findElement(By.id(ID_FIELD_DATE)).sendKeys("10/10/2022");
            //Clicar em salvar
            driver.findElement(By.id(ID_BUTTON_SAVE)).click();

            //Validar mensagem de sucesso
            String message = driver.findElement(By.id(ID_FIELD_MESSAGE)).getText();
            Assert.assertEquals(MSG_SUCCESS, message);

            //Remove tarefa
            driver.findElement((By.xpath(PATH_BUTTON_DELETE))).click();
            message = driver.findElement(By.id(ID_FIELD_MESSAGE)).getText();
            Assert.assertEquals(MSG_SUCCESS, message);
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }*/


}
