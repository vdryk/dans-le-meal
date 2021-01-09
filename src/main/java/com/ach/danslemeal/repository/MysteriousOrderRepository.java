package com.ach.danslemeal.repository;

import com.ach.danslemeal.domain.MysteriousOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the MysteriousOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MysteriousOrderRepository extends JpaRepository<MysteriousOrder, Long> {}
