package com.example.demo1;

import javafx.scene.shape.Rectangle;

public class Miejsca {

    int[] Miejsce_na_statku_x=new int[8];
    int[] Miejsce_na_statku_y=new int[5];
    int[] Miejsce_na_statku_xA=new int[8];
    int[] Miejsce_na_statku_yA=new int[5];
    double[] Miejsce_na_statku_xReverse=new double[8];
    double[] Miejsce_na_statku_yReverse=new double[5];
    Rectangle[] pasazerowie=new Rectangle[Controller.n];
    Rectangle[] do_usuniecia=new Rectangle[Controller.n];
    public Miejsca()
    {
        for(int i=0;i<8;i++){
            Miejsce_na_statku_x[i]=520+(i*16);//panel woda
        }
        for(int i=0;i<5;i++){
            Miejsce_na_statku_y[i]=330+(i*10);//panel woda
        }
        for(int i=0;i<8;i++){
            Miejsce_na_statku_xA[i]=72+(i*16);//panel statek
        }
        for(int i=0;i<5;i++){
            Miejsce_na_statku_yA[i]=17+(i*10);//panel statek
        }

    }
    public void set_pozycja_doplyniecieX(double x,int i)
    {
        Miejsce_na_statku_xReverse[i]=x;
    }
    public void set_pozycja_doplyniecieY(double x,int i)
    {
            Miejsce_na_statku_yReverse[i]=x;
    }


}
