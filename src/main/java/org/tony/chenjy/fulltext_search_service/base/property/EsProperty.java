package org.tony.chenjy.fulltext_search_service.base.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EsProperty {

    @Value("${elasticsearch.rest.uris}")
    private String[] uris;

}
