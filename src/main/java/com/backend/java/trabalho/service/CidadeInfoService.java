package com.backend.java.trabalho.service;

import com.backend.java.trabalho.model.CidadeInfo;
import com.backend.java.trabalho.repository.CidadeInfoRepository;
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
public class CidadeInfoService {

    private static final String CITY_NOT_FOUND = "City %s not found";
    private static final String COLUMN_NOT_FOUND = "Column %s not found";

    @Autowired
    CidadeInfoRepository cidadeInfoRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    List<CidadeInfo> infoCidades;

    public void readCSVFile(){
        infoCidades = FileHandler.splitInfo( FileHandler.readCSV() );
    }

    public List<CidadeInfo> persistInfoCidades(){
        if(infoCidades == null) return null;

        cidadeInfoRepository.saveAll(infoCidades);

        return infoCidades;
    }

    public List<CidadeInfo> findCapitals(){
        List<CidadeInfo> capitals = cidadeInfoRepository.findAllByCapital("true");

        return capitals.stream()
                .sorted(Comparator.comparing(CidadeInfo::getNo_accents))
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

        List<CidadeInfo> cidadeInfos = cidadeInfoRepository.findAll();
        HashMap<String,Long> countByUF = new HashMap<>();

        cidadeInfos.forEach(cidadeInfo -> {
            String uf = cidadeInfo.getUf();
            if(!countByUF.containsKey(uf))
                countByUF.put(uf,cidadeInfoRepository.countByUf(uf));

        });

        return countByUF;
    }

    public Optional<CidadeInfo> findByIbgeId(String ibge_id) {
        return cidadeInfoRepository.findById(ibge_id);
    }

    public List<CidadeInfo> findByUF(String uf) {
        return cidadeInfoRepository.findAllByUf(uf);
    }

    public CidadeInfo addCity(CidadeInfo cidadeInfo) {
        return cidadeInfoRepository.save(cidadeInfo);
    }

    public CidadeInfo deleteCity(String ibge_id) {
        Optional<CidadeInfo> cidadeInfo = findByIbgeId(ibge_id);

        if(!cidadeInfo.isPresent()) throw new ResourceNotFoundException(String.format(CITY_NOT_FOUND,ibge_id));

        cidadeInfoRepository.delete(cidadeInfo.get());

        return cidadeInfo.get();
    }

    public List<CidadeInfo> findByColumn(String column, String search) {

        if(!FileHandler.getColumns().contains(column))
            throw new ResourceNotFoundException(String.format(COLUMN_NOT_FOUND,column));


        switch(FileHandler.getColumns().indexOf(column)) {
            case 0: return cidadeInfoRepository.findAllById(search);
            case 1: return cidadeInfoRepository.findAllByUf(search);
            case 2: return cidadeInfoRepository.findAllByName(search);
            case 3: return cidadeInfoRepository.findAllByCapital(search);
            case 4: return cidadeInfoRepository.findAllByLon(search);
            case 5: return cidadeInfoRepository.findAllByLat(search);
            case 6: return cidadeInfoRepository.findAllByNo_accents(search);
            case 7: return cidadeInfoRepository.findAllByAlternative_names(search);
            case 8: return cidadeInfoRepository.findAllByMicroregion(search);
            case 9: return cidadeInfoRepository.findAllByMesoregion(search);
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
        return cidadeInfoRepository.count();
    }
}
