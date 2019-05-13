package com.backend.java.trabalho.service;

import com.backend.java.trabalho.model.CityInfo;
import com.backend.java.trabalho.repository.CityInfoRepository;
import com.backend.java.trabalho.util.Coordinates;
import com.backend.java.trabalho.util.FileHandler;
import com.mongodb.client.DistinctIterable;
import com.mongodb.client.MongoCursor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CityInfoService {

    private static final String CITY_NOT_FOUND = "City %s not found";
    private static final String COLUMN_NOT_FOUND = "Column %s not found";

    @Autowired
    CityInfoRepository cityInfoRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    List<CityInfo> cities;

    public void readCSVFile(){
        cities = FileHandler.splitInfo( FileHandler.readCSV() );
    }

    public List<CityInfo> persistInfoCities(){
        if(cities == null) return null;

        return cityInfoRepository.saveAll(cities);
    }

    public List<CityInfo> getCapitals(){
        List<CityInfo> capitals = cityInfoRepository.findAllByCapital(true);

        return capitals.stream()
                .sorted(Comparator.comparing(CityInfo::getNo_accents))
                .collect(Collectors.toList());
    }

    public List<String> getHigherAndLowerCityCount(){

        Map<String, Long> countByUF = getCountByUF();

        List<String> result = new ArrayList<>();
        Map.Entry<String, Long> maxEntry = null,minEntry = null;

        for (Map.Entry<String, Long> entry : countByUF.entrySet())
        {
            if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                maxEntry = entry;
            if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0)
                minEntry = entry;
        }

        result.add(maxEntry.getKey()+" : "+ maxEntry.getValue());
        result.add(minEntry.getKey()+" : "+ minEntry.getValue());

        return result;
    }

    public HashMap<String, Long> getCountByUF(){

        List<CityInfo> cityInfos = cityInfoRepository.findAll();
        HashMap<String,Long> countByUF = new HashMap<>();

        cityInfos.forEach(cityInfo -> {
            String uf = cityInfo.getUf();
            if(!countByUF.containsKey(uf))
                countByUF.put(uf, cityInfoRepository.countByUf(uf));

        });

        return countByUF;
    }

    public Optional<CityInfo> findByIbgeId(String ibge_id) {
        return cityInfoRepository.findById(ibge_id);
    }

    public List<CityInfo> findByUF(String uf) {
        return cityInfoRepository.findAllByUf(uf);
    }

    public CityInfo addCity(CityInfo cityInfo) {
        return cityInfoRepository.save(cityInfo);
    }

    public CityInfo deleteCity(String ibge_id) {
        Optional<CityInfo> city = findByIbgeId(ibge_id);

        if(!city.isPresent()) throw new ResourceNotFoundException(String.format(CITY_NOT_FOUND,ibge_id));

        cityInfoRepository.delete(city.get());

        return city.get();
    }

    public List<CityInfo> findByColumn(String column, String search) {

        if(!FileHandler.getColumns().contains(column))
            throw new ResourceNotFoundException(String.format(COLUMN_NOT_FOUND,column));


        switch(FileHandler.getColumns().indexOf(column)) {
            case 0: return cityInfoRepository.findAllById(search);
            case 1: return cityInfoRepository.findAllByUf(search);
            case 2: return cityInfoRepository.findAllByName(search);
            case 3:
                if(search.equals("true"))
                    return cityInfoRepository.findAllByCapital(true);
                else if(search.equals("false"))
                    return cityInfoRepository.findAllByCapital(true);
                else
                    return null;
            case 4: return cityInfoRepository.findAllByLon(search);
            case 5: return cityInfoRepository.findAllByLat(search);
            case 6: return cityInfoRepository.findAllByNo_accents(search);
            case 7: return cityInfoRepository.findAllByAlternative_names(search);
            case 8: return cityInfoRepository.findAllByMicroregion(search);
            case 9: return cityInfoRepository.findAllByMesoregion(search);
            default: return null;
        }

    }

    public List<String> findDistinctByColumn(String search){

        DistinctIterable<String> iterable = mongoTemplate.getCollection("cidades").distinct(search,String.class);
        MongoCursor<String> cursor = iterable.iterator();

        List<String> listDistinct = new ArrayList<>();
        while (cursor.hasNext())
            listDistinct.add(cursor.next());

        return listDistinct;
    }

    public Long getRegisterCount(){
        return cityInfoRepository.count();
    }

    public Set<CityInfo> findTheTwoFarthestCities(){

        CityInfo cityInfo1 =null, cityInfo2 =null;
        Double highestDistance = 0.0;

        List<CityInfo> cities = cityInfoRepository.findAll();

        for(int x=0;x < cities.size()-1 ;x++){
            for(int y=1;y < cities.size();y++){
                Double distance = Coordinates.calculateDistance(cities.get(x).getLat(),cities.get(x).getLon(),cities.get(y).getLat(),cities.get(y).getLon());
                if(distance > highestDistance) {
                    cityInfo1 = cities.get(x);
                    cityInfo2 = cities.get(y);
                    highestDistance = distance;
                }
            }
        }

        Set<CityInfo> result = new HashSet<>();
        result.add(cityInfo1);
        result.add(cityInfo2);

        return result;
    }
}
