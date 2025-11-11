package com.example.variant_a.controllers;



import com.example.variant_a.entities.Item;
import com.example.variant_a.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/items")
@CrossOrigin(origins = "*") // optional, useful for frontend testing
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // 🧾 GET all items (paginated)
    @GetMapping
    public Page<Item> getAllItems(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Long categoryId) {

        // if categoryId param is provided, filter by category
        if (categoryId != null) {
            return itemRepository.findByCategoryId(categoryId, (Pageable) PageRequest.of(page, size));
        }
        return itemRepository.findAll(PageRequest.of(page, size));
    }

    // 🔍 GET single item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemRepository.findById(id);
        return item.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // ➕ POST new item
    @PostMapping
    public Item createItem(@RequestBody Item item) {
        return itemRepository.save(item);
    }

    // ✏️ PUT update item
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item updatedItem) {
        return itemRepository.findById(id)
                .map(existing -> {
                    existing.setSku(updatedItem.getSku());
                    existing.setName(updatedItem.getName());
                    existing.setPrice(updatedItem.getPrice());
                    existing.setStock(updatedItem.getStock());
                    existing.setCategory(updatedItem.getCategory());
                    return ResponseEntity.ok(itemRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ❌ DELETE item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
