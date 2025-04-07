package com.example.tpjenkins.Items.mapper;



import com.example.tpjenkins.Items.ItemsModel;
import com.example.tpjenkins.Items.dto.ItemsDTO;
import com.example.tpjenkins.utils.BaseMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


/**
 * The interface Items mapper.
 */
@Mapper(componentModel = "spring")
public interface ItemsMapper extends BaseMapper<ItemsModel, ItemsDTO> {
    /**
     * The constant INSTANCE.
     */
    ItemsMapper INSTANCE = Mappers.getMapper(ItemsMapper.class);
}