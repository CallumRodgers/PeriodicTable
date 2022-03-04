package com.callumrodgers.tabela;

import com.callumrodgers.tabela.ui.HyperlinkButton;
import com.callumrodgers.tabela.ui.UIDefaults;
import org.json.JSONObject;

import javax.swing.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UpdateChecker implements Runnable {

    private static String[] releaseNames = {"v0.1.0-alpha", "v0.2.0-alpha"};
    private String siteLink = "https://github.com/CallumRodgers/PeriodicTable/releases";
    private String request = "https://api.github.com/repos/CallumRodgers/PeriodicTable/releases";

    private String name, description;
    private int id;
    private String publishTime;

    @Override
    public void run() {
        String response = executeGet(request);
        parseResponse(response);
        if (isNewRelease(name)) {
            SwingUtilities.invokeLater(() -> {
                String message = "Uma nova versão do software foi lançada:\n\n" +
                        "Nome da versão: " + name + "\n" +
                        "Hora de publicação: " + publishTime + "\n" +
                        "Descrição: " + description;
                JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(Main.mainFrame, "Atualização");
                dialog.setIconImage(UIDefaults.icon);
                JButton okButton = new JButton("OK");
                okButton.addActionListener(e -> dialog.dispose());
                HyperlinkButton gitHubButton = new HyperlinkButton(siteLink, "Ir para o GitHub");
                optionPane.setOptions(new Object[]{okButton, gitHubButton});
                dialog.setModal(false);
                dialog.setVisible(true);
            });
        }
    }

    public static String executeGet(String targetURL) {
        try {
            HttpRequest request = HttpRequest.newBuilder().GET()
                            .header("accept", "application/vnd.github.v3+json")
                            .uri(new URI(targetURL)).build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void parseResponse(String response) {
        response = response.replace("[{", "{");
        response = response.replace("}]", "}");
        JSONObject object = new JSONObject(response);
        name = object.getString("name");
        description = object.getString("body");
        publishTime = object.getString("published_at");
        id = Integer.parseInt(object.get("id").toString());
    }

    private boolean isNewRelease(String name) {
        for (String releaseName : releaseNames) {
            if (name.equals(releaseName)) {
                return false;
            }
        }
        return true;
    }
}
