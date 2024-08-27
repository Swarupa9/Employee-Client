package com.cg;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.cg.bo.ClientBO;
import com.cg.eo.ClientEO;

@Mapper
@Component
public interface ClientMapper {

	ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ClientBO eoToBo(ClientEO clientEO);
    ClientEO boToEo(ClientBO clientBO);
}
