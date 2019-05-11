package com.backend.java.trabalho.repository;

import com.backend.java.trabalho.model.CidadeInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CidadeInfoRepository extends MongoRepository<CidadeInfo, String> {

    Long countByUf(String uf);

    List<CidadeInfo> findAllById(String ibgd_id);

    List<CidadeInfo> findAllByUf(String uf);

    List<CidadeInfo> findAllByName(String name);

    //@Query("{ capital: { $eq: $regex: ?0 } }")
    List<CidadeInfo> findAllByCapital(String capital);

    //@Query("{ lon: { $eq: $regex: ?0 } }")
    List<CidadeInfo> findAllByLon(String lon);

    List<CidadeInfo> findAllByLat(String lat);

    @Query("{ 'no_accents': {$regex: ?0}}")
    List<CidadeInfo> findAllByNo_accents(String no_accents);

    @Query("{ 'alternative_names': {$regex: ?0}}")
    List<CidadeInfo> findAllByAlternative_names(String alternative_names);

    List<CidadeInfo> findAllByMicroregion(String microregion);

    List<CidadeInfo> findAllByMesoregion(String mesoregion);
}
