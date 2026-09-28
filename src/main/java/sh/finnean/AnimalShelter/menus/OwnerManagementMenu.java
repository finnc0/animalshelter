package sh.finnean.AnimalShelter.menus;

import sh.finnean.AnimalShelter.instance.Owner;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.manager.OwnerManager;

import java.util.Scanner;
import java.util.UUID;

public class OwnerManagementMenu extends Menu{

    private final Scanner scanner;
    private final OwnerManager ownerManager;
    private final AnimalManager animalManager;

    public OwnerManagementMenu(String title, Scanner scanner, OwnerManager ownerManager, AnimalManager animalManager) {
        super(title, scanner);
        this.scanner = scanner;
        this.ownerManager = ownerManager;
        this.animalManager = animalManager;
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
            case 2:
                // we will remove by accepting an email.
                System.out.println("Please enter an email: ");
                String searchEmail = scanner.nextLine();
                // if owner could not be found with email, the exception will be thrown.
                try {
                    Owner removingOwner = ownerManager.getByEmail(searchEmail);
                    // email is valid and maps to an owner
                    // now check if the owner has any adopted pets on their file, if so deny the removal
                    if (animalManager.getOwnersAdoptions(removingOwner).isPresent()) throw new Exception("Please unadopt your pets first.");

                    // owner is valid for removal
                    ownerManager.removeOwner(removingOwner);
                    System.out.println("Successfully removed the owner.");

                } catch(Exception e) {
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
