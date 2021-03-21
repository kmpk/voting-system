package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByMenu_Id(int menuId);
}
