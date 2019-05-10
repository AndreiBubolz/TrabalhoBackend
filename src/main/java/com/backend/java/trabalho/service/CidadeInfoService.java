package com.backend.java.trabalho.service;

import com.backend.java.trabalho.model.CidadeInfo;
import com.backend.java.trabalho.repository.CidadeInfoRepository;
import com.backend.java.trabalho.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CidadeInfoService {

    @Autowired
    CidadeInfoRepository cidadeInfoRepository;

    List<CidadeInfo> infoCidades;

    public void readCSVFile(){
        infoCidades = FileHandler.splitInfo( FileHandler.readCSV() );
    }

    public void persistInfoCidades(){
        cidadeInfoRepository.saveAll(infoCidades);
    }

    public List<CidadeInfo> findCapitals(){
        List<CidadeInfo> capitals = cidadeInfoRepository.findByCapital();

        return capitals.stream()
                .sorted(Comparator.comparing(CidadeInfo::getNo_accents))
                .collect(Collectors.toList());
    }

}
