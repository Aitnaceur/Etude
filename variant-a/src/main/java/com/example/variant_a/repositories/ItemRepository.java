package com.example.variant_a.repositories;


import com.example.variant_a.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable; // ✅ correct import
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Page<Item> findByCategoryId(Long categoryId, Pageable pageable);
}
