package com.example.api.controllers;

import com.example.api.entities.Dish;
import com.example.api.errors.EntityNotFoundException;
import com.example.api.repositories.DishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/dishes")
@RestController
public class DishesController {
    private final DishesRepository repository;

    @Autowired
    public DishesController(DishesRepository repository) {
        this.repository = repository;
    }

    @GetMapping("all")
    public List<Dish> findAll() {
        return (List<Dish>) repository.findAll();
    }

    @GetMapping("/{id}")
    public Dish findDishById(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id));
    }
}
