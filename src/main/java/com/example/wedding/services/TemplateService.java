package com.example.wedding.services;

import com.example.wedding.dtos.common.response.ResponseData;
import com.example.wedding.dtos.common.response.ResponsePagination;
import com.example.wedding.dtos.template.request.CreateTemplateDto;
import com.example.wedding.dtos.template.request.ListTemplateQueryDto;
import com.example.wedding.dtos.template.request.UpdateTemplateDto;
import com.example.wedding.dtos.template.response.TemplateResponse;

public interface TemplateService {
    void createTemplate (long userId, CreateTemplateDto payload);

    void updateTemplate (long userId, long templateId, UpdateTemplateDto payload);

    ResponseData<TemplateResponse> getTemplate (long userId, long templateId);

    ResponsePagination<TemplateResponse> getTemplates (long userId, ListTemplateQueryDto query);
}
