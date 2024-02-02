package pl.twisz.eLunchApp.controller;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.twisz.eLunchApp.DTO.DishDTO;
import pl.twisz.eLunchApp.DTO.IngredientDTO;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.ProductDTO;
import pl.twisz.eLunchApp.service.OrderService;
import pl.twisz.eLunchApp.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
    interface ProductListView extends ProductDTO.View.Basic {}
    interface ProductView extends ProductDTO.View.Extended, IngredientDTO.View.Basic, DishDTO.View.Basic {}

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @JsonView(ProductListView.class)
    @GetMapping
    public List<ProductDTO> get(){
        return productService.getAll();
    }

    @JsonView(ProductView.class)
    @GetMapping("/{uuid}")
    public ProductDTO get(@PathVariable UUID uuid){
        return productService.getByUuid(uuid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody @Valid ProductDTO json){
        productService.put(uuid, json);
    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){
        productService.delete(uuid);
    }

}
