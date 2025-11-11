package com.example.variant_d.services;


import com.example.variant_d.entities.Item;
import com.example.variant_d.repositories.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ItemService {
    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public Page<Item> getAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    public Page<Item> getByCategory(Long categoryId, int page, int size) {
        return repo.findByCategoryId(categoryId, PageRequest.of(page, size));
    }

    public Optional<Item> getById(Long id) { return repo.findById(id); }

    public Item save(Item i) { return repo.save(i); }

    public void delete(Long id) { repo.deleteById(id); }
}
