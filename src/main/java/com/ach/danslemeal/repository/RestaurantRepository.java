package com.ach.danslemeal.repository;

import com.ach.danslemeal.domain.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Restaurant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query("select restaurant from Restaurant restaurant where restaurant.user.login = ?#{principal.username}")
    List<Restaurant> findByUserIsCurrentUser();

    @Query("select restaurant from Restaurant restaurant join BusinessHours businessHours where businessHours.openingTime")
    List<Restaurant> findOpenedRestaurants();
}
