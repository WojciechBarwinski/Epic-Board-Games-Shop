package com.wojciechbarwinski.demo.epic_board_games_shop.mappers;

import com.wojciechbarwinski.demo.epic_board_games_shop.dtos.AddressDTO;
import com.wojciechbarwinski.demo.epic_board_games_shop.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    Address addressDTOToAddress(AddressDTO addressDTO);

    AddressDTO addressToAddressDTO(Address address);

}
