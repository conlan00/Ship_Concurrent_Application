package com.example.demo1;

import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;


public class Pasazer extends Thread{
    int id;
    Semaphore wolne;
    Semaphore zajete;
    Semaphore chron_j;
    Bufor bufor;
    AnchorPane lad1;
    AnchorPane lad2;
    Semaphore na_ekran;

    public  static boolean na_mostku1=false;
    AnchorPane woda;
    int rozmiar_buf;
    Semaphore na_mostku;
    Semaphore czy_jest_statek;
    ReentrantLock lock = new ReentrantLock();
    Image img=new Image(getClass().getResourceAsStream("p_o.jpg"));
    public Pasazer(String name , int id, Semaphore wolne, Semaphore zajete, Semaphore chron_j, Semaphore na_ekran, Bufor bufor, AnchorPane lad1, AnchorPane lad2, AnchorPane woda, int rozmiar_buf, Semaphore na_mostku, Semaphore czy_jest_statek) {
        super(name);
        this.id = id;
        this.wolne = wolne;
        this.zajete = zajete;
        this.chron_j = chron_j;
        this.bufor = bufor;
        this.na_ekran=na_ekran;
        this.lad1=lad1;
        this.lad2=lad2;
        this.woda=woda;
        this.rozmiar_buf=rozmiar_buf;
        this.na_mostku=na_mostku;
        this.czy_jest_statek=czy_jest_statek;
    }

    public void pasazer() throws InterruptedException {

            //=======Semafor=======//
            na_ekran.acquire();
        czy_jest_statek.acquire();
            //====================//
        Random rand = new Random();
        int result = rand.nextInt(9);
        Rectangle kwadrat = new Rectangle(5.0, 570.0, 10.0, 16.0);
        kwadrat.setVisible(true);
        kwadrat.setFill(new ImagePattern(img));
        Tablica_danych elem = new Tablica_danych(this.id, kwadrat);
        RotateTransition obrot = new RotateTransition();
        TranslateTransition przesuniecie = new TranslateTransition();
        System.out.println("j :" + bufor.j);
        System.out.println(Thread.currentThread().getName() + this.id);
        obrot.setNode(kwadrat);
        obrot.setByAngle(90);
        przesuniecie.setNode(kwadrat);
        przesuniecie.setDuration(Duration.millis(Controller.czas_animacji_lad));
            przesuniecie.setToX(690);
        RotateTransition obrot1 = new RotateTransition();
        obrot1.setNode(kwadrat);
        obrot1.setByAngle(-90);
        TranslateTransition do_gory=new TranslateTransition(Duration.millis(Controller.czas_animacji_klada),kwadrat);
        do_gory.setToY(-250);
        SequentialTransition sekwencja = new SequentialTransition(obrot, przesuniecie, obrot1,do_gory);
        sekwencja.setOnFinished(f -> {

            //=======Semafor=======//
                 na_mostku.release();
              //  na_mostku1=true;
            //====================//

        });
        Platform.runLater(() -> {
            woda.getChildren().add(kwadrat);
            sekwencja.play();
        });
            wolne.acquire();
            chron_j.acquire();
        bufor.Dane[Bufor.j] = elem;
        Bufor.j = (Bufor.j + 1) % rozmiar_buf;

          //  czy_jest_statek.acquire();

        Thread.sleep(Controller.czas_p);

        zajete.release();
        chron_j.release();
        na_ekran.release();
        System.out.println("@@Producent sprawdza ile na statku" + Kapitan.ile_na_statku);
    }




    @Override
    public void run() {
        try {
            pasazer();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}


