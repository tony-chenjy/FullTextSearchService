package org.tony.chenjy.fulltext_search_service.etc.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AjaxJson implements Serializable {

    public static final String STATUS_SUCCESS = "y";
    public static final String STATUS_ERROR = "n";
    public static final String STATUS_NOT_LOGIN = "nl";

    private String status;
    private String info;
    private Object data;
}
