package org.tony.chenjy.fulltext_search_service.dev.api;

import org.springframework.web.bind.annotation.*;
import org.tony.chenjy.fulltext_search_service.base.property.EsProperty;
import org.tony.chenjy.fulltext_search_service.dev.dto.EsDocumentDTO;
import org.tony.chenjy.fulltext_search_service.dev.service.EsDocumentService;
import org.tony.chenjy.fulltext_search_service.etc.bean.AjaxJson;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("es-doc")
public class EsDocumentServiceApi {

    @Resource
    private EsDocumentService esDocumentService;

    @PostMapping(value = "{index}")
    public AjaxJson postDocument(@PathVariable String index,
                                 @RequestBody Map<String, Object> document) {
        EsDocumentDTO data = this.esDocumentService.addDocument(index, EsProperty.TYPE, document);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
    }
}
