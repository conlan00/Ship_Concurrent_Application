package com.example.demo1;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.concurrent.Semaphore;

// tak jakby main ---- mini main do rozpoczecia dzialania producenta i konsumenta
// prpducent pasazerowie
//Na statku są sloty N slotow gdzie mogą wejsc pasazerowie czyli jest jakby N konsumentow;
//KLADKA BUFOR
public class Test {

    AnchorPane lad1;
    AnchorPane lad2;
    Rectangle panel_mostek;
    AnchorPane woda;
    ImageView obraz_statek;
    Rectangle statek_czarny;
    AnchorPane statek_panel;
    AnchorPane panel_port;
    Rectangle port;


    public Test(AnchorPane lad1, AnchorPane lad2, Rectangle panel_mostek, AnchorPane woda,ImageView obraz_statek,Rectangle statek_czarny,AnchorPane statek_panel,AnchorPane panel_port,Rectangle port) {
        this.lad1 = lad1;
        this.lad2 = lad2;
        this.panel_mostek = panel_mostek;
        this.woda = woda;
        this.obraz_statek=obraz_statek;
        this.statek_czarny=statek_czarny;
        this.statek_panel=statek_panel;
        this.panel_port=panel_port;
        this.port=port;
    }

    int liczba_producentow=Controller.l_k;
    int rozmiar_buf=Controller.n;
    int liczba_konsumentow=Controller.n;
    Semaphore wolne= new Semaphore(Controller.k-1);
    Semaphore zajete= new Semaphore(0);//0
    Semaphore chron_j=new Semaphore(1);
    Semaphore chron_k=new Semaphore(1);
    Semaphore na_ekran=new Semaphore(1);
    Semaphore na_mostku=new Semaphore(0);
    Semaphore wstrzymanie_statku=new Semaphore(0);
    Semaphore wyjscie_ze_statku=new Semaphore(0);
    Semaphore czy_jest_statek=new Semaphore(Controller.n);
    Bufor bufor = new Bufor(Controller.n);
  //  Bufor bufor_statku=new Bufor(rozmiar_buf);

    Miejsca m =new Miejsca();
    Thread[] producenci = new Thread[liczba_producentow];
    Thread[] konsumenci = new Thread[liczba_konsumentow];
    Thread[] pobieranie_port= new Thread[liczba_konsumentow];
    public void Watki() throws InterruptedException {
        for (int i = 0; i < liczba_producentow; i++) {
                producenci[i]=new Pasazer("ludek o id-",i,wolne,zajete,chron_j,na_ekran,bufor,lad1,lad2,woda,rozmiar_buf,na_mostku,czy_jest_statek);
                producenci[i].start();
        }
        for(int i=0;i<liczba_konsumentow;i++)
        {
            konsumenci[i]=new Kapitan("konsument o id-",i,wolne,zajete,chron_k,bufor,woda,na_mostku,rozmiar_buf,m,wstrzymanie_statku,statek_panel);
            konsumenci[i].start();
        }
        Statek s1=new Statek(woda,obraz_statek,m,wstrzymanie_statku,statek_czarny,statek_panel,wyjscie_ze_statku,czy_jest_statek);
        s1.start();
        for(int i=0;i<liczba_konsumentow;i++)
        {
            pobieranie_port[i]=new Port(port,panel_port,wyjscie_ze_statku,i,m,statek_panel,wstrzymanie_statku);
            pobieranie_port[i].start();
        }

    }
}





