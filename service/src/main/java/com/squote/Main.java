package com.squote;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import java.io.FileInputStream;
import java.util.Properties;

public class Main {

  public static void main(String[] args) {
    Properties props = new Properties();

    try (FileInputStream in = new FileInputStream("src/main/firebaseAdmin/app.config")) {
      props.load(in);
    } catch (Exception ex) {
      System.out.println(ex);
    }

    String serviceAccountConfigPath = props.getProperty("serviceAccoutConfigPath");
    String dbUrl = props.getProperty("dbUrl");

    initializeFirebaseApp(serviceAccountConfigPath, dbUrl);
  }

  private static void initializeFirebaseApp(String serviceAccountConfigPath, String dbUrl) {
    try (FileInputStream serviceAccount = new FileInputStream(serviceAccountConfigPath)) {
      FirebaseOptions options = new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(serviceAccount))
              .setDatabaseUrl(dbUrl)
              .build();

      FirebaseApp.initializeApp(options);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}
