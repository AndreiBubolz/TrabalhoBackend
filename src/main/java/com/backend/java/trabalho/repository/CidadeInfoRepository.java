package com.backend.java.trabalho.repository;

import com.backend.java.trabalho.model.CidadeInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CidadeInfoRepository extends MongoRepository<CidadeInfo, String> {

    @Query("{ capital: { $eq: true } }")
    List<CidadeInfo> findByCapital();

    Long countByUf(String uf);

    List<CidadeInfo> findAllByUf(String uf);
}
