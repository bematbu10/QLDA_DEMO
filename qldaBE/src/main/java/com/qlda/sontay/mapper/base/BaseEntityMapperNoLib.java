package com.qlda.sontay.mapper.base;


import java.util.List;
import java.util.stream.Collectors;


public abstract class BaseEntityMapperNoLib<D, E> {

    public abstract E toEntity(D dto);

    public abstract D toDto(E entity);

    public List<E> toEntity(List<D> dtoList) {
        if (dtoList == null) return null;
        return dtoList.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<D> toDto(List<E> entityList) {
        if (entityList == null) return null;
        return entityList.stream().map(this::toDto).collect(Collectors.toList());
    }

    /**
     * Cập nhật một entity từ DTO, bỏ qua field null.
     * Mapper con có thể override để custom logic.
     */
    public void partialUpdate(E entity, D dto) {
        // mặc định không làm gì, override ở mapper con nếu cần
    }
}
