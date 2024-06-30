package com.example.wedding.services.serviceImpl;

import com.example.wedding.dtos.common.response.ResponseData;
import com.example.wedding.dtos.common.response.ResponsePagination;
import com.example.wedding.dtos.template.request.CreateTemplateDto;
import com.example.wedding.dtos.template.request.ListTemplateQueryDto;
import com.example.wedding.dtos.template.request.UpdateTemplateDto;
import com.example.wedding.dtos.template.response.TemplateResponse;
import com.example.wedding.entities.Template;
import com.example.wedding.exceptions.ErrorCode;
import com.example.wedding.exceptions.ErrorException;
import com.example.wedding.repositories.TemplateRepository;
import com.example.wedding.services.TemplateService;
import com.example.wedding.utils.Utils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TemplateServiceImpl implements TemplateService {
    final private TemplateRepository templateRepository;

    public TemplateServiceImpl (TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public void createTemplate (long userId, CreateTemplateDto payload) {
        Template template = this.templateRepository.findOneByUserIdAndNameLike(userId, payload.getName()).orElse(null);

        if (template != null) {
            throw new ErrorException(ErrorCode.TEMPLATE_ALREADY_EXISTS);
        }

        Template newTemplate = new Template();
        newTemplate.setName(payload.getName());
        newTemplate.setUserId(userId);

        templateRepository.save(newTemplate);
    }

    public void updateTemplate (long userId, long templateId, UpdateTemplateDto payload) {
        Template template = templateRepository.findByIdAndUserId(templateId, userId).orElse(null);

        if (template == null) {
            throw new ErrorException(ErrorCode.TEMPLATE_NOT_FOUND);
        }

        template.setName(payload.getName());
        template.setComponents(payload.getComponents());

        templateRepository.save(template);
    }

    public ResponseData<TemplateResponse> getTemplate (long userId, long templateId) {
        Template template = templateRepository.findByIdAndUserId(templateId, userId).orElse(null);

        if (template == null) {
            throw new ErrorException(ErrorCode.TEMPLATE_NOT_FOUND);
        }

        return new ResponseData<>(TemplateResponse.from(template));
    }

    public ResponsePagination<TemplateResponse> getTemplates (long userId, ListTemplateQueryDto query) {
        final Pageable pageable = PageRequest.of(query.getPage() - 1, query.getLimit());

        Page<Template> template = this.templateRepository.findByUserIdAndNameLike(userId, Utils.convertKeywordLike(query.getName()), pageable);

        return new ResponsePagination<>(
                query.getPage(),
                query.getLimit(),
                template.getTotalElements(),
                template.getContent().stream().map(TemplateResponse::from).toList());
    }
}
