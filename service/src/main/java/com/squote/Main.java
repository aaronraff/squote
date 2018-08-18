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

    String serviceAccountConfigPath = props.getProperty("serviceAccountConfigPath");
    String dbUrl = props.getProperty("dbUrl");
    String symbolRefString = props.getProperty("symbolRefString");

    initializeFirebaseApp(serviceAccountConfigPath, dbUrl);
    SymbolUpdater symbolUpdaterJob = new SymbolUpdater(symbolRefString);

    // Run from command line
    if (args.length > 0 && args[0].equals("-c"))
        symbolUpdaterJob.runJob();
    else
        symbolUpdaterJob.runDailyJob();
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
