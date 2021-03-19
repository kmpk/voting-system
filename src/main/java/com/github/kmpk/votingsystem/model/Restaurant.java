package com.github.kmpk.votingsystem.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "restaurants", uniqueConstraints = @UniqueConstraint(columnNames = "address", name = "restaurants_address_idx"))
public class Restaurant extends AbstractNamedEntity {

    @NotBlank
    @Size(min = 10, max = 100)
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank
    @Size(max = 200)
    @Column(name = "description", nullable = false)
    private String description;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address, String description) {
        super(id, name);
        this.address = address;
        this.description = description;
    }

    public Restaurant(Restaurant restaurant) {
        this(restaurant.getId(), restaurant.getName(), restaurant.getAddress(), restaurant.getDescription());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
