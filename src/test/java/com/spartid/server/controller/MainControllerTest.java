package com.spartid.server.controller;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Test;

import com.spartid.server.controller.MainController;

public class MainControllerTest {

	@Test
	public void testHome() {
		MainController mainController = new MainController();
		
		Map<String, Object> home = mainController.home();
		
		assertEquals("ok", home.get("result"));
	}

}
