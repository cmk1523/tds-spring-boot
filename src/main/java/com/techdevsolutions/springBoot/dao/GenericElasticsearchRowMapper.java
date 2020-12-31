package com.techdevsolutions.springBoot.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.techdevsolutions.common.beans.elasticsearchCommonSchema.Event;
import com.techdevsolutions.common.service.core.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

public class GenericElasticsearchRowMapper {
    private ObjectMapper objectMapper = new ObjectMapper();

    public GenericElasticsearchRowMapper() {
        this.objectMapper.registerModule(new Jdk8Module());
    }

    public Map fromJson(String json) throws IOException, ParseException {
        return this.fromJson(json, "");
    }

    public Map fromJson(String json, String id) throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtils.ISO_STRING);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

        Map<String, Object> map = this.objectMapper.readValue(json, Map.class);

        if (map.get("id") == null) {
            map.put("id", id);
        }

        return this.objectMapper.convertValue(map, Map.class);
    }

    public String toJson(Map object) throws JsonProcessingException {
        try {
            return this.objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
