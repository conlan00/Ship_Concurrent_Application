package com.example.demo1;

import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.concurrent.Semaphore;

public class Port extends Thread{
    Rectangle port;
    AnchorPane panel_port;
    Semaphore wyjscie_ze_statku;
    int id;
    Miejsca m;
    AnchorPane statek_panel;
    Semaphore wstrzymanie_statku;
    static int ile=Controller.n;
    public static boolean o=false;
    public Port(Rectangle port, AnchorPane panel_port, Semaphore wyjscie_ze_statku,int id,Miejsca m,AnchorPane statek_panel,Semaphore wstrzymanie_statku) {
        this.port = port;
        this.panel_port = panel_port;
        this.wyjscie_ze_statku = wyjscie_ze_statku;
        this.id=id;
        this.m=m;
        this.statek_panel=statek_panel;
        this.wstrzymanie_statku=wstrzymanie_statku;
    }
    public void pobranie() throws InterruptedException {
        while (true) {
            System.out.println("port watek" + this.id);
            wyjscie_ze_statku.acquire();
            wyladuj(m.Miejsce_na_statku_xReverse, m.Miejsce_na_statku_yReverse, id);
            if(o) {
                ile--;
            }
            if(ile==0)
            {
                o=false;
                ile=Controller.n;
                wyjscie_ze_statku.acquire();
                wstrzymanie_statku.release();

            }
        }
    }
    public void wyladuj(double[] pasazerx,double[] pasazery, int i)   {


        Path wysiadaj = new Path();
        MoveTo moveTo = new MoveTo(pasazerx[i%8],pasazery[i%5]);
        LineTo lineTo = new LineTo(-210, 70);
        wysiadaj.getElements().add(moveTo);
        wysiadaj.getElements().add(lineTo);
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(m.pasazerowie[i]);
        pathTransition.setDuration(Duration.millis(Controller.czas_animacji_port));
        pathTransition.setPath(wysiadaj);
        pathTransition.setOnFinished(u -> {

            statek_panel.getChildren().remove(m.pasazerowie[i]);


                System.out.println("%%Port sprawdza ile na statku"+ile);
        //=======Semafor=======//
            wyjscie_ze_statku.release();
        //====================//
        });
        Platform.runLater(() -> {
            pathTransition.play();
        });

    }


    @Override
    public void run() {
        try {
            pobranie();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}


