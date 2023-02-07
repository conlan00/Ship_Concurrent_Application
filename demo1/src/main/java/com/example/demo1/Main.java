package com.example.demo1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {

     static Test minimain;
     public static AnchorPane woda;

     static AnchorPane root;
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view.fxml"));
         root = loader.load();
         woda=(AnchorPane) root.getChildren().get(1);
        Scene scene=new Scene(root);
        stage.setTitle("Projekt Statek");
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("istockphoto-845632640-612x612.jpg")));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        woda.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    }

    public static void main(String[] args) {

        launch();
    }
}




