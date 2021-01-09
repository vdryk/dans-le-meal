package com.ach.danslemeal.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.ach.danslemeal.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class MysteriousOrderTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MysteriousOrder.class);
        MysteriousOrder mysteriousOrder1 = new MysteriousOrder();
        mysteriousOrder1.setId(1L);
        MysteriousOrder mysteriousOrder2 = new MysteriousOrder();
        mysteriousOrder2.setId(mysteriousOrder1.getId());
        assertThat(mysteriousOrder1).isEqualTo(mysteriousOrder2);
        mysteriousOrder2.setId(2L);
        assertThat(mysteriousOrder1).isNotEqualTo(mysteriousOrder2);
        mysteriousOrder1.setId(null);
        assertThat(mysteriousOrder1).isNotEqualTo(mysteriousOrder2);
    }
}
