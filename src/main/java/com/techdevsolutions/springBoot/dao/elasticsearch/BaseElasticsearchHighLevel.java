package com.techdevsolutions.springBoot.dao.elasticsearch;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BaseElasticsearchHighLevel {
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());
//    protected RestHighLevelClient client = null;
//    protected BulkProcessor bulkProcessor = null;
//    public static final long BULK_PROCESSOR_INTERVAL = 1;
    private String host = "localhost";

    public BaseElasticsearchHighLevel() {
    }

//    public BaseElasticsearchHighLevel(String host) {
//        this.setHost(host);
//    }

//    public RestHighLevelClient getClient() {
//        return this.getClient(this.getHost());
//    }
//
//    public RestHighLevelClient getClient(String host) {
//        return this.getClient(host, "9200");
//    }
//
//    public RestHighLevelClient getClient(String host, String port) {
//        if (this.client == null) {
//            this.client = new RestHighLevelClient(
//                    RestClient.builder(new HttpHost(host, Integer.valueOf(port), "http")));
//        }
//
//        return this.client;
//    }
//
//    public String getHost() {
//        return host;
//    }
//
//    public BaseElasticsearchHighLevel setHost(String host) {
//        this.host = host;
//        return this;
//    }
//
//    public BulkProcessor getBulkProcessor() {
//        return this.getBulkProcessor(this.getHost());
//    }
//
//    public BulkProcessor getBulkProcessor(String host) {
//        if (this.bulkProcessor == null) {
//            BulkProcessor.Listener listener = new BulkProcessor.Listener() {
//                @Override
//                public void beforeBulk(long executionId, BulkRequest request) {
//                    LOG.debug("beforeBulk... " +
//                            "items: " + request.numberOfActions() + ", " +
//                            "estimatedSizeInBytes: " + request.estimatedSizeInBytes()
//                    );
//                }
//
//                @Override
//                public void afterBulk(long executionId, BulkRequest request,
//                                      BulkResponse response) {
//                    LOG.debug("afterBulk... " +
//                            "response: " + response.getIngestTookInMillis() + " ms"
//                    );
//                }
//
//                @Override
//                public void afterBulk(long executionId, BulkRequest request,
//                                      Throwable failure) {
//                    LOG.error("afterBulk... failure: " + failure.toString());
//                }
//            };
//
//            BulkProcessor.Builder builder = BulkProcessor.builder((request, bulkListener) ->
//                            this.getClient(host).bulkAsync(request, RequestOptions.DEFAULT, bulkListener),
//                    listener);
//            builder.setBulkActions(10000);
//            builder.setBulkSize(new ByteSizeValue(5L, ByteSizeUnit.MB));
//            builder.setConcurrentRequests(1);
//            builder.setFlushInterval(TimeValue.timeValueSeconds(BaseElasticsearchHighLevel.BULK_PROCESSOR_INTERVAL));
//            builder.setBackoffPolicy(BackoffPolicy
//                    .constantBackoff(TimeValue.timeValueSeconds(1L), 3));
//            this.bulkProcessor = builder.build();
//        }
//
//        return this.bulkProcessor;
//    }
//
//    public BulkProcessor getBulkProcessor(Client client) {
//        BulkProcessor bulkProcessor = BulkProcessor.builder(client,
//                new BulkProcessor.Listener() {
//                    @Override
//                    public void beforeBulk(long executionId,
//                                           BulkRequest request) {
//
//                    }
//
//                    @Override
//                    public void afterBulk(long executionId,
//                                          BulkRequest request,
//                                          BulkResponse response) {
//
//                    }
//
//                    @Override
//                    public void afterBulk(long executionId,
//                                          BulkRequest request,
//                                          Throwable failure) {
//
//                    }
//                })
//                .setBulkActions(10000)
//                .setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
//                .setFlushInterval(TimeValue.timeValueSeconds(5))
//                .setConcurrentRequests(1)
//                .setBackoffPolicy(
//                        BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3))
//                .build();
//        return bulkProcessor;
//    }
//
//
//    public void createIndex(String index) throws IOException {
//        CreateIndexRequest request = new CreateIndexRequest(index);
//        this.getClient().indices().create(request, RequestOptions.DEFAULT);
//    }
//
//    public void deleteIndex(RestHighLevelClient client, String index) throws IOException {
//        DeleteIndexRequest request = new DeleteIndexRequest(index);
//        this.getClient().indices().delete(request, RequestOptions.DEFAULT);
//    }
//
//    public void deleteByQuery(DeleteByQueryRequest request) throws IOException {
//        this.getClient().deleteByQuery(request, RequestOptions.DEFAULT);
//    }
//
//
//    public AcknowledgedResponse createMapping(String index, String mapping) throws IOException {
//        PutMappingRequest request = new PutMappingRequest(index);
//        request.source(mapping, XContentType.JSON);
//        return this.getClient().indices().putMapping(request, RequestOptions.DEFAULT);
//    }
//
//
//
//    public GetResponse getDocument(String id, String index) throws Exception {
//        if (StringUtils.isEmpty(id)) {
//            throw new IllegalArgumentException("id is null or empty");
//        } else if (StringUtils.isEmpty(index)) {
//            throw new IllegalArgumentException("index is null or empty");
//        }
//
//        GetRequest request = new GetRequest(index, id);
//
//        try {
//            return this.getClient().get(request, RequestOptions.DEFAULT);
//        } catch (ElasticsearchException e) {
//            if (e.status() == RestStatus.NOT_FOUND) {
//                throw new Exception("Item not found with id: " + id);
//            } else {
//                throw new Exception("Error: " + e.getDetailedMessage());
//            }
//        }
//    }
//
//    public List<SearchHit> getDocuments(SearchRequest request) throws Exception {
//        try {
//            SearchResponse response = this.getClient().search(request, RequestOptions.DEFAULT);
//            SearchHits hits = response.getHits();
//            SearchHit[] searchHitsArray = hits.getHits();
//            return new ArrayList<>(Arrays.asList(searchHitsArray));
//        } catch (ElasticsearchException e) {
//            throw new Exception("Error: " + e.getDetailedMessage());
//        }
//    }
//
//    public List<SearchHit> getDocumentsWithScroll(SearchRequest request) throws Exception {
//        try {
//            SearchResponse response = this.getClient().search(request, RequestOptions.DEFAULT);
//            String scrollId = response.getScrollId();
//            SearchHits hits = response.getHits();
//            SearchHit[] searchHitsArray = hits.getHits();
//            List<SearchHit> items = new ArrayList<>(Arrays.asList(searchHitsArray));
//
//            while (searchHitsArray != null && searchHitsArray.length > 0) {
//                SearchScrollRequest scrollRequest = new SearchScrollRequest(scrollId);
//                scrollRequest.scroll(request.scroll());
//                response = this.getClient().scroll(scrollRequest, RequestOptions.DEFAULT);
//                scrollId = response.getScrollId();
//                searchHitsArray = response.getHits().getHits();
////                this.logger.info("Adding " + searchHitsArray.length + " to list");
//                items.addAll(Arrays.asList(searchHitsArray));
//            }
//
//            if (StringUtils.isNotEmpty(scrollId)) {
//                ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
//                clearScrollRequest.addScrollId(scrollId);
//
//                try {
//                    this.getClient().clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
//                } catch (ElasticsearchException e) {
//                    throw new Exception("Error clearing scroll: " + e.getDetailedMessage());
//                }
//            }
//
//            return items;
//        } catch (ElasticsearchException e) {
//            throw new Exception("Error: " + e.getDetailedMessage());
//        }
//    }
//
//    public void createDocument(String objectAsString, String id, String index) throws Exception {
//        IndexRequest request = new IndexRequest(index);
//        request.id(id);
//        request.source(objectAsString, XContentType.JSON);
//        IndexResponse response = this.getClient().index(request, RequestOptions.DEFAULT);
//
//        if (!response.status().toString().equalsIgnoreCase("CREATED") &&
//                !response.status().toString().equalsIgnoreCase("OK")) {
//            throw new Exception("Unable to create item: " + response.status());
//        }
//    }
//
//    public String createDocument(String objectAsString, String index) throws Exception {
//        IndexRequest request = new IndexRequest(index, "_doc");
//        request.source(objectAsString, XContentType.JSON);
//        IndexResponse response = this.getClient().index(request, RequestOptions.DEFAULT);
//
//        if (!response.status().toString().equalsIgnoreCase("CREATED") &&
//                !response.status().toString().equalsIgnoreCase("OK")) {
//            throw new Exception("Unable to create item: " + response.status());
//        }
//
//        return response.getId();
//    }
//
//    public void deleteDocument(String id, String index) throws Exception {
//        DeleteRequest request = new DeleteRequest(index);
//        request.id(id);
//        DeleteResponse response = this.getClient().delete(request, RequestOptions.DEFAULT);
//
//        if (!response.status().toString().equalsIgnoreCase("OK")) {
//            throw new Exception("Unable to delete item by id " + id + ": " + response.status());
//        }
//    }
//
//    public String updateDocument(String objectAsString, String id, String index) throws Exception {
//        UpdateRequest request = new UpdateRequest(index, id)
//                .index(index)
//                .id(id)
//                .doc(objectAsString, XContentType.JSON);
//        UpdateResponse response = this.getClient().update(request, RequestOptions.DEFAULT);
//
//        if (!response.status().toString().equalsIgnoreCase("CREATED") &&
//                !response.status().toString().equalsIgnoreCase("OK")) {
//            throw new Exception("Unable to create item: " + response.status());
//        }
//
//        return response.getId();
//    }

}

