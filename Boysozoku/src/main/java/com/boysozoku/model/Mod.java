package com.boysozoku.model;

import com.boysozoku.model.enums.Console;

public class Mod {
    int id;
    Console console;
    float price;
    String name;

    public Mod(int id, Console console, float price, String name) {
        this.id = id;
        this.console = console;
        this.price = price;
        this.name = name;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId(){
        return id;
    }
}
