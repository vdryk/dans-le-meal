package com.ach.danslemeal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A BusinessHours.
 */
@Entity
@Table(name = "business_hours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BusinessHours implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "day")
    private Integer day;

    @Column(name = "opening_time")
    private String openingTime;

    @Column(name = "closing_time")
    private String closingTime;

    @ManyToOne
    @JsonIgnoreProperties(value = "businessHours", allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public BusinessHours day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public BusinessHours openingTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public BusinessHours closingTime(String closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public BusinessHours restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BusinessHours)) {
            return false;
        }
        return id != null && id.equals(((BusinessHours) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BusinessHours{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", openingTime='" + getOpeningTime() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            "}";
    }
}
