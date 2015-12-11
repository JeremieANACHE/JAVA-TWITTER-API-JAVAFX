package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;

public class DemarreGraphisme extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/vue.fxml"));

        primaryStage.setTitle("Twitter pour grille-pain");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
        primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image("ressources/icon.png"));
        primaryStage.getIcons().add(new Image("https://pbs.twimg.com/app_img/671661640086118401/BDOIO_JO?format=png&name=73x73")); // Icone en haut de l'app
    }


    public static void main(String[] args) {

        // Ajoute l'icone au démarrage sur mac
        try {
            URL iconURL = DemarreGraphisme.class.getResource("../ressources/icon.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
        } catch (Exception e) {
            // Won't work on Windows or Linux.
        }

        launch(args);
    }
}
