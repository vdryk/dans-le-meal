package com.ach.danslemeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ach.danslemeal.DansLeMealApp;
import com.ach.danslemeal.domain.BusinessHours;
import com.ach.danslemeal.repository.BusinessHoursRepository;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BusinessHoursResource} REST controller.
 */
@SpringBootTest(classes = DansLeMealApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BusinessHoursResourceIT {
    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    private static final String DEFAULT_OPENING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_OPENING_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_CLOSING_TIME = "AAAAAAAAAA";
    private static final String UPDATED_CLOSING_TIME = "BBBBBBBBBB";

    @Autowired
    private BusinessHoursRepository businessHoursRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBusinessHoursMockMvc;

    private BusinessHours businessHours;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessHours createEntity(EntityManager em) {
        BusinessHours businessHours = new BusinessHours()
            .day(DEFAULT_DAY)
            .openingTime(DEFAULT_OPENING_TIME)
            .closingTime(DEFAULT_CLOSING_TIME);
        return businessHours;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BusinessHours createUpdatedEntity(EntityManager em) {
        BusinessHours businessHours = new BusinessHours()
            .day(UPDATED_DAY)
            .openingTime(UPDATED_OPENING_TIME)
            .closingTime(UPDATED_CLOSING_TIME);
        return businessHours;
    }

    @BeforeEach
    public void initTest() {
        businessHours = createEntity(em);
    }

    @Test
    @Transactional
    public void createBusinessHours() throws Exception {
        int databaseSizeBeforeCreate = businessHoursRepository.findAll().size();
        // Create the BusinessHours
        restBusinessHoursMockMvc
            .perform(
                post("/api/business-hours")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessHours))
            )
            .andExpect(status().isCreated());

        // Validate the BusinessHours in the database
        List<BusinessHours> businessHoursList = businessHoursRepository.findAll();
        assertThat(businessHoursList).hasSize(databaseSizeBeforeCreate + 1);
        BusinessHours testBusinessHours = businessHoursList.get(businessHoursList.size() - 1);
        assertThat(testBusinessHours.getDay()).isEqualTo(DEFAULT_DAY);
        assertThat(testBusinessHours.getOpeningTime()).isEqualTo(DEFAULT_OPENING_TIME);
        assertThat(testBusinessHours.getClosingTime()).isEqualTo(DEFAULT_CLOSING_TIME);
    }

    @Test
    @Transactional
    public void createBusinessHoursWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = businessHoursRepository.findAll().size();

        // Create the BusinessHours with an existing ID
        businessHours.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBusinessHoursMockMvc
            .perform(
                post("/api/business-hours")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(businessHours))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessHours in the database
        List<BusinessHours> businessHoursList = businessHoursRepository.findAll();
        assertThat(businessHoursList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBusinessHours() throws Exception {
        // Initialize the database
        businessHoursRepository.saveAndFlush(businessHours);

        // Get all the businessHoursList
        restBusinessHoursMockMvc
            .perform(get("/api/business-hours?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(businessHours.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)))
            .andExpect(jsonPath("$.[*].openingTime").value(hasItem(DEFAULT_OPENING_TIME)))
            .andExpect(jsonPath("$.[*].closingTime").value(hasItem(DEFAULT_CLOSING_TIME)));
    }

    @Test
    @Transactional
    public void getBusinessHours() throws Exception {
        // Initialize the database
        businessHoursRepository.saveAndFlush(businessHours);

        // Get the businessHours
        restBusinessHoursMockMvc
            .perform(get("/api/business-hours/{id}", businessHours.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(businessHours.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY))
            .andExpect(jsonPath("$.openingTime").value(DEFAULT_OPENING_TIME))
            .andExpect(jsonPath("$.closingTime").value(DEFAULT_CLOSING_TIME));
    }

    @Test
    @Transactional
    public void getNonExistingBusinessHours() throws Exception {
        // Get the businessHours
        restBusinessHoursMockMvc.perform(get("/api/business-hours/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBusinessHours() throws Exception {
        // Initialize the database
        businessHoursRepository.saveAndFlush(businessHours);

        int databaseSizeBeforeUpdate = businessHoursRepository.findAll().size();

        // Update the businessHours
        BusinessHours updatedBusinessHours = businessHoursRepository.findById(businessHours.getId()).get();
        // Disconnect from session so that the updates on updatedBusinessHours are not directly saved in db
        em.detach(updatedBusinessHours);
        updatedBusinessHours.day(UPDATED_DAY).openingTime(UPDATED_OPENING_TIME).closingTime(UPDATED_CLOSING_TIME);

        restBusinessHoursMockMvc
            .perform(
                put("/api/business-hours")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBusinessHours))
            )
            .andExpect(status().isOk());

        // Validate the BusinessHours in the database
        List<BusinessHours> businessHoursList = businessHoursRepository.findAll();
        assertThat(businessHoursList).hasSize(databaseSizeBeforeUpdate);
        BusinessHours testBusinessHours = businessHoursList.get(businessHoursList.size() - 1);
        assertThat(testBusinessHours.getDay()).isEqualTo(UPDATED_DAY);
        assertThat(testBusinessHours.getOpeningTime()).isEqualTo(UPDATED_OPENING_TIME);
        assertThat(testBusinessHours.getClosingTime()).isEqualTo(UPDATED_CLOSING_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingBusinessHours() throws Exception {
        int databaseSizeBeforeUpdate = businessHoursRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBusinessHoursMockMvc
            .perform(
                put("/api/business-hours").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(businessHours))
            )
            .andExpect(status().isBadRequest());

        // Validate the BusinessHours in the database
        List<BusinessHours> businessHoursList = businessHoursRepository.findAll();
        assertThat(businessHoursList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBusinessHours() throws Exception {
        // Initialize the database
        businessHoursRepository.saveAndFlush(businessHours);

        int databaseSizeBeforeDelete = businessHoursRepository.findAll().size();

        // Delete the businessHours
        restBusinessHoursMockMvc
            .perform(delete("/api/business-hours/{id}", businessHours.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BusinessHours> businessHoursList = businessHoursRepository.findAll();
        assertThat(businessHoursList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
