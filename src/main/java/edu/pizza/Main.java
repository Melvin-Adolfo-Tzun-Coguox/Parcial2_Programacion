package edu.pizza;
import edu.formularios.frmPizza;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        JFrame frame = new JFrame("frmPizza");
        frame.setContentPane(new frmPizza().getjPanelPrincipal());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("------ Mamma Mia Pizzeria------");
        frame.setVisible(true);
        frame.setSize(500,800);
        frame.setLocationRelativeTo(null);
        frame.pack();
    }
}
