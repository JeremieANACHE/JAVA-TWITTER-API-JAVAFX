package controllers;


/**
 * Created by LazerCat on 01/12/15.
 */


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
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
    private TextField textTweet;

    @FXML
    private TextField textScreenName;

    @FXML
    private TextField textHashtag;

    @FXML
    private ProgressIndicator progressIncoming;

    @FXML
    public void tweet() {

        TwitterAPI twitterAPI = new TwitterAPI();
        if(!textTweet.getText().equals(""))
        {
            try
            {
                progressIncoming.setVisible(true);
                progressIncoming.setProgress(-1);
                String message = URLEncoder.encode(textTweet.getText(), "UTF-8");
                twitterAPI.tweetMessage(message);
            }
            catch(Exception e)
            {
                System.out.print(e.getMessage());
            }

            progressIncoming.setVisible(false);
            textTweet.setText("");
        }
    }



    @FXML
    public void getTweetUser() {
        TwitterAPI twitterAPI = new TwitterAPI();
        if(!textScreenName.getText().equals(""))
        {
            List<String> list =  new ArrayList<>();
            list.clear();
            contenuTweetsUser.getChildren().clear();

            try {

                list = twitterAPI.voirTweetsDe(textScreenName.getText());
                contenuTweetsUser.setLineSpacing(5);
                contenuTweetsUser.setMaxWidth(1170);
                for(int i=0;i<list.size();i++)
                {
                    Text tweetActuel = new Text(list.get(i) + "\n \n");

                    contenuTweetsUser.getChildren().add(tweetActuel);
                }
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
            List<String> list =  new ArrayList<>();
            list.clear();
            contenuTweetsHashtag.getChildren().clear();

            try {
                list = twitterAPI.voirTweetsSur(textHashtag.getText());
                contenuTweetsHashtag.setLineSpacing(5);
                contenuTweetsHashtag.setMaxWidth(1170);
                for(int i=0;i<list.size();i++)
                {
                    Text tweetActuel = new Text(list.get(i) + "\n \n");
                    contenuTweetsHashtag.getChildren().add(tweetActuel);
                }
            }
            catch(Exception e)
            {

            }
        }

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        progressIncoming.setVisible(false);
    }
}