package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {
    List<Menu> getAll();

    List<Menu> getAllByDate(LocalDate date);

    Menu get(int id, int restaurantId);

    Menu get(int id);

    void delete(int id, int restaurantId);

    Menu create(Menu menu, int restaurantId);

    void update(Menu menu, int restaurantId);

    List<Menu> getAllByRestaurantId(int restaurantId);
}
