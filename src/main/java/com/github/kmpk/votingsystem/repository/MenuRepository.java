package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
}
