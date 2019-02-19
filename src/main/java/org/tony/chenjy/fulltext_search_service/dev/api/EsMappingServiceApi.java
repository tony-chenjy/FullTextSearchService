package org.tony.chenjy.fulltext_search_service.dev.api;

import org.springframework.web.bind.annotation.*;
import org.tony.chenjy.fulltext_search_service.dev.dto.EsMappingDTO;
import org.tony.chenjy.fulltext_search_service.dev.service.EsMappingService;
import org.tony.chenjy.fulltext_search_service.etc.bean.AjaxJson;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("es-mapping")
public class EsMappingServiceApi {

    @Resource
    private EsMappingService esMappingService;

    @PutMapping(value = "{index}/{type}")
    public AjaxJson putMapping(@PathVariable String index,
                               @PathVariable String type,
                               @RequestBody Map<String, Object> source) {
        this.esMappingService.putMapping(index, type, source);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, null);
    }

    @RequestMapping(value = "all", method = RequestMethod.GET)
    public AjaxJson allMappings() {
        List<EsMappingDTO> data = this.esMappingService.allMappings();
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
    }

    @RequestMapping(value = "{index}/{type}", method = RequestMethod.GET)
    public AjaxJson searchMappings(@PathVariable String index,
                                   @PathVariable String type) {
        List<EsMappingDTO> data = this.esMappingService.searchMappings(index, type);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
    }

    @RequestMapping(value = "{index}/{type}/1", method = RequestMethod.GET)
    public AjaxJson getMapping(@PathVariable String index,
                               @PathVariable String type) {
        EsMappingDTO data = this.esMappingService.getMapping(index, type);
        return new AjaxJson(AjaxJson.STATUS_SUCCESS, null, data);
    }
}
