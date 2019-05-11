package com.backend.java.trabalho.service;

import com.backend.java.trabalho.model.CidadeInfo;
import com.backend.java.trabalho.repository.CidadeInfoRepository;
import com.backend.java.trabalho.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CidadeInfoService {

    private static final String CITY_NOT_FOUND = "City %s not found";

    @Autowired
    CidadeInfoRepository cidadeInfoRepository;

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
        List<CidadeInfo> capitals = cidadeInfoRepository.findByCapital();

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

}
