package com.bombert.validation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface ClientRepository extends CrudRepository<Client, Long> {
    boolean existsByUsername(String username);
}
