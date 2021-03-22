package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Dish;
import com.github.kmpk.votingsystem.model.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.github.kmpk.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class DishRepositoryImpl implements DishRepository {

    private final DishCrudRepository repository;
    private final MenuRepository menuRepository;

    public DishRepositoryImpl(DishCrudRepository repository, MenuRepository menuRepository) {
        this.repository = repository;
        this.menuRepository = menuRepository;
    }

    @Override
    public List<Dish> create(int menuId, Dish... dishes) {
        Menu menu = menuRepository.get(menuId);
        for (Dish d : dishes) {
            d.setMenu(menu);
        }
        return repository.saveAll(List.of(dishes));
    }

    @Override
    public List<Dish> getAll(int menuId) {
        return repository.findAllByMenu_Id(menuId);
    }

    @Override
    public Dish get(int id, int menuId) {
        return checkNotFoundWithId(repository
                .findById(id)
                .filter(d -> d.getMenu().getId() == menuId)
                .orElse(null), id);
    }

    @Override
    @Transactional
    public void update(Dish dish, int menuId) {
        Dish current = get(dish.getId(), menuId);
        current.setName(dish.getName());
        current.setPrice(dish.getPrice());
        repository.save(current);
    }

    @Override
    public void delete(int id, int menuId) {
        checkNotFoundWithId(repository.delete(id, menuId), id);
    }
}
