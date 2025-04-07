package com.example.tpjenkins.utils;

import java.util.List;

/**
 * The interface Base mapper.
 *
 * @param <E> the type parameter
 * @param <D>    the type parameter
 */
public interface BaseMapper<E, D>
{
    /**
     * To dto dto.
     *
     * @param entity the entity
     * @return the dto
     */
    D toDto(E entity);

    /**
     * To dto list list.
     *
     * @param entityList the entity list
     * @return the list
     */
    List<D> toDtoList(List<E> entityList);
}