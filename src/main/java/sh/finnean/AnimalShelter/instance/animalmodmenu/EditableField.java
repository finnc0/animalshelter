package sh.finnean.AnimalShelter.instance.animalmodmenu;

import java.util.function.Consumer;
import java.util.function.Supplier;

public record EditableField(
        String label,
        FieldType fieldType,
        // method to get the field value
        Supplier<Object> getter,
        // method to set the field value
        Consumer<Object> setter
) {
}
