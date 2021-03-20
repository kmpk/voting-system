package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface MenuCrudRepository extends JpaRepository<Menu, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.id=:id AND m.restaurant.id=:restaurantId")
    int delete(@Param("id") int id, @Param("restaurantId") int userId);

    List<Menu> findAllByDate(LocalDate date);

    @Query("SELECT m FROM Menu m WHERE m.restaurant.id=:restaurantId")
    List<Menu> findAllByRestaurant(@Param("restaurantId") int restaurantId);

    @Transactional//todo: fix n+1
    default List<Menu> getAllFetchedByDate(LocalDate date) {
        List<Menu> allByDate = findAllByDate(date);
        fetchData(allByDate);
        return allByDate;
    }

    @Transactional//todo: fix n+1
    default List<Menu> getAllFetchedByRestaurantId(int restaurantId) {
        List<Menu> allByRestaurant = findAllByRestaurant(restaurantId);
        fetchData(allByRestaurant);
        return allByRestaurant;
    }

    default void fetchData(List<Menu> menus) {
        menus.forEach(this::fetchData);
    }

    default void fetchData(Menu menu) {
        menu.getRestaurant().getAddress();
        menu.getDishes().size();
    }


}
