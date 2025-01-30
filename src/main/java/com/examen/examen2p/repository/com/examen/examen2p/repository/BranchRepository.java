package com.examen.examen2p.repository;

import com.examen.examen2p.model.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BranchRepository extends MongoRepository<Branch, String> {
} 