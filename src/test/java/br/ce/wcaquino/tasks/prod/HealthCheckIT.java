package br.ce.wcaquino.tasks.prod;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HealthCheckIT {

    //Caminho onde fica o executável do chromedrive
    //private static final String PATH_CHROME_DRIVER = "/Users/marcos.camara/Devtools/devops_pipeline/chromedriver";
    private static final String URL = "http://192.168.15.28:9999/tasks";

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
    public void healthtCheck() throws MalformedURLException {
        WebDriver driver = createWebDriver();

        try{
            String version = driver.findElement(By.id("version")).getText();
            Assert.assertTrue(version.startsWith("build"));
        }finally {
            //Fecha o browser
            driver.quit();
        }
    }
}
