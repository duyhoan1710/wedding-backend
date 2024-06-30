package com.example.wedding.dtos.template.response;

import com.example.wedding.entities.Component;
import com.example.wedding.entities.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TemplateResponse {
    private Long id;
    private String name;
    private List<Component> components;

    public static TemplateResponse from(final Template template) {
        return TemplateResponse.builder()
                .id(template.getId())
                .name(template.getName())
                .components(template.getComponents())
                .build();
    }
}
