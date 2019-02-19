package org.tony.chenjy.fulltext_search_service.dev.service;

import com.carrotsearch.hppc.cursors.ObjectCursor;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.springframework.stereotype.Service;
import org.tony.chenjy.fulltext_search_service.dev.dto.EsIndexDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EsIndexService {

    @Resource
    private RestHighLevelClient client;

    // 创建索引
    public void createIndex(String index, String type, Map<String, Object> properties) {
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

    // 获取所有索引
    public List<EsIndexDTO> allIndices() {
        List<EsIndexDTO> list = new ArrayList<>();
        try {
            GetIndexRequest request = new GetIndexRequest().indices("*");
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.getMappings();
            if (allMappings.isEmpty()) {
                return list;
            }
            for (ObjectCursor<String> indexItem : allMappings.keys()) {
                ImmutableOpenMap<String, MappingMetaData> typeMappings = allMappings.get(indexItem.value);
                for (ObjectCursor<String> typeItem : typeMappings.keys()) {
                    list.add(new EsIndexDTO(indexItem.value, typeItem.value, typeMappings.get(typeItem.value).getSourceAsMap()));
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 获取所有索引（简单）
    public List<EsIndexDTO> allSimpleIndices() {
        List<EsIndexDTO> list = new ArrayList<>();
        try {
            GetIndexRequest request = new GetIndexRequest().indices("*");
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.getMappings();
            if (allMappings.isEmpty()) {
                return list;
            }
            for (ObjectCursor<String> indexItem : allMappings.keys()) {
                ImmutableOpenMap<String, MappingMetaData> typeMappings = allMappings.get(indexItem.value);
                for (ObjectCursor<String> typeItem : typeMappings.keys()) {
                    list.add(new EsIndexDTO(indexItem.value, typeItem.value));
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

//
//    // 查找映射
//    public List<EsMappingDTO> searchMappings(String index, String type) {
//        List<EsMappingDTO> list = new ArrayList();
//        try {
//            GetMappingsRequest request = new GetMappingsRequest().indices(index).types(type);
//            GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
//            System.out.println(response); // TODO
//
//            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.mappings();
//            if (allMappings.isEmpty()) {
//                return list;
//            }
//            for (ObjectCursor<String> indexItem : allMappings.keys()) {
//                ImmutableOpenMap<String, MappingMetaData> typeMappings = allMappings.get(indexItem.value);
//                for (ObjectCursor<String> typeItem : typeMappings.keys()) {
//                    list.add(new EsMappingDTO(indexItem.value, typeItem.value, typeMappings.get(typeItem.value).sourceAsMap()));
//                }
//            }
//            return list;
//        } catch (ElasticsearchException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return list;
//    }

    // 获取索引
    public EsIndexDTO getIndex(String index, String type) {
        EsIndexDTO result = new EsIndexDTO();
        try {
            GetIndexRequest request = new GetIndexRequest().indices(index).types(type);
            GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.getMappings();
            ImmutableOpenMap<String, MappingMetaData> indexMapping = allMappings.get(index);
            if (indexMapping == null || indexMapping.isEmpty()) {
                return result;
            }

            MappingMetaData typeMapping = indexMapping.get(type);
            if (typeMapping == null) {
                return result;
            }

            result.setIndex(index);
            result.setType(type);
            result.setMapping(typeMapping.sourceAsMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
