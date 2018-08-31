package io.github.seastar.tx.org.iface;

import io.github.seastar.tx.ac.model.AcRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IAcRoleService {

    @Autowired
    private RestTemplate restTemplate;


    public AcRole findById(Integer id) {

        AcRole acRole =
                restTemplate.getForObject("http://localhost:9832/ac/role/{id}", AcRole.class, id);
        return acRole;
    }
}
