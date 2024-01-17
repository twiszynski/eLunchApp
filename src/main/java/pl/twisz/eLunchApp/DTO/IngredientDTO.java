package pl.twisz.eLunchApp.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class IngredientDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    private Boolean isAllergen;


    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllergen() {
        return isAllergen;
    }

    public void setAllergen(Boolean allergen) {
        isAllergen = allergen;
    }
}
