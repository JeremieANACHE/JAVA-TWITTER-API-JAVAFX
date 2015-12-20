package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.net.URL;

public class DemarreGraphisme extends Application {
    private static final Logger LOGGER = LoggerFactory.getLogger(DemarreGraphisme.class);


    @Override
    public void start(Stage primaryStage) throws Exception{

        LOGGER.info("chargement de la vue");
        Parent root = FXMLLoader.load(getClass().getResource("/view/vue.fxml"));

        LOGGER.info("chargement du titre de la fenêtre");
        primaryStage.setTitle("Twitter pour grille-pain");
        LOGGER.info("chargement des paramètres de la fenêtre");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();
        primaryStage.setResizable(false);
        //primaryStage.getIcons().add(new Image("ressources/icon.png"));
        LOGGER.info("chargement de l'image miniature pour le cadre de la fenêtre");
        primaryStage.getIcons().add(new Image("https://pbs.twimg.com/app_img/671661640086118401/BDOIO_JO?format=png&name=73x73")); // Icone en haut de l'app
    }


    public static void main(String[] args) {


        // Ajoute l'icone au démarrage sur mac
        try {
            URL iconURL = DemarreGraphisme.class.getResource("../ressources/icon.png");
            java.awt.Image image = new ImageIcon(iconURL).getImage();
            com.apple.eawt.Application.getApplication().setDockIconImage(image);
            LOGGER.info("chargement de l'icône dans le dock");
        } catch (Exception e) {
            LOGGER.error("ne fonctionne pas sous Windows ou Linux");
        }

        launch(args);
    }
}
