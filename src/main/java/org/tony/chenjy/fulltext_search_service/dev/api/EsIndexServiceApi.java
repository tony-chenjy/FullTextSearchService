package org.tony.chenjy.fulltext_search_service.dev.api;

import org.springframework.web.bind.annotation.*;
import org.tony.chenjy.fulltext_search_service.dev.dto.EsIndexDTO;
import org.tony.chenjy.fulltext_search_service.dev.service.EsIndexService;
import org.tony.chenjy.fulltext_search_service.etc.bean.AjaxJson;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("es-index")
public class EsIndexServiceApi {

    @Resource
    private EsIndexService esIndexService;

    @PostMapping(value = "{index}/{type}")
    public AjaxJson postIndex(@PathVariable String index,
                              @PathVariable String type,
                              @RequestBody Map<String, Object> properties) {
        this.esIndexService.createIndex(index, type, properties);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, null);
    }

    @DeleteMapping(value = "{index}")
    public AjaxJson deleteIndex(@PathVariable String index) {
        boolean data = this.esIndexService.deleteIndex(index);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
    }

    @GetMapping(value = "all")
    public AjaxJson allIndices() {
        List<EsIndexDTO> list = this.esIndexService.allIndices();
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, list);
    }

    @GetMapping(value = "all/simple")
    public AjaxJson allSimpleIndices() {
        List<EsIndexDTO> list = this.esIndexService.allSimpleIndices();
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, list);
    }

//
//    @RequestMapping(value = "{index}/{type}", method = RequestMethod.GET)
//    public AjaxJson searchMappings(@PathVariable String index,
//                                   @PathVariable String type) {
//        List<EsMappingDTO> data = this.esMappingService.searchMappings(index, type);
//        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
//    }

    @GetMapping(value = "{index}/{type}/1")
    public AjaxJson getIndex(@PathVariable String index,
                             @PathVariable String type) {
        EsIndexDTO data = this.esIndexService.getIndex(index, type);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
    }
}
