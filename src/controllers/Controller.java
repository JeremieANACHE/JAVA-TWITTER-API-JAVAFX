package controllers;


/**
 * Created by LazerCat on 01/12/15.
 */


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.TwitterAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @FXML
    public TextFlow contenuTweetsUser;

    @FXML
    public TextFlow contenuTweetsHashtag;

    @FXML
    public TextFlow headerTweetsHashtag;

    @FXML
    public Rectangle cadreTweetUser;

    @FXML
    public Rectangle cadreTweetsHashtag;

    @FXML
    private TextField textTweet;

    @FXML
    private TextField textScreenName;

    @FXML
    private TextField textHashtag;

    @FXML
    private ProgressIndicator progressIncoming;

    @FXML
    private TextFlow headerTweets;

    @FXML
    public void tweet() {

        TwitterAPI twitterAPI = new TwitterAPI();
        LOGGER.debug("fonction d'envoi de tweet");
        if(!textTweet.getText().equals(""))
        {
            LOGGER.debug("Le tweet n'est pas vide");
            try
            {
                String message = URLEncoder.encode(textTweet.getText(), "UTF-8");
                twitterAPI.tweetMessage(message);
                LOGGER.info("Tweet envoyé");
            }
            catch(Exception e)
            {
                LOGGER.error(e.getMessage());
            }
            textTweet.setText("");
            LOGGER.debug("réinitialisation du tweet");
        }
        else{
            LOGGER.warn("Le message à envoyer ne doit pas être vide");
        }
    }



    @FXML
    public void getTweetUser() {
        TwitterAPI twitterAPI = new TwitterAPI();
        LOGGER.debug("Fonction de récupération de tweets");
        if(!textScreenName.getText().equals(""))
        {
            LOGGER.debug("Le nom d'utilisateur n'est pas vide");

            Text nomDuTwitteur = new Text("");
            headerTweets.getChildren().clear();
            List<String> list =  new ArrayList<>();
            list.clear();
            contenuTweetsUser.getChildren().clear();

            nomDuTwitteur.setText("5 derniers tweets par @" + textScreenName.getText());
            nomDuTwitteur.setFont(Font.font("MullerBlack", 55));
            nomDuTwitteur.setFill(Color.BLACK);
            headerTweets.getChildren().add(nomDuTwitteur);

            try {
                LOGGER.debug("Requête de récupération de tweets");
                list = twitterAPI.voirTweetsDe(textScreenName.getText());
                contenuTweetsUser.setLineSpacing(5);
                contenuTweetsUser.setMaxWidth(1170);
                for (int i=0;i<list.size();i++)
                {
                    Text tweetActuel = new Text(list.get(i) + "\n \n");
                    tweetActuel.setFill(Color.WHITE);
                    if(list.size() < 2) {
                        tweetActuel.setFont(Font.font("MullerBlack", 55));
                    }
                    else {
                        tweetActuel.setFont(Font.font("MullerBlack", 23));

                    }
                    contenuTweetsUser.getChildren().add(tweetActuel);
                }
                cadreTweetUser.setVisible(true);
                LOGGER.info("Messages récupérés");
            }
            catch(Exception e)
            {
                LOGGER.error(e.getMessage());
            }
        }
        else{
            LOGGER.warn("le nom d'utilisateur doit être renseigné");
        }

    }


    @FXML
    public void getTweetHashtag() {
        TwitterAPI twitterAPI = new TwitterAPI();
        LOGGER.debug("Fonction de Récupération de Tweets");
        if(!textHashtag.getText().equals(""))
        {
            LOGGER.debug("Le hashtag n'est pas vide");
            Text hashtagRecherche = new Text("");
            headerTweetsHashtag.getChildren().clear();

            List<String> list =  new ArrayList<>();
            list.clear();
            contenuTweetsHashtag.getChildren().clear();

            hashtagRecherche.setText("Le dernier tweet sur #" + textHashtag.getText());
            hashtagRecherche.setFont(Font.font("MullerBlack", 45));
            hashtagRecherche.setFill(Color.BLACK);
            headerTweetsHashtag.getChildren().add(hashtagRecherche);

            try {
                LOGGER.debug("Requête de récupération du dernier Tweet");
                list = twitterAPI.voirTweetsSur(textHashtag.getText());
                contenuTweetsHashtag.setLineSpacing(5);
                contenuTweetsHashtag.setMaxWidth(1170);
                for(int i=0;i<list.size();i++)
                {
                    Text tweetActuel = new Text(list.get(i) + "\n \n");
                    tweetActuel.setFont(Font.font("MullerBlack", 45));
                    tweetActuel.setFill(Color.WHITE);
                    contenuTweetsHashtag.getChildren().add(tweetActuel);
                }
                cadreTweetsHashtag.setVisible(true);
            }
            catch(Exception e)
            {

            }
        }
        else {
            LOGGER.warn("Le hashtag doit être renseigné");
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}