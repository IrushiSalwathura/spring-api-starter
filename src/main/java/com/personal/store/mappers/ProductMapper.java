package com.personal.store.mappers;

import com.personal.store.dtos.ProductDto;
import com.personal.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}
