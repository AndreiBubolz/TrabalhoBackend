package com.backend.java.trabalho.controller;

import com.backend.java.trabalho.model.CidadeInfo;
import com.backend.java.trabalho.service.CidadeInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class CidadeInfoController {

    @Autowired
    CidadeInfoService cidadeInfoService;

    @GetMapping(value = "/loadCSV")
    public ResponseEntity<List<Void>> readCSVFile(){
        cidadeInfoService.readCSVFile();
        cidadeInfoService.persistInfoCidades();

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(value = "/capitals")
    public ResponseEntity<List<CidadeInfo>> findCapitals(){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.findCapitals());
    }

}
