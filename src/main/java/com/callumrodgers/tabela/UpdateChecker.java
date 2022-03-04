package com.callumrodgers.tabela;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class UpdateChecker implements Runnable{

    private String reposLink = "";
    private String request = "https://api.github.com/repos/CallumRodgers/PeriodicTable/releases";
    private String charset = StandardCharsets.UTF_8.name();

    @Override
    public void run() {
        String response = executeGet(request, "");
        System.out.println(response);
    }

    public static String executeGet(String targetURL, String urlParameters) {
        URL myURL;
        try {
            myURL = new URL(targetURL);
            HttpURLConnection myURLConnection = (HttpURLConnection) myURL.openConnection();

            myURLConnection.setRequestMethod("GET");
            myURLConnection.setRequestProperty("accept", "application/vnd.github.v3+json");
            myURLConnection.setRequestProperty("owner", "CallumRodgers");
            myURLConnection.setRequestProperty("repo", "PeriodicTable");
            myURLConnection.setRequestProperty("Content-Language", "en-US");
            myURLConnection.setUseCaches(false);
            myURLConnection.setDoInput(true);
            myURLConnection.setDoOutput(true);

            return myURLConnection.getResponseMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
