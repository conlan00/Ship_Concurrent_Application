package com.example.demo1;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;

public class Controller {
    @FXML
    TextField N;
    @FXML
    TextField K;
    @FXML
    Button Przycisk;// save
    @FXML
    Button Przycisk1;// run
    @FXML
    AnchorPane woda;
    @FXML
    AnchorPane lad1;//dol
    @FXML
    AnchorPane lad2;//gora
    @FXML
    Rectangle mostek;
    @FXML
    ImageView obraz_statek;

    @FXML
    Rectangle statek_czarny;
    @FXML
    AnchorPane statek_panel;
    @FXML
    AnchorPane panel_port;
    @FXML
    Rectangle port;
   public static int n=0,k=0,l_k=0,czas_p=0,czas_animacji_port=0,czas_animacji_na_statek=0,czas_animacji_lad=0,czas_animacji_klada=0,czas_animacji_statku=0;
    @FXML
    Label info;
    @FXML
    Label lab_S;
    @FXML
    ImageView ikona;
    @FXML
    TextField konsumenci_ile;
    @FXML
    TextField time;
    @FXML
    TextField time1_pobranie;
    @FXML
    TextField time_on_statek;
    @FXML
    TextField czas_an_lad;
    @FXML
    TextField time_on_kladka;
    @FXML
    TextField statek_an;
    @FXML
    Label autor;

    @FXML
    void initialize() throws FileNotFoundException {
        File plik = new File("src/main/resources/com/example/demo1/dane.txt");
        Scanner odczyt = new Scanner(plik);
        String dane = odczyt.nextLine();
        K.setText(dane);
        dane = odczyt.nextLine();
        N.setText(dane);
        dane = odczyt.nextLine();
        konsumenci_ile.setText(dane);
        dane = odczyt.nextLine();
        time.setText(dane);
        dane = odczyt.nextLine();
        time1_pobranie.setText(dane);
        dane = odczyt.nextLine();
        time_on_statek.setText(dane);
        dane = odczyt.nextLine();
        czas_an_lad.setText(dane);
        dane = odczyt.nextLine();
        time_on_kladka.setText(dane);
        dane = odczyt.nextLine();
        statek_an.setText(dane);
        odczyt.close();

    }
    @FXML public void klik()// zapisanie danych
    {

        n=Integer.parseInt(N.getText());
        k=Integer.parseInt(K.getText());
        l_k=Integer.parseInt(konsumenci_ile.getText());
        czas_p=Integer.parseInt(time.getText());
        czas_animacji_port=Integer.parseInt(time1_pobranie.getText());
        czas_animacji_na_statek=Integer.parseInt(time_on_statek.getText());
        czas_animacji_lad=Integer.parseInt(czas_an_lad.getText());
        czas_animacji_klada=Integer.parseInt(time_on_kladka.getText());
        czas_animacji_statku=Integer.parseInt(statek_an.getText());
        if(k>n)
        {
            System.exit(0);
        }
        System.out.println("to jest n"+n+"to jest k"+k);
        Main.minimain =new Test(lad1,lad2,mostek,woda,obraz_statek,statek_czarny,statek_panel,panel_port,port);
        Przycisk.setDisable(true);
    }
    @FXML
    public void rozpoczecie() throws InterruptedException {
        info.setVisible(false);
        ikona.setVisible(false);
        lab_S.setVisible(false);
        autor.setVisible(false);
        woda.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.EMPTY)));
        statek_czarny.setVisible(true);
        port.setVisible(true);
        woda.setStyle("-fx-background-color: #0fffff");
        mostek.setVisible(true);

        //obraz_statek.toBack();
        lad1.setStyle("-fx-background-color: #7fff00");
        lad2.setStyle("-fx-background-color: #7fff00");

        Main.minimain.Watki();
        Przycisk1.setDisable(true);
    }

}