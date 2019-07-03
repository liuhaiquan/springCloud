package com.kavin.core.data;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.kavin.core.util.JacksonUtils;

import java.io.IOException;

public class DataObject {

	private Map<String, Object> map;

	public DataObject() {
		this.map = new HashMap<String, Object>();
	}

	public DataObject(Map<String, Object> map) {
		if (map == null) {
			map = new HashMap<String, Object>();
		}
		this.map = map;
	}

	public Map<String, Object> getMap() {
		return map;
	}
	
	public String getJson() throws JsonGenerationException,
		JsonMappingException, IOException {
		return JacksonUtils.getJsonFromMap(this.getMap());
	}
}
