package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Dish;

import java.util.List;

public interface DishRepository {
    List<Dish> create(int menuId, Dish... dishes);

    List<Dish> getAll(int menuId);

    Dish get(int dishId, int menuId);

    void update(Dish dish, int menuId);

    void delete(int dishId, int menuId);
}
