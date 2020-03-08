package com.epam.lab.newsmanagement.mapper;

import com.epam.lab.newsmanagement.dto.AbstractDto;
import com.epam.lab.newsmanagement.entity.AbstractEntity;

import java.util.Objects;

public abstract class AbstractMapper<N extends AbstractEntity, T extends AbstractDto> {
    public N toEntity(T t) {
        return Objects.isNull(t) ? null : getEntity(t);
    }

    public T toDto(N n) {
        return Objects.isNull(n) ? null : getDto(n);
    }

    protected abstract N getEntity(T t);

    protected abstract T getDto(N n);
}
