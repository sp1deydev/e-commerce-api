package com.thientdk.e_commerce_api.controllers;

import com.thientdk.e_commerce_api.entities.AttributeEntity;
import com.thientdk.e_commerce_api.entities.AttributeValueEntity;
import com.thientdk.e_commerce_api.models.dtos.AttributeDto;
import com.thientdk.e_commerce_api.models.dtos.AttributeValueDto;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.services.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping()
    public Page<AttributeEntity> getAttributes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "20") int size,
            @RequestParam(required = false, defaultValue = "") String keySearch,
            Pageable pageable
    ) {
        return attributeService.getAttributes(page, size, keySearch, pageable);
    }

    @PostMapping()
    public TextResponse createAttribute(@RequestBody AttributeDto attributeDto) {
        return attributeService.createAttribute(attributeDto.getName());
    }

    @PutMapping()
    public TextResponse updateAttribute(@RequestBody AttributeDto attributeDto) {
        return attributeService.updateAttribute(attributeDto);
    }

    @DeleteMapping("/{id}")
    public TextResponse deleteAttribute(@PathVariable(name = "id", required = false) String id) {
        return attributeService.deleteAttribute(id);
    }

    @PostMapping("/values/create-update")
    public TextResponse createUpdateAttributeValue(@RequestBody AttributeValueDto request) {
        return attributeService.createUpdateAttributeValue(request);
    }

    @DeleteMapping("/values/{id}")
    public TextResponse deleteAttributeValue(@PathVariable(name = "id", required = false) String id) {
        return attributeService.deleteAttributeValue(id);
    }

    @GetMapping("/values/attribute-id/{id}")
    public List<AttributeValueEntity> getAttributes(@PathVariable(name = "id", required = false) String id) {
        return attributeService.getAttributeValuesByAttributeId(id);
    }
}
