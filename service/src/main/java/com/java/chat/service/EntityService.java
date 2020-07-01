package com.java.chat.service;

import com.java.chat.dto.EntityDto;

public interface EntityService<T extends EntityDto> {

    T add(T dto);
    T update(T dto);
    Boolean remove(T dto);
    T get(T dto);

}
