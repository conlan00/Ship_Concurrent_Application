package com.example.demo1;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.concurrent.Semaphore;

public class Kapitan extends Thread{
    int id;
    Semaphore wolne;
    Semaphore zajete;
    Semaphore chron_k;
    Bufor bufor;
    AnchorPane woda;
    Semaphore na_mostku;
    int rozmiar_buf;
    Miejsca m;
    Semaphore wstrzymanie_statku;
    AnchorPane statek_panel;
    static int ile_na_statku=0;
    static boolean flaga_na=false;
    public Kapitan(String name, int id, Semaphore wolne, Semaphore zajete, Semaphore chron_k, Bufor bufor, AnchorPane woda,
                   Semaphore na_mostku, int rozmiar_buf, Miejsca m, Semaphore wstrzymanie_statku, AnchorPane statek_panel) {
        super(name);
        this.id = id;
        this.wolne = wolne;
        this.zajete = zajete;
        this.chron_k = chron_k;
        this.bufor = bufor;
        this.woda=woda;
        this.na_mostku=na_mostku;
        this.rozmiar_buf=rozmiar_buf;
        this.m=m;
        this.wstrzymanie_statku=wstrzymanie_statku;
        this.statek_panel=statek_panel;
    }


    public void kapitan() throws InterruptedException {
        while(true) {
            //=======Semafor=======//

            na_mostku.acquire();
            //====================//
            //----------s-----------/
            zajete.acquire();
            chron_k.acquire();
            //----------------------/
            //pobranie danych z bufora

            Tablica_danych elem = bufor.Dane[Bufor.k];
            m.do_usuniecia[id] = elem.ludek;
            RotateTransition obrot = new RotateTransition();
            obrot.setNode(m.do_usuniecia[id]);
            obrot.setByAngle(-90);
            System.out.println("k:" + Bufor.k);
            Path path = new Path();
            MoveTo moveTo = new MoveTo(700, 330);
            LineTo lineTo = new LineTo(m.Miejsce_na_statku_x[this.id % 8], m.Miejsce_na_statku_y[this.id % 5]);
            path.getElements().add(moveTo);
            path.getElements().add(lineTo);
            PathTransition pathTransition = new PathTransition();
            pathTransition.setNode(m.do_usuniecia[id]);
            pathTransition.setDuration(Duration.millis(Controller.czas_animacji_na_statek));
            pathTransition.setPath(path);
            SequentialTransition sekwencja = new SequentialTransition(obrot, pathTransition);
            Platform.runLater(sekwencja::play);
            sekwencja.setOnFinished(z -> {

                ile_na_statku++;
                System.out.println("##KONSUMENT ILE NA STATKU:" + ile_na_statku);
                if (ile_na_statku == Controller.n) {
                    flaga_na = true;
                    ile_na_statku = 0;
                    for (int i = 0; i < Controller.n; i++) {
                        woda.getChildren().removeAll(m.do_usuniecia[i]);
                    }
                    //==========Semafor===========//
                    wstrzymanie_statku.release();
                    //-==========================//
                    //Statek.czy_koniec=true
                }

            });

            //----------s-----------/
            wolne.release();
            //----------------------/
            Bufor.k = (Bufor.k + 1) % rozmiar_buf;

            //----------s-----------/
            chron_k.release();

        }

    }



    @Override
    public void run() {
        try {
            kapitan();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
