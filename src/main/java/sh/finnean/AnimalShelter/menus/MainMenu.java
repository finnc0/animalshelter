package sh.finnean.AnimalShelter.menus;

import java.util.Scanner;

public class MainMenu extends Menu {

    private final Scanner scanner;

    public MainMenu(String title, Scanner scanner) {
        super(title, scanner);
        this.scanner = scanner;
    }

    @Override
    protected void printOptions() {
        System.out.println("1. Owner Management Menu");
        System.out.println("2. Initial Data Import Menu");
        System.out.println("3. Animal Management Menu");
        System.out.println("4. Adoption Menu");
        System.out.println("5. Quit");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                new OwnerManagementMenu("Owner MGMT", this.scanner).run();
                return true;
            case 5: return false;
            default: return true;
        }

    }
}
