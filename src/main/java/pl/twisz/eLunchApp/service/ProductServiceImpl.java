package pl.twisz.eLunchApp.service;

import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.IngredientDTO;
import pl.twisz.eLunchApp.DTO.ProductDTO;
import pl.twisz.eLunchApp.model.*;
import pl.twisz.eLunchApp.repo.DishRepo;
import pl.twisz.eLunchApp.repo.IngredientRepo;
import pl.twisz.eLunchApp.repo.OrderItemRepo;
import pl.twisz.eLunchApp.repo.ProductRepo;
import pl.twisz.eLunchApp.utils.ConverterUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final IngredientRepo ingredientRepo;
    private final DishRepo dishRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo, IngredientRepo ingredientRepo, DishRepo dishRepo) {
        this.productRepo = productRepo;
        this.ingredientRepo = ingredientRepo;
        this.dishRepo = dishRepo;
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepo.findAll().stream()
                .map(ConverterUtils::convert)
                .toList();
    }

    @Override
    public void put(UUID uuid, ProductDTO productDTO) {
        if (!Objects.equal(productDTO.getUuid(), uuid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (IngredientDTO p : productDTO.getIngredients()) {
            Ingredient ingredient = ingredientRepo.findByUuid(p.getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            ingredients.add(ingredient);
        }

        Product product = productRepo.findByUuid(productDTO.getUuid())
                .orElseGet(() -> newProduct(uuid));

        product.setName(productDTO.getName());
        product.setIngredients(ingredients);

        if (productDTO.getDish() != null) {
            Dish dish = dishRepo.findByUuid(productDTO.getDish().getUuid())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            product.setDish(dish);
        } else {
            product.setDish(null);
        }

        if (product.getId() == null) {
            productRepo.save(product);
        }
    }

    private Product newProduct(UUID uuid) {
        return new ProductBuilder()
                .withUuid(uuid)
                .build();
    }

    @Override
    public void delete(UUID uuid) {
        Product product = productRepo.findByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        productRepo.delete(product);
    }

    @Override
    public Optional<ProductDTO> getByUuid(UUID uuid) {
        return productRepo.findByUuid(uuid).map(ConverterUtils::convert);    }
}
