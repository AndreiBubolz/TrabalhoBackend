package com.backend.java.trabalho.repository;

import com.backend.java.trabalho.model.CityInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityInfoRepository extends MongoRepository<CityInfo, String> {

    Long countByUf(String uf);

    List<CityInfo> findAllById(String ibgd_id);

    List<CityInfo> findAllByUf(String uf);

    List<CityInfo> findAllByName(String name);

    //@Query("{ capital: { $eq: $regex: ?0 } }")
    List<CityInfo> findAllByCapital(boolean capital);

    //@Query("{ lon: { $eq: $regex: ?0 } }")
    List<CityInfo> findAllByLon(String lon);

    List<CityInfo> findAllByLat(String lat);

    @Query("{ 'no_accents': {$regex: ?0}}")
    List<CityInfo> findAllByNo_accents(String no_accents);

    @Query("{ 'alternative_names': {$regex: ?0}}")
    List<CityInfo> findAllByAlternative_names(String alternative_names);

    List<CityInfo> findAllByMicroregion(String microregion);

    List<CityInfo> findAllByMesoregion(String mesoregion);
}
