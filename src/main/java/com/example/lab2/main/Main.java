package com.example.lab2.main;

import com.example.lab2.controllers.BaseController;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Context context = new Context();
        context.setState(new BaseController(context));

        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            context.getState().sendActionsList();
            input = scanner.nextLine();

            if ("exit".equals(input)) {
                return;
            }

            context.getState().answerToInputAction(input);
        }
    }
}
