package com.ach.danslemeal.repository;

import com.ach.danslemeal.domain.BusinessHours;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BusinessHours entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BusinessHoursRepository extends JpaRepository<BusinessHours, Long> {}
