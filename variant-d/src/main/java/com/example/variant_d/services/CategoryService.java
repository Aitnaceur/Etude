package com.example.variant_d.services;


import com.example.variant_d.entities.Category;
import com.example.variant_d.repositories.CategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository repo;

    public CategoryService(CategoryRepository repo) {
        this.repo = repo;
    }

    public Page<Category> getAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size));
    }

    public Optional<Category> getById(Long id) { return repo.findById(id); }

    public Category save(Category c) { return repo.save(c); }

    public void delete(Long id) { repo.deleteById(id); }
}
