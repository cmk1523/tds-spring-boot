package com.techdevsolutions.springBoot.dao.elasticsearch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techdevsolutions.springBoot.beans.Search;
import com.techdevsolutions.springBoot.dao.DaoCrudInterface;
import com.techdevsolutions.springBoot.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchModule;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.*;
import java.util.stream.Collectors;

//@Service
public class ElasticsearchGenericDaoImpl { // implements DaoCrudInterface<Map> {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());

    private String INDEX_BASE_NAME = "generic";
    private String ES_HOST = "localhost";
    private String ES_PROTOCOL = "http";
    private Integer ES_PORT = 9200;
    private String ES_USERNAME = "elastic";
    private String ES_PASSWORD = "password";
    private String ES_CERT_PATH = "";

    private CustomBaseElasticsearchHighLevel dao;
    private ObjectMapper objectMapper = new ObjectMapper();
    public final GenericElasticsearchRowMapper rowMapper = new GenericElasticsearchRowMapper();

//    @Autowired
//    public ElasticsearchGenericDaoImpl(Environment environment) {
//        this.dao = new CustomBaseElasticsearchHighLevel("localhost");
//
//        if (environment != null) {
//            String host = environment.getProperty("elasticsearch.host");
//
//            if (StringUtils.isNotEmpty(host)) {
//                this.ES_HOST = host;
//                this.LOG.debug("Setting ES_HOST: " + this.ES_HOST);
//            }
//
//            String port = environment.getProperty("elasticsearch.port");
//
//            if (StringUtils.isNotEmpty(host)) {
//                this.ES_PORT = Integer.valueOf(port);
//                this.LOG.debug("Setting ES_PORT: " + this.ES_PORT);
//            }
//
//            String protocol = environment.getProperty("elasticsearch.protocol");
//
//            if (StringUtils.isNotEmpty(protocol)) {
//                this.ES_PROTOCOL = protocol;
//                this.LOG.debug("Setting ES_PROTOCOL: " + this.ES_PROTOCOL);
//            }
//
//            String username = environment.getProperty("elasticsearch.username");
//
//            if (StringUtils.isNotEmpty(username)) {
//                this.ES_USERNAME = username;
//                this.LOG.debug("Setting ES_USERNAME: " + this.ES_USERNAME);
//            }
//
//            String password = environment.getProperty("elasticsearch.password");
//
//            if (StringUtils.isNotEmpty(password)) {
//                this.ES_PASSWORD = password;
//                this.LOG.debug("Setting ES_PASSWORD: REDACTED");
//            }
//
//            String certificatePath = environment.getProperty("elasticsearch.certificatePath");
//
//            if (StringUtils.isNotEmpty(certificatePath)) {
//                this.ES_CERT_PATH = certificatePath;
//                this.LOG.debug("Setting ES_CERT_PATH: " + this.ES_CERT_PATH);
//            }
//
//            this.dao.getClient(this.ES_HOST, this.ES_PORT, this.ES_PROTOCOL, this.ES_CERT_PATH,
//                    this.ES_USERNAME, this.ES_PASSWORD);
//        }
//    }

//    @Override
//    public List<Map> search(Search search) throws Exception {
//        String query = "{\n" +
//                "  \"query\": {\n" +
//                "    \"match_all\": {}\n" +
//                "  },\n" +
//                "  \"sort\": [\n" +
//                "    {\n" +
//                "      \"" + search.getSort() + "\": {\n" +
//                "        \"order\": \"desc\"\n" +
//                "      }\n" +
//                "    }\n" +
//                "  ],\n" +
//                "  \"size\": " + search.getSize() + "\n" +
//                "}";
//
//        LOG.debug("Elasticsearch query: " + query);
//
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        SearchModule searchModule = new SearchModule(Settings.EMPTY, false, Collections.emptyList());
//
//        try {
//            NamedXContentRegistry namedXContentRegistry = new NamedXContentRegistry(searchModule.getNamedXContents());
//            XContent xContent = XContentFactory.xContent(XContentType.JSON);
//            XContentParser parser = xContent.createParser(namedXContentRegistry,
//                    DeprecationHandler.THROW_UNSUPPORTED_OPERATION, query);
//            searchSourceBuilder.parseXContent(parser);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        SearchRequest searchRequest = new SearchRequest(this.INDEX_BASE_NAME);
//        searchRequest.source(searchSourceBuilder);
//        searchRequest.scroll(TimeValue.timeValueMinutes(1L));
//
//        List<SearchHit> results = this.dao.getDocumentsWithScroll(searchRequest);
//        return results.stream().map((i) -> {
//            try {
//                Map item = this.rowMapper.fromJson(i.getSourceAsString());
//                item.put("id", i.getId());
//                return item;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }).filter(Objects::nonNull).collect(Collectors.toList());
//    }
//
//    @Override
//    public Map get(String id) throws Exception {
//        GetResponse i = this.dao.getDocument(id, this.INDEX_BASE_NAME);
//        Map item = this.rowMapper.fromJson(i.getSourceAsString());
//        item.put("id", id);
//        return item;
//    }
//
//    @Override
//    public Map create(Map i) throws Exception {
//        String id = (String) i.get("id");
//        Map copy = this.rowMapper.fromJson(this.rowMapper.toJson(i));
//
//        if (copy.get("@timestamp") == null) {
//            copy.put("@timestamp", DateUtils.DateToISO(new Date()));
//        }
//
//        if (StringUtils.isEmpty(id)) {
//            id = UUID.randomUUID().toString();
//            copy.put("id", id);
//        }
//
//        String itemAsJson = this.rowMapper.toJson(copy);
//
//        this.dao.createDocument(itemAsJson, id, this.INDEX_BASE_NAME);
//        return this.get(id);
//    }
//
//    @Override
//    public void remove(String id) throws Exception {
//        this.dao.deleteDocument(id, this.INDEX_BASE_NAME);
//    }
//
//    @Override
//    public void delete(String id) throws Exception {
//        this.dao.deleteDocument(id, this.INDEX_BASE_NAME);
//    }
//
//    @Override
//    public Map update(Map i) throws Exception {
//        String itemAsJson = this.rowMapper.toJson(i);
//        String id = (String) i.get("id");
//        this.dao.updateDocument(itemAsJson, id, this.INDEX_BASE_NAME);
//        return this.get(id);
//    }
//
//    @Override
//    public Boolean verifyRemoval(String s) throws Exception {
//        return null;
//    }
//
//    @Override
//    public void install() throws Exception {
//        this.dao.createIndex(this.INDEX_BASE_NAME);
//
//        String mapping = "{\n" +
//                "  \"properties\" : {\n" +
//                "    \"@timestamp\" : {\n" +
//                "      \"type\" : \"date\"\n" +
//                "    }\n" +
//                "  }\n" +
//                "}";
//
//        LOG.debug("Elasticsearch mapping: " + mapping);
//
//        this.dao.createMapping(this.INDEX_BASE_NAME, mapping);
//    }
}
