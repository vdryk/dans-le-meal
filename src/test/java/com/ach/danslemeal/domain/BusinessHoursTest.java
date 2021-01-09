package com.ach.danslemeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ach.danslemeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class BusinessHoursTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BusinessHours.class);
        BusinessHours businessHours1 = new BusinessHours();
        businessHours1.setId(1L);
        BusinessHours businessHours2 = new BusinessHours();
        businessHours2.setId(businessHours1.getId());
        assertThat(businessHours1).isEqualTo(businessHours2);
        businessHours2.setId(2L);
        assertThat(businessHours1).isNotEqualTo(businessHours2);
        businessHours1.setId(null);
        assertThat(businessHours1).isNotEqualTo(businessHours2);
    }
}
