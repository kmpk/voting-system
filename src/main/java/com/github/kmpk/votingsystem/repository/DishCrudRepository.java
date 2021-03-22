package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DishCrudRepository extends JpaRepository<Dish, Integer> {
    List<Dish> findAllByMenu_Id(int menuId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.menu.id=:menuId")
    int delete(@Param("id") int id, @Param("menuId") int menuId);
}
