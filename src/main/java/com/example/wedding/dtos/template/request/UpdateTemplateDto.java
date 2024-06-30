package com.example.wedding.dtos.template.request;

import com.example.wedding.entities.Component;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateTemplateDto {
    String name;

    List<Component> components;
}
