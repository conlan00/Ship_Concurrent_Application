package com.example.demo1;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.util.concurrent.Semaphore;

public class Statek extends Thread{

    AnchorPane woda;
    ImageView obraz_statek;
    Miejsca m;
    Semaphore wstrzymanie_statku;
    Rectangle statek_czarny;

    Rectangle port;
    int index=0;
    static boolean czy_koniec=true;
    boolean czy_wyszedl=true;
    Image img=new Image(getClass().getResourceAsStream("p_dol.jpg"));
    Image stat_img=new Image(getClass().getResourceAsStream("stateczek.png"));
    public static  boolean start=true;
    AnchorPane statek_panel;
    Semaphore wyjscie_ze_statku;
    Semaphore czy_jest_statek;
    public Statek(AnchorPane woda, ImageView obraz_statek,  Miejsca m, Semaphore wstrzmanie_statku,Rectangle statek_czarny,AnchorPane statek_panel,Semaphore wyjscie_ze_statku,Semaphore czy_jest_statek) {
        this.woda = woda;
        this.obraz_statek=obraz_statek;
        this.m=m;
        this.statek_panel=statek_panel;
        this.wstrzymanie_statku=wstrzmanie_statku;
        this.statek_czarny=statek_czarny;
        this.wyjscie_ze_statku=wyjscie_ze_statku;
        this.czy_jest_statek=czy_jest_statek;
    }

    public void statek() throws InterruptedException {
        while(true) {
            if(czy_koniec) {
                System.out.println("kontynuacja");
                czy_koniec = false;

                statek_czarny.setFill(new ImagePattern(stat_img));
                //==========Semafor===========//
                if(start) {
                    start=false;
                    wstrzymanie_statku.acquire();
                }
                //-==========================//
                System.out.println("[][][][][][][][][][");
                Rectangle[] pasazerowie_na_pokladzie = new Rectangle[Controller.n];
                miejsca_na_statku(pasazerowie_na_pokladzie);


                System.out.println("x" + statek_panel.getLayoutX() + "y" + statek_panel.getLayoutY());
                //w lewo
                TranslateTransition statek_lewo = new TranslateTransition(Duration.millis(Controller.czas_animacji_statku), statek_panel);
                statek_lewo.setToX(-450);
                statek_lewo.setOnFinished(o -> {
                    statek_panel.setRotate(90);
                    statek_panel.setLayoutX(380);
                });
                //do gory
                TranslateTransition statek_gora = new TranslateTransition(Duration.millis(Controller.czas_animacji_statku), statek_panel);
                statek_gora.setToY(-170);
                statek_gora.setOnFinished(w -> {
                    statek_panel.setRotate(180);
                    statek_panel.setLayoutX(450);
                    statek_panel.setLayoutY(230);
                });
                // w prawo
                TranslateTransition statek_prawo = new TranslateTransition(Duration.millis(Controller.czas_animacji_statku), statek_panel);
                statek_prawo.setToX(0);
                //sekwencja lewo->gora->prawo

                SequentialTransition sekwencja1=new SequentialTransition(statek_lewo, statek_gora, statek_prawo);
                sekwencja1.setOnFinished(p->{
                    for (int i = 0; i < Controller.n; i++) {
                        m.set_pozycja_doplyniecieX(pasazerowie_na_pokladzie[i%8].getX(), i%8);
                       m.set_pozycja_doplyniecieY(pasazerowie_na_pokladzie[i%3].getY(), i%5);
                       m.pasazerowie[i]=pasazerowie_na_pokladzie[i];
                        //System.out.println("@@@@y po doplynieciu:"+m.Miejsce_na_statku_xReverse[i]);
                    }
                    Port.o=true;
                    //============Semafor============//
                    wyjscie_ze_statku.release();
                    //===============================//
                });
                Platform.runLater(()->{
                    sekwencja1.play();
                });
                    //============Semafor============//
                    wstrzymanie_statku.acquire();
                    //===============================//

                System.out.println("wznowiony");

                statek_panel.setRotate(270);//obrot musi byc po wyladowaniu
                statek_panel.setLayoutX(500);
                statek_panel.setLayoutY(300);
                TranslateTransition statek_dol = new TranslateTransition(Duration.millis(Controller.czas_animacji_statku), statek_panel);
                statek_dol.setToY(50);
                statek_dol.setOnFinished(u -> {
                    statek_panel.setLayoutX(443);
                    statek_panel.setLayoutY(255);
                    //  statek_panel.setRotate(360);

                });
                System.out.println("wznowiony drygi raz");
                RotateTransition obrot=new RotateTransition();
                obrot.setNode(statek_panel);
                obrot.setByAngle(90);
                SequentialTransition sekwencja2 = new SequentialTransition(statek_dol,obrot);

                //----------------------------------------------
                sekwencja2.setOnFinished(w -> {
                    czy_koniec = true;
                    //=======Semafor======//
                  //  Konsument.flaga_na=false;
                    czy_jest_statek.release(Controller.n);
                    //===================//
                });
                    Platform.runLater(() -> {
                        sekwencja2.play();

                    });
                         //=======Semafor======//
                    wstrzymanie_statku.acquire();
                        //===================//
            }
        }
    }


public void miejsca_na_statku(Rectangle[] pasazerowie_na_pokladzie)
{
    for (int i = 0; i < Controller.n; i++) {
        Rectangle ludek = new Rectangle(10, 16);
        ludek.setRotate(90);
        ludek.setLayoutX(m.Miejsce_na_statku_xA[i % 8]);//72
        ludek.setLayoutY(m.Miejsce_na_statku_yA[i % 5]);//17
        ludek.setFill(new ImagePattern(img));
        ludek.setVisible(true);
        pasazerowie_na_pokladzie[i] = ludek;
        int finalI = i;
        Platform.runLater(() -> {

            statek_panel.getChildren().add(pasazerowie_na_pokladzie[finalI]);
        });
    }
}

    @Override
    public void run() {
        try {
            statek();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
