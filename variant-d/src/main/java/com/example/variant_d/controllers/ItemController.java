package com.example.variant_d.controllers;


import com.example.variant_d.entities.Item;
import com.example.variant_d.services.ItemService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "*")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Item> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) Long categoryId) {
        if (categoryId != null)
            return service.getByCategory(categoryId, page, size);
        return service.getAll(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        Optional<Item> item = service.getById(id);
        return item.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Item create(@RequestBody Item i) { return service.save(i); }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item i) {
        return service.getById(id)
                .map(existing -> {
                    existing.setName(i.getName());
                    existing.setSku(i.getSku());
                    existing.setPrice(i.getPrice());
                    existing.setStock(i.getStock());
                    existing.setCategory(i.getCategory());
                    return ResponseEntity.ok(service.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
