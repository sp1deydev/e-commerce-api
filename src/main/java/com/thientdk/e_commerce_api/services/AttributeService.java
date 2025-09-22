package com.thientdk.e_commerce_api.services;

import com.thientdk.e_commerce_api.aop.exceptions.ApiException;
import com.thientdk.e_commerce_api.aop.exceptions.ErrorCode;
import com.thientdk.e_commerce_api.entities.AttributeEntity;
import com.thientdk.e_commerce_api.models.dtos.AttributeDto;
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

    public void createAttributeValue() {
        log.info("[createAttributeValue] - START");
        log.info("[createAttributeValue] - END");
    }

    public void updateAttributeValue() {
        log.info("[updateAttributeValue] - START");
        log.info("[updateAttributeValue] - END");
    }

    public void deleteAttributeValue() {
        log.info("[deleteAttributeValue] - START");
        log.info("[deleteAttributeValue] - END");
    }

    public void getAttributeValuesByAttribute() {
        log.info("[getAttributeValuesByAttribute] - START");
        log.info("[getAttributeValuesByAttribute] - END");
    }
}
