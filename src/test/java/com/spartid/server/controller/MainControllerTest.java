package com.spartid.server.controller;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class MainControllerTest {

    @Test
    public void testHome() {
        MainController mainController = new MainController(null);

        Map<String, Object> home = mainController.home();

        assertEquals("ok", home.get("result"));
    }

}
