package org.tony.chenjy.fulltext_search_service.dev.service;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;
import org.tony.chenjy.fulltext_search_service.dev.dto.EsDocumentDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
public class EsDocumentService {

    @Resource
    private RestHighLevelClient client;

    // 创建全文索引
    public EsDocumentDTO addDocument(String index, String type, Map<String, Object> document) {
        EsDocumentDTO dto = null;
        try {
            IndexRequest request = new IndexRequest(index, type).source(document);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            dto = new EsDocumentDTO(response.getIndex(), response.getType(), response.getId(), response.getVersion());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dto;
    }
}
