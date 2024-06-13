package org.example;

public class ConsoleRenderer {
    public static void printRow(char[] chars){
        int width = chars.length*11;
        int columns = chars.length;
        width -=4;
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("");
        for(int i = 0; i < columns ;i++) {
            System.out.printf(String.format("%-5s%-5s", "|", chars[i]));
        }
        System.out.print("|");
        System.out.println("");
    }
}
