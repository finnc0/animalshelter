package sh.finnean.AnimalShelter.instance;

import java.util.Arrays;
import java.util.Optional;

public enum AnimalType {
    DOG,
    CAT,
    BIRD,
    HORSE;

    // helper that returns a potential animal type, used to check if string is one of the enum values
    public static Optional<AnimalType> fromString(String s) {
        return Arrays.stream(values())
                .filter(t -> t.name().equalsIgnoreCase(s.trim()))
                .findFirst();
    }
}
