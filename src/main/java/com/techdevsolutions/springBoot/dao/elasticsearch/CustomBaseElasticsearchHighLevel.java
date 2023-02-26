package com.techdevsolutions.springBoot.dao.elasticsearch;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomBaseElasticsearchHighLevel extends BaseElasticsearchHighLevel {
    private Logger LOG = LoggerFactory.getLogger(this.getClass());
    private String certificatePath = "";
    private String ES_HOST = "localhost";
    private String ES_PROTOCOL = "http";
    private Integer ES_PORT = 9200;
    private String ES_USERNAME = "elastic";
    private String ES_PASSWORD = "password";
    private String ES_CERT_PATH = "";

    public CustomBaseElasticsearchHighLevel() {
    }

//    public CustomBaseElasticsearchHighLevel(String host) {
//        super(host);
//    }
//
//    public CustomBaseElasticsearchHighLevel(String host, String certificatePath) {
//        super(host);
//        this.certificatePath = certificatePath;
//    }
//
//    @Override
//    public RestHighLevelClient getClient() {
//        return this.getClient(this.ES_HOST, this.ES_PORT, this.ES_PROTOCOL, this.ES_CERT_PATH,
//                this.ES_USERNAME, this.ES_PASSWORD);
//    }
//
//    @Override
//    public RestHighLevelClient getClient(String host) {
//        return this.getClient(this.ES_HOST, this.ES_PORT, this.ES_PROTOCOL, this.ES_CERT_PATH,
//                this.ES_USERNAME, this.ES_PASSWORD);
//    }
//
//    @Override
//    public RestHighLevelClient getClient(String host, String port) {
//        return this.getClient(this.ES_HOST, this.ES_PORT, this.ES_PROTOCOL, this.ES_CERT_PATH,
//                this.ES_USERNAME, this.ES_PASSWORD);
//    }
//
//    @Override
//    public List<SearchHit> getDocuments(SearchRequest request) throws Exception {
//        try {
//            RestHighLevelClient client = this.getClient();
//
//            if (client != null) {
//                SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//                SearchHits hits = response.getHits();
//                SearchHit[] searchHitsArray = hits.getHits();
//                return new ArrayList<>(Arrays.asList(searchHitsArray));
//            } else {
//                throw new Exception("Error: Unable to get client");
//            }
//        } catch (ElasticsearchException e) {
//            LOG.error("Unable to get documents");
//            throw new Exception(e);
//        }
//    }
//
//    public RestHighLevelClient getClient(String host, Integer port, String protocol, String certificatePath,
//                                         String username, String password) {
//        if (this.client == null) {
//            try {
//                this.ES_HOST = host;
//                this.ES_PORT = port;
//                this.ES_PROTOCOL = protocol;
//                this.ES_CERT_PATH = certificatePath;
//                this.ES_USERNAME = username;
//                this.ES_PASSWORD = password;
//
//                final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//                credentialsProvider.setCredentials(AuthScope.ANY,
//                        new UsernamePasswordCredentials(username, password));
//
//                Path caCertificatePath = Paths.get(certificatePath);
//                CertificateFactory factory = CertificateFactory.getInstance("X.509");
//                Certificate trustedCa;
//
//                try (InputStream is = Files.newInputStream(caCertificatePath)) {
//                    trustedCa = factory.generateCertificate(is);
//                }
//
//                KeyStore trustStore = KeyStore.getInstance("pkcs12");
//                trustStore.load(null, null);
//                trustStore.setCertificateEntry("ca", trustedCa);
//
//                SSLContextBuilder sslContextBuilder = SSLContexts.custom()
//                        .loadTrustMaterial(trustStore, new TrustSelfSignedStrategy());
//
//                final SSLContext sslContext = sslContextBuilder.build();
//                RestClientBuilder restClientBuilder = RestClient.builder(
//                        new HttpHost(host, port, protocol))
//                        .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
//                            @Override
//                            public HttpAsyncClientBuilder customizeHttpClient(
//                                    HttpAsyncClientBuilder httpClientBuilder) {
//                                return httpClientBuilder
//                                        .setDefaultCredentialsProvider(credentialsProvider)
//                                        .setSSLContext(sslContext);
//                            }
//                        });
//                this.client = new RestHighLevelClient(restClientBuilder);
//            } catch (Exception e) {
//                LOG.error("Unable to start a secure Elasticsearch High Level Client");
//                e.printStackTrace();
//            }
//        }
//
//        return this.client;
//    }
}
