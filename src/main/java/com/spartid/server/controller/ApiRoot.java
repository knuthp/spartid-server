package com.spartid.server.controller;

import org.springframework.hateoas.ResourceSupport;

public class ApiRoot extends ResourceSupport {
	private String result = "ok";

	public String getResult() {
		return result;
	}
}
