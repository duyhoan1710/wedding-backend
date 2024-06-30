package com.example.wedding.controllers;

import com.example.wedding.dtos.auth.request.RegisterDto;
import com.example.wedding.dtos.common.response.ResponseData;
import com.example.wedding.dtos.common.response.ResponsePagination;
import com.example.wedding.dtos.template.request.CreateTemplateDto;
import com.example.wedding.dtos.template.request.ListTemplateQueryDto;
import com.example.wedding.dtos.template.request.UpdateTemplateDto;
import com.example.wedding.dtos.template.response.TemplateResponse;
import com.example.wedding.services.TemplateService;
import com.example.wedding.utils.jwt.JwtPayload;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("templates")
@SecurityRequirement(name = "bearerAuth")
public class TemplateController {
    final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping()
    void createTemplate(@RequestBody CreateTemplateDto payload) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        JwtPayload jwtPayload = (JwtPayload) auth.getCredentials();

        templateService.createTemplate(jwtPayload.getId(), payload);
    }

    @GetMapping()
    ResponsePagination<TemplateResponse> getTemplates(@RequestParam(value = "page", required = true) int page, @RequestParam(value = "limit", required = true) int limit, @RequestParam(value = "name", required = false, defaultValue = "") String name) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        JwtPayload jwtPayload = (JwtPayload) auth.getCredentials();

        ListTemplateQueryDto query = new ListTemplateQueryDto();
        query.setLimit(limit);
        query.setPage(page);
        query.setName(name);

        return templateService.getTemplates(jwtPayload.getId(), query);
    }

    @GetMapping("/{templateId}")
    ResponseData<TemplateResponse> getTemplate(@PathVariable("templateId") long templateId) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        JwtPayload jwtPayload = (JwtPayload) auth.getCredentials();

        return templateService.getTemplate(jwtPayload.getId(), templateId);
    }

    @PutMapping("/{templateId}")
    void updateTemplate(@PathVariable("templateId") long templateId, @RequestBody UpdateTemplateDto payload) {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
        JwtPayload jwtPayload = (JwtPayload) auth.getCredentials();

        templateService.updateTemplate(jwtPayload.getId(), templateId, payload);
    }
}
