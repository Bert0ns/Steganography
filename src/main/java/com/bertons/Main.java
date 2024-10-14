package com.bertons;

import java.io.File;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
         Stenography st = new Stenography("src/main/resources/TestImg2.png");
         st.Encode(new File(""));
    }
}