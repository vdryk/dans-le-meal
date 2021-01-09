package com.ach.danslemeal.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ach.danslemeal.DansLeMealApp;
import com.ach.danslemeal.domain.MysteriousOrder;
import com.ach.danslemeal.domain.enumeration.Status;
import com.ach.danslemeal.repository.MysteriousOrderRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link MysteriousOrderResource} REST controller.
 */
@SpringBootTest(classes = DansLeMealApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MysteriousOrderResourceIT {
    private static final Status DEFAULT_STATUS = Status.PENDING;
    private static final Status UPDATED_STATUS = Status.VALIDATED;

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MysteriousOrderRepository mysteriousOrderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMysteriousOrderMockMvc;

    private MysteriousOrder mysteriousOrder;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MysteriousOrder createEntity(EntityManager em) {
        MysteriousOrder mysteriousOrder = new MysteriousOrder().status(DEFAULT_STATUS).date(DEFAULT_DATE);
        return mysteriousOrder;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MysteriousOrder createUpdatedEntity(EntityManager em) {
        MysteriousOrder mysteriousOrder = new MysteriousOrder().status(UPDATED_STATUS).date(UPDATED_DATE);
        return mysteriousOrder;
    }

    @BeforeEach
    public void initTest() {
        mysteriousOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createMysteriousOrder() throws Exception {
        int databaseSizeBeforeCreate = mysteriousOrderRepository.findAll().size();
        // Create the MysteriousOrder
        restMysteriousOrderMockMvc
            .perform(
                post("/api/mysterious-orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mysteriousOrder))
            )
            .andExpect(status().isCreated());

        // Validate the MysteriousOrder in the database
        List<MysteriousOrder> mysteriousOrderList = mysteriousOrderRepository.findAll();
        assertThat(mysteriousOrderList).hasSize(databaseSizeBeforeCreate + 1);
        MysteriousOrder testMysteriousOrder = mysteriousOrderList.get(mysteriousOrderList.size() - 1);
        assertThat(testMysteriousOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testMysteriousOrder.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createMysteriousOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mysteriousOrderRepository.findAll().size();

        // Create the MysteriousOrder with an existing ID
        mysteriousOrder.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMysteriousOrderMockMvc
            .perform(
                post("/api/mysterious-orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mysteriousOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the MysteriousOrder in the database
        List<MysteriousOrder> mysteriousOrderList = mysteriousOrderRepository.findAll();
        assertThat(mysteriousOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMysteriousOrders() throws Exception {
        // Initialize the database
        mysteriousOrderRepository.saveAndFlush(mysteriousOrder);

        // Get all the mysteriousOrderList
        restMysteriousOrderMockMvc
            .perform(get("/api/mysterious-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mysteriousOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMysteriousOrder() throws Exception {
        // Initialize the database
        mysteriousOrderRepository.saveAndFlush(mysteriousOrder);

        // Get the mysteriousOrder
        restMysteriousOrderMockMvc
            .perform(get("/api/mysterious-orders/{id}", mysteriousOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(mysteriousOrder.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMysteriousOrder() throws Exception {
        // Get the mysteriousOrder
        restMysteriousOrderMockMvc.perform(get("/api/mysterious-orders/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMysteriousOrder() throws Exception {
        // Initialize the database
        mysteriousOrderRepository.saveAndFlush(mysteriousOrder);

        int databaseSizeBeforeUpdate = mysteriousOrderRepository.findAll().size();

        // Update the mysteriousOrder
        MysteriousOrder updatedMysteriousOrder = mysteriousOrderRepository.findById(mysteriousOrder.getId()).get();
        // Disconnect from session so that the updates on updatedMysteriousOrder are not directly saved in db
        em.detach(updatedMysteriousOrder);
        updatedMysteriousOrder.status(UPDATED_STATUS).date(UPDATED_DATE);

        restMysteriousOrderMockMvc
            .perform(
                put("/api/mysterious-orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedMysteriousOrder))
            )
            .andExpect(status().isOk());

        // Validate the MysteriousOrder in the database
        List<MysteriousOrder> mysteriousOrderList = mysteriousOrderRepository.findAll();
        assertThat(mysteriousOrderList).hasSize(databaseSizeBeforeUpdate);
        MysteriousOrder testMysteriousOrder = mysteriousOrderList.get(mysteriousOrderList.size() - 1);
        assertThat(testMysteriousOrder.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testMysteriousOrder.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMysteriousOrder() throws Exception {
        int databaseSizeBeforeUpdate = mysteriousOrderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMysteriousOrderMockMvc
            .perform(
                put("/api/mysterious-orders")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(mysteriousOrder))
            )
            .andExpect(status().isBadRequest());

        // Validate the MysteriousOrder in the database
        List<MysteriousOrder> mysteriousOrderList = mysteriousOrderRepository.findAll();
        assertThat(mysteriousOrderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMysteriousOrder() throws Exception {
        // Initialize the database
        mysteriousOrderRepository.saveAndFlush(mysteriousOrder);

        int databaseSizeBeforeDelete = mysteriousOrderRepository.findAll().size();

        // Delete the mysteriousOrder
        restMysteriousOrderMockMvc
            .perform(delete("/api/mysterious-orders/{id}", mysteriousOrder.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MysteriousOrder> mysteriousOrderList = mysteriousOrderRepository.findAll();
        assertThat(mysteriousOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
