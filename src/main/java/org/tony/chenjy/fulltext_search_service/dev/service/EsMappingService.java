package org.tony.chenjy.fulltext_search_service.dev.service;

import com.carrotsearch.hppc.cursors.ObjectCursor;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.springframework.stereotype.Service;
import org.tony.chenjy.fulltext_search_service.dev.dto.EsMappingDTO;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class EsMappingService {

    @Resource
    private RestHighLevelClient client;

    // 创建映射
    public void putMapping(String index, String type, Map<String, Object> source) {
        try {
            // TODO 判断索引是否存在
            // 创建映射
            PutMappingRequest request = new PutMappingRequest(index).type(type).source(source);
            AcknowledgedResponse response = client.indices().putMapping(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            // TODO
            boolean acknowledged = response.isAcknowledged();
            System.out.println(acknowledged); // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取所有映射
    public List<EsMappingDTO> allMappings() {
        List<EsMappingDTO> list = new ArrayList();
        try {
            GetMappingsRequest request = new GetMappingsRequest();
            GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.mappings();
            if (allMappings.isEmpty()) {
                return list;
            }
            for (ObjectCursor<String> indexItem : allMappings.keys()) {
                ImmutableOpenMap<String, MappingMetaData> typeMappings = allMappings.get(indexItem.value);
                for (ObjectCursor<String> typeItem : typeMappings.keys()) {
                    // TODO fields
                    list.add(new EsMappingDTO(indexItem.value, typeItem.value));
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 查找映射
    public List<EsMappingDTO> searchMappings(String index, String type) {
        List<EsMappingDTO> list = new ArrayList();
        try {
            GetMappingsRequest request = new GetMappingsRequest().indices(index).types(type);
            GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.mappings();
            if (allMappings.isEmpty()) {
                return list;
            }
            for (ObjectCursor<String> indexItem : allMappings.keys()) {
                ImmutableOpenMap<String, MappingMetaData> typeMappings = allMappings.get(indexItem.value);
                for (ObjectCursor<String> typeItem : typeMappings.keys()) {
                    list.add(new EsMappingDTO(indexItem.value, typeItem.value, typeMappings.get(typeItem.value).sourceAsMap()));
                }
            }
            return list;
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 获取映射
    public EsMappingDTO getMapping(String index, String type) {
        EsMappingDTO result = new EsMappingDTO();
        try {
            GetMappingsRequest request = new GetMappingsRequest().indices(index).types(type);
            GetMappingsResponse response = client.indices().getMapping(request, RequestOptions.DEFAULT);
            System.out.println(response); // TODO

            ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = response.mappings();
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
            result.setFields(typeMapping.sourceAsMap());
        } catch (ElasticsearchException e) {
            e.printStackTrace();
            // TODO
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
