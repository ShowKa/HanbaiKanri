package com.shoka;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DomainConfig {
	private String domain;
	private String builderBaseClass;
	private ArrayList<HashMap<String, String>> members;
}
