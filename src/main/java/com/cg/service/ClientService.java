package com.cg.service;

import com.cg.bo.ClientBO;
import java.util.List;

public interface ClientService {

    // Using Rest template
    List<ClientBO> getAllClientsFromRest();
    ClientBO getClientByIdFromRest(Long id);
    ClientBO createClientFromRest(ClientBO clientBO);
    
    // Using Feign client
    List<ClientBO> getAllClientsFromFeign();
    ClientBO getClientByIdFromFeign(Long id);
    ClientBO createClientFromFeign(ClientBO clientBO);
	
}
