package org.tony.chenjy.fulltext_search_service.dev.service;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.get.GetResult;
import org.elasticsearch.rest.RestStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

@Service
public class EsService {

    @Resource
    private RestHighLevelClient client;

    // 创建索引
    public void postIndex(String index, String type, Map<String, Object> jsonMap) {
        try {
            IndexRequest request = new IndexRequest(index, type).source(jsonMap);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            // TODO
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                // TODO version conflict error
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 判断索引是否存在
    public void isExists(String index, String type, String id) {
        try {
            GetRequest request = new GetRequest(index, type, id);
            boolean exists = client.exists(request, RequestOptions.DEFAULT);
            System.out.println(exists); // TODO

            if (!exists) {
                // TODO
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 列举索引
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

    // 获取索引
    public GetResponse getIndex(String index, String type, String id) {
        try {
            GetRequest request = new GetRequest(index, type, id);
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            if (response.isExists()) {
                return response;
            } else {
                // TODO
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                // TODO index does not exist
            }
            if (e.status() == RestStatus.CONFLICT) {
                // TODO version conflict error
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 更新索引
    public GetResult updateIndex(String index, String type, String id, Map<String, Object> jsonMap) {
        try {
            UpdateRequest request = new UpdateRequest(index, type, id).doc(jsonMap);
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO
            GetResult result = response.getGetResult();
            System.out.println(result); // TODO

            if (result.isExists()) {
                return result;
            } else {
                // TODO
            }
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                // TODO document not exist
            }
            if (e.status() == RestStatus.CONFLICT) {
                // TODO version conflict error
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 删除索引
    public void deleteIndex(String index, String type, String id) {
        try {
            DeleteRequest request = new DeleteRequest(index, type, id);
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            if (response.getResult() == DocWriteResponse.Result.NOT_FOUND) {
                // TODO the document to be deleted was not found
            }
        } catch (ElasticsearchException exception) {
            if (exception.status() == RestStatus.CONFLICT) {
                // TODO version conflict error
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
