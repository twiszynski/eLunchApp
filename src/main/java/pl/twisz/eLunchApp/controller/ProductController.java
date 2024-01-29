package pl.twisz.eLunchApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.twisz.eLunchApp.DTO.OrderDTO;
import pl.twisz.eLunchApp.DTO.ProductDTO;
import pl.twisz.eLunchApp.service.OrderService;
import pl.twisz.eLunchApp.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {


    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDTO> get(){
        return null;
    }

    @GetMapping("/{uuid}")
    public ProductDTO get(@PathVariable UUID uuid){
        return null;
    }

    @Transactional
    @PutMapping("/{uuid}")
    public void put(@PathVariable UUID uuid, @RequestBody ProductDTO json){

    }

    @Transactional
    @DeleteMapping("/{uuid}")
    public void delete(@PathVariable UUID uuid){

    }

}
