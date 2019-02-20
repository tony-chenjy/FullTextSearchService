package org.tony.chenjy.fulltext_search_service.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsIndexDTO extends EsBaseDTO {
    private Map<String, Object> mapping;

    public EsIndexDTO(String index, String type) {
        super(index, type);
    }

    public EsIndexDTO(String index, String type, Map<String, Object> mapping) {
        super(index, type);
        this.mapping = mapping;
    }
}
