package com.github.kmpk.votingsystem.to;

import com.github.kmpk.votingsystem.model.Restaurant;

import java.util.Objects;

public class VotesCountTo implements Comparable<VotesCountTo> {
    private Restaurant restaurant;
    private Long count;

    public VotesCountTo() {
    }

    public VotesCountTo(Restaurant restaurant, Long count) {
        this.restaurant = restaurant;
        this.count = count;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public int compareTo(VotesCountTo o) {
        if (o == null) throw new NullPointerException();
        if (Objects.equals(this.count, o.count)) return 0;
        return this.count > o.count ? 1 : -1;
    }
}
