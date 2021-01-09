package com.ach.danslemeal.web.rest;

import com.ach.danslemeal.domain.BusinessHours;
import com.ach.danslemeal.repository.BusinessHoursRepository;
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
 * REST controller for managing {@link com.ach.danslemeal.domain.BusinessHours}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class BusinessHoursResource {
    private final Logger log = LoggerFactory.getLogger(BusinessHoursResource.class);

    private static final String ENTITY_NAME = "businessHours";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BusinessHoursRepository businessHoursRepository;

    public BusinessHoursResource(BusinessHoursRepository businessHoursRepository) {
        this.businessHoursRepository = businessHoursRepository;
    }

    /**
     * {@code POST  /business-hours} : Create a new businessHours.
     *
     * @param businessHours the businessHours to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessHours, or with status {@code 400 (Bad Request)} if the businessHours has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/business-hours")
    public ResponseEntity<BusinessHours> createBusinessHours(@RequestBody BusinessHours businessHours) throws URISyntaxException {
        log.debug("REST request to save BusinessHours : {}", businessHours);
        if (businessHours.getId() != null) {
            throw new BadRequestAlertException("A new businessHours cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BusinessHours result = businessHoursRepository.save(businessHours);
        return ResponseEntity
            .created(new URI("/api/business-hours/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /business-hours} : Updates an existing businessHours.
     *
     * @param businessHours the businessHours to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessHours,
     * or with status {@code 400 (Bad Request)} if the businessHours is not valid,
     * or with status {@code 500 (Internal Server Error)} if the businessHours couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/business-hours")
    public ResponseEntity<BusinessHours> updateBusinessHours(@RequestBody BusinessHours businessHours) throws URISyntaxException {
        log.debug("REST request to update BusinessHours : {}", businessHours);
        if (businessHours.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BusinessHours result = businessHoursRepository.save(businessHours);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, businessHours.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /business-hours} : get all the businessHours.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessHours in body.
     */
    @GetMapping("/business-hours")
    public List<BusinessHours> getAllBusinessHours() {
        log.debug("REST request to get all BusinessHours");
        return businessHoursRepository.findAll();
    }

    /**
     * {@code GET  /business-hours/:id} : get the "id" businessHours.
     *
     * @param id the id of the businessHours to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessHours, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/business-hours/{id}")
    public ResponseEntity<BusinessHours> getBusinessHours(@PathVariable Long id) {
        log.debug("REST request to get BusinessHours : {}", id);
        Optional<BusinessHours> businessHours = businessHoursRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(businessHours);
    }

    /**
     * {@code DELETE  /business-hours/:id} : delete the "id" businessHours.
     *
     * @param id the id of the businessHours to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/business-hours/{id}")
    public ResponseEntity<Void> deleteBusinessHours(@PathVariable Long id) {
        log.debug("REST request to delete BusinessHours : {}", id);
        businessHoursRepository.deleteById(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
