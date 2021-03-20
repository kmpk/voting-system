package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Menu;

import java.time.LocalDate;
import java.util.List;

public interface MenuRepository {
    List<Menu> getAll();

    List<Menu> getAllByDate(LocalDate date);

    Menu get(int id, int restaurantId);

    void delete(int id, int restaurantId);

    Menu create(Menu menu, int restaurantId);

    void update(Menu menu, int restaurantId);
}
