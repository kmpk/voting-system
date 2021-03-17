package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
