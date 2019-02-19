package org.tony.chenjy.fulltext_search_service.dev.service;

import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EsDocumentService {

    @Resource
    private RestHighLevelClient client;

    // 创建全文
    public void createDocument(String index, String type, Map<String, Object> properties) {
        try {
            Map<String, Object> mapping = new HashMap<>();
            mapping.put(type, properties);

            CreateIndexRequest request = new CreateIndexRequest(index).mapping(type, mapping);
            CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            // TODO
            boolean acknowledged = response.isAcknowledged();
            System.out.println(acknowledged); // TODO
            boolean shardsAcknowledged = response.isShardsAcknowledged();
            System.out.println(shardsAcknowledged); // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取所有全文
    public void listIndices(String index) {
        try {
            SearchRequest request = new SearchRequest().indices(index);
            SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
