package com.ach.danslemeal.web.rest;

import com.ach.danslemeal.domain.MysteriousOrder;
import com.ach.danslemeal.repository.MysteriousOrderRepository;
import com.ach.danslemeal.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link com.ach.danslemeal.domain.MysteriousOrder}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class MysteriousOrderResource {
    private final Logger log = LoggerFactory.getLogger(MysteriousOrderResource.class);

    private static final String ENTITY_NAME = "mysteriousOrder";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MysteriousOrderRepository mysteriousOrderRepository;

    public MysteriousOrderResource(MysteriousOrderRepository mysteriousOrderRepository) {
        this.mysteriousOrderRepository = mysteriousOrderRepository;
    }

    /**
     * {@code POST  /mysterious-orders} : Create a new mysteriousOrder.
     *
     * @param mysteriousOrder the mysteriousOrder to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mysteriousOrder, or with status {@code 400 (Bad Request)} if the mysteriousOrder has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mysterious-orders")
    public ResponseEntity<MysteriousOrder> createMysteriousOrder(@RequestBody MysteriousOrder mysteriousOrder) throws URISyntaxException {
        log.debug("REST request to save MysteriousOrder : {}", mysteriousOrder);
        if (mysteriousOrder.getId() != null) {
            throw new BadRequestAlertException("A new mysteriousOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MysteriousOrder result = mysteriousOrderRepository.save(mysteriousOrder);
        return ResponseEntity
            .created(new URI("/api/mysterious-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mysterious-orders} : Updates an existing mysteriousOrder.
     *
     * @param mysteriousOrder the mysteriousOrder to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mysteriousOrder,
     * or with status {@code 400 (Bad Request)} if the mysteriousOrder is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mysteriousOrder couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mysterious-orders")
    public ResponseEntity<MysteriousOrder> updateMysteriousOrder(@RequestBody MysteriousOrder mysteriousOrder) throws URISyntaxException {
        log.debug("REST request to update MysteriousOrder : {}", mysteriousOrder);
        if (mysteriousOrder.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MysteriousOrder result = mysteriousOrderRepository.save(mysteriousOrder);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, mysteriousOrder.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mysterious-orders} : get all the mysteriousOrders.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mysteriousOrders in body.
     */
    @GetMapping("/mysterious-orders")
    public List<MysteriousOrder> getAllMysteriousOrders() {
        log.debug("REST request to get all MysteriousOrders");
        return mysteriousOrderRepository.findAll();
    }

    /**
     * {@code GET  /mysterious-orders/:id} : get the "id" mysteriousOrder.
     *
     * @param id the id of the mysteriousOrder to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mysteriousOrder, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mysterious-orders/{id}")
    public ResponseEntity<MysteriousOrder> getMysteriousOrder(@PathVariable Long id) {
        log.debug("REST request to get MysteriousOrder : {}", id);
        Optional<MysteriousOrder> mysteriousOrder = mysteriousOrderRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mysteriousOrder);
    }

    /**
     * {@code DELETE  /mysterious-orders/:id} : delete the "id" mysteriousOrder.
     *
     * @param id the id of the mysteriousOrder to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mysterious-orders/{id}")
    public ResponseEntity<Void> deleteMysteriousOrder(@PathVariable Long id) {
        log.debug("REST request to delete MysteriousOrder : {}", id);
        mysteriousOrderRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
