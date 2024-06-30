package com.example.wedding.entities;

import com.example.wedding.enums.ComponentValueKey;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Component {
    String code;

    Map<ComponentValueKey, String> data;
}
