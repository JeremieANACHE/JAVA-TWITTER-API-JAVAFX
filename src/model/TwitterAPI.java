package model;

import javafx.scene.text.Text;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LazerCat on 07/12/15.
 */





        /*




            ________   ___   ____
           / __   __| / _ \ |  _ \
     ______> \ | |   |  _  ||    /_____________________________
    / _______/ |_|   |_| |_||_|\______________________________ \
   / /                                                        \ \
  | |                                                          | |
  | |                                                          | |
  | |                                                          | |
  | |                                                          | |
  | |               Moteur permettant l'envoi                  | |
  | |               et la réception de tweets                  | |
  | |                                                          | |
  | |                                                          | |
  | |                                                          | |
   \ \____________________________    _   ___   ____   _______/ /
    \___________________________  |  | | / _ \ |  _ \ / _______/
                                | |/\| ||  _  ||    / > \
                                 \_/\_/ |_| |_||_|\_\|__/

                  _             _
                 //             \\
                /'               `\
               /,'     ..-..     `.\
              /,'   .''     ``.   `.\
             /,'   :   .---.   :   `.\
            I I   :  .'\   /`.  :   I I
            I b__:   . .`~'. .   :__d I
            I p~~:   . `._.' .   :~~q I
            I I   :   ./   \.   :   I I
             \`.   :   `---'   :   ,'/
              \`.   `..     ..'   ,'/
               \`.     ``~''     ,'/
                \`               '/
                 \\             //
                  ~             ~

                ._,.
           "..-..pf.
          -L   ..#'
        .+_L  ."]#
        ,'j' .+.j`                 -'.__..,.,p.
       _~ #..<..0.                 .J-.``..._f.
      .7..#_.. _f.                .....-..,`4'
      ;` ,#j.  T'      ..         ..J....,'.j`
     .` .."^.,-0.,,,,yMMMMM,.    ,-.J...+`.j@
    .'.`...' .yMMMMM0M@^=`""g.. .'..J..".'.jH
    j' .'1`  q'^)@@#"^".`"='BNg_...,]_)'...0-
   .T ...I. j"    .'..+,_.'3#MMM0MggCBf....F.
   j/.+'.{..+       `^~'-^~~""""'"""?'"``'1`
   .... .y.}                  `.._-:`_...jf
   g-.  .Lg'                 ..,..'-....,'.
  .'.   .Y^                  .....',].._f
  ......-f.                 .-,,.,.-:--&`
                            .`...'..`_J`
                            .~......'#'
                            '..,,.,_]`
                            .L..`..``.




        */



public class TwitterAPI {

    final static String AccessToken = "YourAccessToken";
    final static String AccessSecret = "YourAccessSecret";
    final static String ConsumerKey = "YourConsumerKey";
    final static String ConsumerSecret = "YourConsumerSecret";


    public void tweetMessage(String message)
    {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
                ConsumerKey,
                ConsumerSecret);

        consumer.setTokenWithSecret(AccessToken, AccessSecret);



        HttpPost requestPost = new HttpPost("https://api.twitter.com/1.1/statuses/update.json?status="+message);
        try {
            consumer.sign(requestPost);
        }
        catch (Exception e){
            //erreur lel
        }

        HttpClient clientR = new DefaultHttpClient();
        try{
            HttpResponse responseR = clientR.execute(requestPost);
            int statusCodeR = responseR.getStatusLine().getStatusCode();
            System.out.println(statusCodeR + ":" + responseR.getStatusLine().getReasonPhrase());
        }
        catch (IOException e){
            //erreur
        }

    }

    public List<String> voirTweetsDe(String screenName) throws Exception {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
                ConsumerKey,
                ConsumerSecret);

        consumer.setTokenWithSecret(AccessToken, AccessSecret);
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="+screenName+"&count=5");
        consumer.sign(request);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        String strReponse = (IOUtils.toString(response.getEntity().getContent()));

        JSONArray jsonArray = (new JSONArray(strReponse));
        List<String> list =  new ArrayList<>();
        for(int i=0;i<jsonArray.length();i++) {
            list.add(jsonArray.getJSONObject(i).get("text").toString());
        }
        return list;
    }



    public List<String> voirTweetsSur(String hashtag) throws Exception {
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
                ConsumerKey,
                ConsumerSecret);

        consumer.setTokenWithSecret(AccessToken, AccessSecret);
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/search/tweets.json?q=%23"+hashtag+"&count=5");
        consumer.sign(request);

        HttpClient client = new DefaultHttpClient();
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        String strReponseHashtag = (IOUtils.toString(response.getEntity().getContent()));


        List<String> list =  new ArrayList<>();
        try {
            JSONObject statuts = (new JSONObject(strReponseHashtag));
            JSONArray statutsArray = statuts.getJSONArray("statuses");
            for(int i=1;i<statuts.length();i++) {
                JSONObject tweet = new JSONObject((statutsArray.getJSONObject(0)).toString());
                Object contenutweet = tweet.get("text");
                String tweetActuel = contenutweet.toString();
                list.add(tweetActuel);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println(strReponseHashtag);
        }
        return list;
    }



}
