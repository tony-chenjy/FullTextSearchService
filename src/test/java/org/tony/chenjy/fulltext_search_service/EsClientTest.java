package org.tony.chenjy.fulltext_search_service;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EsClientTest {

    private static final String HOST_NAME = "139.162.67.165";
    private static final int PORT = 9200;
    private static final String SCHEMA = "http";

    RestHighLevelClient client = null;

    @Test
    public void index() {
        init();
        // String jsonString
        // Map<String, Object> jsonMap
        // XContentBuilder builder
        // Object key-pairs
        IndexRequest request = new IndexRequest(
                "posts",
                "doc");
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        request.source(jsonString, XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse); // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        destroy();
    }

    @Test
    public void indexBuilder() {
        init();
        // String jsonString
        // Map<String, Object> jsonMap
        // XContentBuilder builder
        // Object key-pairs
        try {
            IndexRequest request = new IndexRequest("posts", "doc");
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            builder.endObject();
            request.source(builder);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse); // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        destroy();
    }

    private void init() {
        client = new RestHighLevelClient(RestClient.builder(new HttpHost(HOST_NAME, PORT, SCHEMA)));
    }

    private void destroy() {
        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(HOST_NAME, PORT, SCHEMA)));

        // TODO

        try {
            if (client != null) {
                client.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
