package edu.pizza.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Ejercicio 1
Crear la clase pizza con los siguientes atributos:
 */
public class Pizza {
    private String name;
    //private double price;
    private List<Topping> toppings = new ArrayList<>();

    public Pizza(String name, Topping... toppings) {
        this.name = name;

        for (Topping topping : toppings) {
            this.toppings.add(topping);
        }
    }

    public void addTopping(Topping topping) {
        this.toppings.add(topping);
    }

    public void removeTopping(int index) {
        this.toppings.remove(index);
    }

    public List<Topping> getToppings() {

        return Collections.unmodifiableList(new ArrayList<>(toppings));
    }

    public String getName() {
        return name;
    }

//    public double getPrice() {
//        return price;
//    }

    @Override
    public String toString() {
        return "Pizza{" + "name='" + name + '\'' + ", price="  + ", toppings=" + toppings + '}';
    }

    public void prepare() {
        System.out.println("Preparing..... " + name);
        System.out.println("Adding toppings:");
        for (Topping topping : toppings) {
            System.out.println("- " + topping.getNombre());
            //put 1 second delay
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("The Pizza is ready!");
    }
}
