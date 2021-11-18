package application;

import gui.Gui;

import javax.swing.*;

public class Main {
    public static boolean isHost, isConnected = false;
    public static Gui g;
    public static void main(String[] args) {
        g = new Gui();
        g.create();
    }
}

