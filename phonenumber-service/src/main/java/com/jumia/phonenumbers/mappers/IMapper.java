package com.jumia.phonenumbers.mappers;

import java.util.Set;

public interface IMapper<E, D> {
    E toEntity(D dto);

    D toDTO(E entity);

    Set<E> toEntities(Set<D> dtos);

    Set<D> toDTOs(Set<E> entities);

}
