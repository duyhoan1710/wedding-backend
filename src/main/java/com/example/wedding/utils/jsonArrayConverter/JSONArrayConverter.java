package com.example.wedding.utils.jsonArrayConverter;

import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@Converter(autoApply = false)
public class JSONArrayConverter <T> implements AttributeConverter<T, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = (Logger) LoggerFactory.getLogger(JSONArrayConverter.class);

    @Override
    public String convertToDatabaseColumn(T array)
    {
        String data = null;

        try
        {
            data = objectMapper.writeValueAsString(array);
        }
        catch (final Exception e)
        {
            logger.error("JSON writing error", e);
        }

        return data;
    }

    @Override
    public T convertToEntityAttribute(String data)
    {
        T result = null;

        try
        {
            result = objectMapper.readValue(data, new TypeReference<T>() {});
        }
        catch (final Exception e) {
            logger.error("JSON reading error", e);
        }

        return result;
    }
}