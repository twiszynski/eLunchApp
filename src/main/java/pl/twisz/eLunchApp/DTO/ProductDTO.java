package pl.twisz.eLunchApp.DTO;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import net.karneim.pojobuilder.GeneratePojoBuilder;

import java.util.List;
import java.util.UUID;
@GeneratePojoBuilder
public class ProductDTO {

    @NotNull
    private UUID uuid;

    @NotBlank
    private String name;

    @NotNull
    private List<IngredientDTO> ingredientDTOS;

    @Nullable
    private DishDTO dishDTO;

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

    public List<IngredientDTO> getIngredients() {
        return ingredientDTOS;
    }

    public void setIngredients(List<IngredientDTO> ingredientDTOS) {
        this.ingredientDTOS = ingredientDTOS;
    }

    @Nullable
    public DishDTO getDish() {
        return dishDTO;
    }

    public void setDish(@Nullable DishDTO dishDTO) {
        this.dishDTO = dishDTO;
    }
}
