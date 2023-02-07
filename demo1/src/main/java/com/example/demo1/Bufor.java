package com.example.demo1;

public class Bufor {
    static int  j=0;
    static int k=0;
    int rozmiar_buforu;
    Tablica_danych[] Dane;

    public Bufor(int rozmiar_buforu)
    {
        this.rozmiar_buforu=rozmiar_buforu;
        Dane=new Tablica_danych[rozmiar_buforu];
        System.out.println("Bufor stworzony"+rozmiar_buforu);
    }

}
