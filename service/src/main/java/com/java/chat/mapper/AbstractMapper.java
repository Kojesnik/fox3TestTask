package com.java.chat.mapper;

import com.java.chat.dto.EntityDto;
import com.java.chat.model.AbstractEntity;

import java.util.List;

public interface AbstractMapper<T extends EntityDto, K extends AbstractEntity> {

    K toEntity(T dto);
    T toDto(K entity);
    List<T> toDtoList(List<K> entities);
    List<K> toEntityList(List<T> dtos);

}
