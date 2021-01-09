package com.ach.danslemeal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Restaurant implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "delivery")
    private Boolean delivery;

    @Column(name = "opened")
    private Boolean opened;

    @Column(name = "price")
    private Float price;

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<BusinessHours> businessHours = new HashSet<>();

    @OneToMany(mappedBy = "restaurant")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MysteriousOrder> mysteriousOrders = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "restaurants", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Restaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Restaurant latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Restaurant longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Boolean isDelivery() {
        return delivery;
    }

    public Restaurant delivery(Boolean delivery) {
        this.delivery = delivery;
        return this;
    }

    public void setDelivery(Boolean delivery) {
        this.delivery = delivery;
    }

    public Boolean isOpened() {
        return opened;
    }

    public Restaurant opened(Boolean opened) {
        this.opened = opened;
        return this;
    }

    public void setOpened(Boolean opened) {
        this.opened = opened;
    }

    public Float getPrice() {
        return price;
    }

    public Restaurant price(Float price) {
        this.price = price;
        return this;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<BusinessHours> getBusinessHours() {
        return businessHours;
    }

    public Restaurant businessHours(Set<BusinessHours> businessHours) {
        this.businessHours = businessHours;
        return this;
    }

    public Restaurant addBusinessHours(BusinessHours businessHours) {
        this.businessHours.add(businessHours);
        businessHours.setRestaurant(this);
        return this;
    }

    public Restaurant removeBusinessHours(BusinessHours businessHours) {
        this.businessHours.remove(businessHours);
        businessHours.setRestaurant(null);
        return this;
    }

    public void setBusinessHours(Set<BusinessHours> businessHours) {
        this.businessHours = businessHours;
    }

    public Set<MysteriousOrder> getMysteriousOrders() {
        return mysteriousOrders;
    }

    public Restaurant mysteriousOrders(Set<MysteriousOrder> mysteriousOrders) {
        this.mysteriousOrders = mysteriousOrders;
        return this;
    }

    public Restaurant addMysteriousOrder(MysteriousOrder mysteriousOrder) {
        this.mysteriousOrders.add(mysteriousOrder);
        mysteriousOrder.setRestaurant(this);
        return this;
    }

    public Restaurant removeMysteriousOrder(MysteriousOrder mysteriousOrder) {
        this.mysteriousOrders.remove(mysteriousOrder);
        mysteriousOrder.setRestaurant(null);
        return this;
    }

    public void setMysteriousOrders(Set<MysteriousOrder> mysteriousOrders) {
        this.mysteriousOrders = mysteriousOrders;
    }

    public User getUser() {
        return user;
    }

    public Restaurant user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", delivery='" + isDelivery() + "'" +
            ", opened='" + isOpened() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
