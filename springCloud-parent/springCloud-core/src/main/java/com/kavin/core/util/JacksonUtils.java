package com.kavin.core.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
public class JacksonUtils {


	private static ObjectMapper objectMapper = new ObjectMapper();

	static {
		objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		objectMapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		objectMapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		objectMapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
			public void serialize(Object value, JsonGenerator jsonGenerator, SerializerProvider provider)
					throws IOException, JsonProcessingException {
				jsonGenerator.writeString("");
			}
		});
	}

	public static Map<String, Object> getMapFromJson(String json)
			throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(json, new TypeReference<Map<String, Object>>() {
		});
	}

	public static String getJsonFromMap(Map<String, Object> map) throws JsonProcessingException {
		return objectMapper.writeValueAsString(map);
	}
	
	public static String getJsonFromObject(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

	

}
