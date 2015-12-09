package model;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
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

        HttpClient clientR = HttpClientBuilder.create().build();
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
        List<String> list =  new ArrayList<>();
        OAuthConsumer consumer = new CommonsHttpOAuthConsumer(
                ConsumerKey,
                ConsumerSecret);

        consumer.setTokenWithSecret(AccessToken, AccessSecret);
        HttpGet request = new HttpGet("https://api.twitter.com/1.1/statuses/user_timeline.json?screen_name="+screenName+"&count=5");
        consumer.sign(request);

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        String strReponse = (IOUtils.toString(response.getEntity().getContent()));
        JSONArray jsonArray = new JSONArray();
        try{
            JSONArray jsonArrayTest = new JSONArray(strReponse);
            jsonArray = jsonArrayTest;
        }
        catch (Exception e){
            list.add("Cet utilisateur n'existe pas !");}
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

        HttpClient client = HttpClientBuilder.create().build();
        HttpResponse response = client.execute(request);

        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode + ":" + response.getStatusLine().getReasonPhrase());
        String strReponseHashtag = (IOUtils.toString(response.getEntity().getContent()));


        List<String> list =  new ArrayList<>();
        try {
            JSONObject statuts = (new JSONObject(strReponseHashtag));
            JSONArray statutsArray = statuts.getJSONArray("statuses");
            JSONObject tweet = new JSONObject();
            for(int i=1;i<statuts.length();i++) {
                try{
                    tweet = statutsArray.getJSONObject(0);
                }
                catch(Exception e){
                    tweet.put("text", "Aucun tweet trouvé !");
                }

                Object contenutweet = tweet.get("text");
                String tweetActuel = new String();
                tweetActuel = contenutweet.toString();
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
