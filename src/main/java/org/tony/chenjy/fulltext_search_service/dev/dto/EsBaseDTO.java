package org.tony.chenjy.fulltext_search_service.dev.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EsBaseDTO {
    private String index;
    private String type;
}
