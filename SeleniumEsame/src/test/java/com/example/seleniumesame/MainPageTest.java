package com.example.seleniumesame;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.time.Duration;


public class MainPageTest {
    WebDriver webDriver;

    @Before
    public void setUp(){
        //uso il driver firefox, driver firefox fornito insieme ai 2 test
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\samue\\Downloads\\APP\\geckodriver-v0.33.0-win64\\geckodriver.exe");
        webDriver= new FirefoxDriver();
    }

    @After
    public void tearDown() throws InterruptedException {
        Thread.sleep(4000);
        webDriver.quit();
    }

    @Test
    public void search() throws InterruptedException, IOException, UnsupportedFlavorException {
        // Vado al sito per effettuare la traduzione
        webDriver.get("https://www.deepl.com/it/translator");
        // Imposto una dimensione fissa per la visualizzazione della pagina
        webDriver.manage().window().setSize(new Dimension(1055, 740));

        // Tempo di attesa per far si che esca il pulsante per accettare i cookie
        Thread.sleep(4000);

        // Accetto i cookie
        webDriver.findElement(By.xpath("/html/body/div[9]/div/div/div[3]/button[2]")).click();

        Thread.sleep(4000);


        // Mi è successo che il test si bloccasse qui ma poi facendolo ripartire funziona
        // Clicco sul box su cui scriverò la frase da tradurre
        webDriver.findElement(By.xpath("/html/body/div[3]/main/div[6]/div[1]/div[2]/section[1]/div[3]/div[2]/d-textarea/div/p")).click();

        // inserisco una WebDriverWait per verificare che posso iniziare a scrivere
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
        WebElement textarea = webDriver.findElement(By.xpath("/html/body/div[3]/main/div[6]/div[1]/div[2]/section[1]/div[3]/div[2]/d-textarea/div"));
        wait.until(ExpectedConditions.elementToBeClickable(textarea));
        // scrivo la frase da tradurre
        textarea.sendKeys("la pizza margherita è senza ananas");


        Thread.sleep(3000);
        webDriver.findElement(By.xpath("/html/body/div[3]/main/div[6]/div[1]/div[2]/section[2]/div[3]/div[6]/div/div[5]/span[2]/span/span/button")).click();

        // Per salvare il contenuto della stringa che mi viene restituita utilizo il button "copy" per salvarla negli
        // appunti di sistema. Poi tramite getSystemClipboard() un metodo che ho trovato:
        // (https://stackoverflow.com/questions/26879284/read-system-clipboard-data-in-java) salvo il valore letto.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();

        // Leggi il contenuto degli appunti
        String clipboardContents = (String) clipboard.getData(DataFlavor.stringFlavor);


        // Verifico se la frase tradotta è corretta
        Assert.assertEquals("margherita pizza is without pineapple", clipboardContents);
    }
}
