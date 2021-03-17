package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DishRepository extends JpaRepository<Dish, Integer> {
}
