package com.cabapplication.user.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.cabapplication.user.entity.Source;

public interface SourceRepository extends MongoRepository<Source, Integer> {

}
