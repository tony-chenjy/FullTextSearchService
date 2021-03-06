package org.tony.chenjy.fulltext_search_service.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsMappingDTO {
    private String index;
    private String type;
    private Map<String, Object> fields;

    public EsMappingDTO(String index, String type) {
        this.index = index;
        this.type = type;
    }
}
