package com.github.kmpk.votingsystem.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

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

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    private Set<Menu> menus;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name, String address, String description, Set<Menu> menus) {
        super(id, name);
        this.address = address;
        this.description = description;
        this.menus = menus;
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

    public Set<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Set<Menu> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", description='" + description + '\'' +
                ", menus=" + menus +
                '}';
    }
}
