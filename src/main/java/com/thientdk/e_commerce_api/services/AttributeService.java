package com.thientdk.e_commerce_api.services;

import com.thientdk.e_commerce_api.aop.exceptions.ApiException;
import com.thientdk.e_commerce_api.aop.exceptions.ErrorCode;
import com.thientdk.e_commerce_api.entities.AttributeEntity;
import com.thientdk.e_commerce_api.entities.AttributeValueEntity;
import com.thientdk.e_commerce_api.models.dtos.AttributeDto;
import com.thientdk.e_commerce_api.models.dtos.AttributeValueDto;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.repositories.AttributeRepository;
import com.thientdk.e_commerce_api.repositories.AttributeValueRepository;
import com.thientdk.e_commerce_api.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttributeService {

    private final AttributeRepository attributeRepository;
    private final AttributeValueRepository attributeValueRepository;

    public TextResponse createAttribute(String name) {
        if (StringUtil.isNullOrEmpty(name)) {
            log.info("[createAttribute] - create attribute - is null or empty");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute name is not valid!");
        }
        log.info("[createAttribute] - START");
        AttributeEntity attributeEntity = new AttributeEntity();
        attributeEntity.setName(name);
        attributeRepository.save(attributeEntity);
        log.info("[createAttribute] - END");
        return new TextResponse("Create Attribute successfully.");
    }

    public TextResponse updateAttribute(AttributeDto attributeDto) {
        if (StringUtil.isNullOrEmpty(attributeDto.getId())) {
            log.info("[updateAttribute] - update attribute - id is null");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute id is not valid!");
        }

        if (StringUtil.isNullOrEmpty(attributeDto.getName())) {
            log.info("[updateAttribute] - update attribute - name is null or empty");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute name is not valid!");
        }

        log.info("[updateAttribute] - START");
        AttributeEntity attributeEntity = attributeRepository.findById(attributeDto.getId())
                .orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "Attribute not found with id: " + attributeDto.getId()));

        attributeEntity.setName(attributeDto.getName());
        attributeRepository.save(attributeEntity);

        log.info("[updateAttribute] - END");
        return new TextResponse("Update Attribute successfully.");
    }

    public TextResponse deleteAttribute(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            log.info("[deleteAttribute] - delete attribute - id is null");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute id is not valid!");
        }
        log.info("[deleteAttribute] - START");
        if (!attributeRepository.existsById(id)) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute not found with id: " + id);
        }
        attributeRepository.deleteById(id);
        attributeValueRepository.deleteByAttributeId(id);

        log.info("[deleteAttribute] - END");
        return new TextResponse("Delete Attribute successfully.");
    }


    public Page<AttributeEntity> getAttributes(Integer page, Integer size, String keySearch, Pageable pageable) {
        log.info("[getAttributes] - START");

        Sort sort = pageable.getSort();
        Pageable pageRequest = PageRequest.of(page, size, sort);

        Page<AttributeEntity> attributeEntities = attributeRepository.findByNameContaining(keySearch, pageRequest);
        log.info("[getAttributes] - END");
        return attributeEntities;
    }

    public TextResponse createUpdateAttributeValue(AttributeValueDto request) {
        if (request.getId() == null) {
            log.info("[createUpdateAttributeValue] - create attribute id START");
            AttributeValueEntity entity = new AttributeValueEntity();
            entity.setAttributeId(request.getAttributeId());
            entity.setValue(request.getValue());
            attributeValueRepository.save(entity);
            log.info("[createUpdateAttributeValue] - create attribute id END");
            return new TextResponse("Create Attribute Value successfully.");
        }
        else {
            log.info("[createUpdateAttributeValue] - update attribute id START");
            Optional<AttributeValueEntity> opt = attributeValueRepository.findById(request.getId());
            if (opt.isEmpty()) {
                log.info("[createUpdateAttributeValue] - attribute value not found");
                throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute value not found with id: " + request.getId());
            }
            AttributeValueEntity entity = opt.get();
            entity.setAttributeId(request.getAttributeId());
            entity.setValue(request.getValue());
            attributeValueRepository.save(entity);
            log.info("[createUpdateAttributeValue] - update attribute id END");
            return new TextResponse("Update Attribute Value successfully.");
        }

    }

    public TextResponse deleteAttributeValue(String id) {
        if (StringUtil.isNullOrEmpty(id)) {
            log.info("[deleteAttributeValue] - delete attribute value - id is null");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute id is not valid!");
        }
        log.info("[deleteAttributeValue] - START");
        if (!attributeRepository.existsById(id)) {
            log.info("[deleteAttributeValue] - attribute value not found");
            throw new ApiException(ErrorCode.BAD_REQUEST, "Attribute not found with id: " + id);
        }
        attributeValueRepository.deleteById(id);
        log.info("[deleteAttributeValue] - END");
        return new TextResponse("Delete Attribute Value successfully.");
    }

    public List<AttributeValueEntity> getAttributeValuesByAttributeId(String id) {
        log.info("[getAttributeValuesByAttributeId] - START");
        List<AttributeValueEntity> entities = attributeValueRepository.findAttributeValueEntitiesByAttributeId(id);
        log.info("[getAttributeValuesByAttributeId] - END");
        return entities;
    }
}
