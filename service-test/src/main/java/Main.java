package com.company;

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

    System.out.println("test");
  }
}
