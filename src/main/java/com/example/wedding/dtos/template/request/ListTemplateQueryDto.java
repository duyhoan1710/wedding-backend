package com.example.wedding.dtos.template.request;

import com.example.wedding.dtos.common.request.PageReq;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ListTemplateQueryDto extends PageReq {
    String name;
}
