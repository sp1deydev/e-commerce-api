package com.thientdk.e_commerce_api.controllers;

import com.thientdk.e_commerce_api.models.dtos.AttributeDto;
import com.thientdk.e_commerce_api.models.responses.TextResponse;
import com.thientdk.e_commerce_api.services.AttributeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/attributes")
@RequiredArgsConstructor
public class AttributeController {

    private final AttributeService attributeService;

    @GetMapping()
    public void getAttributes() {
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
}
