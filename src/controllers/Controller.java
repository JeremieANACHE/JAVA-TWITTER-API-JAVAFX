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

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {
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
        if(!textTweet.getText().equals(""))
        {
            try
            {
                String message = URLEncoder.encode(textTweet.getText(), "UTF-8");
                twitterAPI.tweetMessage(message);
            }
            catch(Exception e)
            {
                System.out.print(e.getMessage());
            }
            textTweet.setText("");
        }
    }



    @FXML
    public void getTweetUser() {
        TwitterAPI twitterAPI = new TwitterAPI();
        if(!textScreenName.getText().equals(""))
        {
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
            }
            catch(Exception e)
            {

            }
        }

    }


    @FXML
    public void getTweetHashtag() {
        TwitterAPI twitterAPI = new TwitterAPI();
        if(!textHashtag.getText().equals(""))
        {
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
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}