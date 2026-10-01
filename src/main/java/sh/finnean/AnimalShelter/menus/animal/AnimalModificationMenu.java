package sh.finnean.AnimalShelter.menus.animal;

import sh.finnean.AnimalShelter.instance.Animal;
import sh.finnean.AnimalShelter.instance.AnimalType;
import sh.finnean.AnimalShelter.instance.Menu;
import sh.finnean.AnimalShelter.instance.animalmodmenu.EditableField;
import sh.finnean.AnimalShelter.manager.AnimalManager;
import sh.finnean.AnimalShelter.utils.ShelterUtil;

import java.util.List;
import java.util.Scanner;

public class AnimalModificationMenu extends Menu {

    private final Scanner scanner;
    private final Animal animal;
    private List<EditableField> editableFields;

    public AnimalModificationMenu(String title, Scanner scanner, Animal animal) {
        super(title, scanner);

        this.scanner = scanner;
        this.animal = animal;
        //set default value, used to determine the number of choices for the handle choice method.
    }

    private void displayEditableField() {
        List<EditableField> editableFieldList = animal.getEditableFields(this.animal);

        this.editableFields = editableFieldList;

        System.out.println();
        for (EditableField eF : editableFieldList) {
            System.out.println((editableFieldList.indexOf(eF) + 1) + ". Field to edit - " + eF.label() + ": ");
        }

        // we add +1 for the quit option at the end of the choice menu.
        System.out.println((editableFieldList.size()+1) + ". Back");
        System.out.println();
    }

    @Override
    protected void printOptions() {
       displayEditableField();
    }

    @Override
    protected boolean handleChoice(int choice) {
        if (choice == (editableFields.size()+1)) {
            return false;
        }

        if (choice < 1 && choice >= (editableFields.size()+1)) {
            System.out.println("Invalid choice, try again. ");
            return false;
        }

        EditableField field = editableFields.get(choice - 1);
        Object newValue = switch (field.fieldType()) {
            case STRING  -> ShelterUtil.strPromptNotBlank(field.label(), scanner, null);
            case BOOLEAN -> ShelterUtil.boolPromptNotBlank(field.label(), scanner, AnimalType.DOG, "Is");
            case DATE    -> ShelterUtil.promptDate(field.label(), scanner);
        };
        field.setter().accept(newValue);
        System.out.println("Updated " + field.label() + " successfully!");
        return true;   // stay in the menu
    }
}
