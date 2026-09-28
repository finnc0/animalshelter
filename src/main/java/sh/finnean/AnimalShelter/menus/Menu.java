package sh.finnean.AnimalShelter.menus;

import java.util.Scanner;

public abstract class Menu {

    private final Scanner scanner;
    private final String title;

    public Menu(String title, Scanner scanner) {
        this.scanner = scanner;
        this.title = title;
    }

    public void run() {
        boolean running = true;
        while (running) {
            System.out.printf("------- %s -------\n", title);
            // 1st print options
            this.printOptions();
            System.out.println();
            // then verify input and handle choices
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                running = handleChoice(choice);
            } catch (NumberFormatException e1) {
                System.out.println("Please enter a valid choice.");
            }
        }
    }

    protected abstract void printOptions();
    protected abstract boolean handleChoice(int choice);
}
