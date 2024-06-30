package com.example.wedding.utils.jsonArrayConverter;

import com.example.wedding.entities.Component;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class ComponentConverter  extends JSONArrayConverter<List<Component>> {
}
