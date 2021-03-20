package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Menu;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.github.kmpk.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class MenuRepositoryImpl implements MenuRepository {

    private final MenuCrudRepository repository;
    private final RestaurantRepository restaurantRepository;

    public MenuRepositoryImpl(MenuCrudRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Menu> getAll() {
        return repository.findAll();
    }

    @Override
    public List<Menu> getAllByDate(LocalDate date) {
        return repository.getAllFetchedByDate(date);
    }

    @Override
    public Menu get(int id, int restaurantId) {
        return checkNotFoundWithId(repository
                .findById(id)
                .filter(m -> m.getRestaurant().getId() == restaurantId)
                .orElse(null), id);
    }

    @Override
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId), id);
    }

    @Override
    public Menu create(Menu menu, int restaurantId) {
        menu.setId(null);
        menu.setRestaurant(restaurantRepository.get(restaurantId));
        return repository.save(menu);
    }

    @Override
    @Transactional
    public void update(Menu menu, int restaurantId) {
        get(menu.getId(), restaurantId);
        repository.save(menu);
    }

    @Override
    public List<Menu> getAllByRestaurantId(int restaurantId) {
        return repository.getAllFetchedByRestaurantId(restaurantId);
    }
}
