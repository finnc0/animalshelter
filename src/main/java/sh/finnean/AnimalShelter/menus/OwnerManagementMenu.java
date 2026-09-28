package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.manager.OwnerManager;

import java.util.Scanner;

public class OwnerManagementMenu extends Menu{

    private final Scanner scanner;
    private final OwnerManager ownerManager;

    public OwnerManagementMenu(String title, Scanner scanner, OwnerManager ownerManager) {
        super(title, scanner);
        this.scanner = scanner;
        this.ownerManager = ownerManager;
    }

    @Override
    protected void printOptions() {
        System.out.println();
        System.out.println("1. Add new owner");
        System.out.println("2. Remove owner");
        System.out.println("3. View owner");
        System.out.println("4. View all owners");
        System.out.println("5. Back");
    }

    @Override
    protected boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                // get info to create an owner
                System.out.print("Enter a name: ");
                String name = scanner.nextLine();

                System.out.print("Enter an email: ");
                String email = scanner.nextLine();

                System.out.print("Enter a phone number e. 843-123-4567: ");
                String phoneNumber = scanner.nextLine();

                // catch exception if thrown from ownermanager
                try {
                    ownerManager.addOwner(new Owner(name, email, phoneNumber));
                    System.out.println("Successfully added a new owner!");
                } catch (IllegalStateException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                for (Owner owner : ownerManager.getAllOwners()) {
                    // print new line above and before owner data
                    System.out.println();
                    owner.displayInfo();
                    System.out.println();
                }
                break;
            case 5: return false;
            default:
                System.out.println("Please enter a choice 1-5.");
        }
        return true;
    }
}
