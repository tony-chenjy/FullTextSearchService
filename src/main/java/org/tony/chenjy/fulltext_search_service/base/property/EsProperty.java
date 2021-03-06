package org.tony.chenjy.fulltext_search_service.base.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class EsProperty {

    public static final String TYPE = "_doc";

    @Value("${elasticsearch.rest.uris}")
    private String[] uris;

}
