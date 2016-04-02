package com.spartid.server.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class MainControllerTest {
    private MockMvc mockMvc;

    @Configuration
    public static class ContextConfiguration {
        @Bean
        MainController mainController() {
            return new MainController(null, null, null, null, null);
        }

    }

    @Autowired
    MainController mainController;

    @Test
    public void testHome() {
        ApiRoot home = mainController.home();

        assertThat(home.getResult(), equalTo("ok"));
    }

}
