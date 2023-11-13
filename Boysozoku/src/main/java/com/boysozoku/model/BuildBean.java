package com.boysozoku.model;

import com.boysozoku.model.enums.Console;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;
import java.util.Scanner;

public class BuildBean {
String name = null;
Console console = null;
Optional<Shell> shell = Optional.empty();
Optional<Screen> screen = Optional.empty();
Optional<Buttons> buttons = Optional.empty();

    final static float MOUNT_PRICE = 15.00f;
float price = 0.00f;

File custom = null;
Boolean mount = false;

public File toFile(){
    File result = new File(name+".txt");
    try {
        if (result.createNewFile()) {
            System.out.println("File created: " + result.getName());
        } else {
            System.out.println("File already exists.");
        }
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    try {
        FileWriter myWriter = new FileWriter(name+".txt");
        myWriter.write(name);
        if (shell.isPresent())
            myWriter.write("\n"+shell.get().getName());
        if (screen.isPresent())
            myWriter.write("\n"+screen.get().getName());
        if (buttons.isPresent())
            myWriter.write("\n"+buttons.get().getName());
        if (custom != null)
            myWriter.write("\n"+custom.getName());
        myWriter.close();
        System.out.println("Successfully wrote to the file.");
    } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }
    return result;
}

    public static float calcFullPrice(BuildBean bean){
        float price = 0.00f;
        if (bean.shell.isPresent())
            price += bean.shell.get().price;
        if (bean.screen.isPresent())
            price += bean.screen.get().price;
        if (bean.buttons.isPresent())
            price += bean.buttons.get().price;
        if (bean.mount)
            price += MOUNT_PRICE;
        return price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Optional<Shell> getShell() {
        return shell;
    }

    public void setShell(Optional<Shell> shell) {
        this.shell = shell;
    }

    public Optional<Screen> getScreen() {
        return screen;
    }

    public void setScreen(Optional<Screen> screen) {
        this.screen = screen;
    }

    public Optional<Buttons> getButtons() {
        return buttons;
    }

    public void setButtons(Optional<Buttons> buttons) {
        this.buttons = buttons;
    }

    public File getCustom() {
        return custom;
    }

    public void setCustom(File custom) {
        this.custom = custom;
    }

    public Boolean getMount() {
        return mount;
    }

    public void setMount(Boolean mount) {
        this.mount = mount;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float v) {
        this.price = v;
    }

    public Console getConsole() {
        return console;
    }

    public void setConsole(Console console) {
        this.console = console;
    }


    @Override
    public String toString() {
        String shellName;
        String screenName;
        String buttonsName;
        String encodedPath;
        if (custom!=null)
           encodedPath = Base64.getEncoder().encodeToString(custom.getAbsolutePath().getBytes());
        else encodedPath = null;
        if(shell.isPresent()) shellName = shell.get().getName(); else shellName ="null";
        if(screen.isPresent()) screenName = screen.get().getName(); else screenName="null";
        if(buttons.isPresent())  buttonsName = buttons.get().getName(); else buttonsName = "null";
        return  name + 'Þ' +
                console.name() + 'Þ' +
                shellName + 'Þ' +
                screenName + 'Þ' +
                buttonsName + 'Þ' +
                encodedPath + 'Þ' +
                mount;
    }
    public static BuildBean fromString(String string) {
        BuildBean result = new BuildBean();
        String[] parts = string.split("Þ");
        result.name = parts[0];
        result.console = Console.valueOf(parts[1]);
        result.shell = BuildDAO.shellList.stream().filter(shell -> shell.getName().equals(parts[2])).findFirst();
        result.screen = BuildDAO.screenList.stream().filter(screen -> screen.getName().equals(parts[3])).findFirst();
        result.buttons = BuildDAO.buttonsList.stream().filter(buttons -> buttons.getName().equals(parts[4])).findFirst();
        result.setCustom(new File(new String(Base64.getDecoder().decode(parts[5]))));
        return result;
    }
}
