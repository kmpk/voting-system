package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import static com.github.kmpk.votingsystem.util.ValidationUtil.checkNotFoundWithId;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    default Restaurant create(Restaurant restaurant) {
        if (!restaurant.isNew()) {
            restaurant.setId(null);
        }
        return save(restaurant);
    }

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int deleteAndCount(@Param("id") int id);

    default void delete(int id) {
        checkNotFoundWithId(deleteAndCount(id) == 1, id);
    }

    @Transactional
    default void update(Restaurant restaurant) {
        Restaurant current = checkNotFoundWithId(get(restaurant.id()), restaurant.id());
        current.setAddress(restaurant.getAddress());
        current.setName(restaurant.getName());
        current.setDescription(restaurant.getDescription());
        save(current);
    }

    default Restaurant get(int id) {
        return checkNotFoundWithId(findById(id).orElse(null), id);
    }
}
