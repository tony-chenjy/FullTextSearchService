package org.tony.chenjy.fulltext_search_service.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsDocumentDTO extends EsBaseDTO{
    private String id;
    private Long version;

    public EsDocumentDTO(String index, String type, String id, Long version) {
        super(index, type);
        this.id = id;
        this.version = version;
    }
}
