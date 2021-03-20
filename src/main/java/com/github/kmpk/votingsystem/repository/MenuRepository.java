package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.kmpk.votingsystem.util.ValidationUtil.checkNotFoundWithId;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Transactional
    default List<Menu> getAll() {
        List<Menu> all = findAll();
        fetchData(all);
        return all;
    }

    List<Menu> findAllByDate(LocalDate date);

    @Transactional
    default List<Menu> getAllByDate(LocalDate date) {
        List<Menu> allByDate = findAllByDate(date);
        fetchData(allByDate);
        return allByDate;
    }

    default void fetchData(List<Menu> menus) {
        menus.forEach(m -> {
            m.getRestaurant().getAddress();
            m.getDishes().size();
        });
    }

    default Menu get(int id) {
        return checkNotFoundWithId(findById(id).orElse(null), id);
    }
}
