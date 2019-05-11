package com.backend.java.trabalho.controller;

import com.backend.java.trabalho.model.CidadeInfo;
import com.backend.java.trabalho.service.CidadeInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class CidadeInfoController {

    @Autowired
    CidadeInfoService cidadeInfoService;

    @GetMapping(value = "/loadCSV") //Requisito #1
    public ResponseEntity<List<Void>> readCSVFile(){
        cidadeInfoService.readCSVFile();

        List<CidadeInfo> cidades = cidadeInfoService.persistInfoCidades();

        if(cidades == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else if(cidades.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(value = "/capitals") // Requisito #2
    public ResponseEntity<List<CidadeInfo>> findCapitals(){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.findCapitals());
    }

    @GetMapping(value = "/citiesCount") // Requisito #3
    public ResponseEntity<List<String>> getHigherAndLowerCityCounting(){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.getHigherAndLowerCityCount());
    }

    @GetMapping(value = "/citiesByUF") // Requisito #4
    public ResponseEntity<HashMap<String, Long>> getCityCountingByUF(){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.getCountByUF());
    }

    @GetMapping(value = "/findById/{ibge_id}") // Requisito #5
    public ResponseEntity<CidadeInfo> getCityCountingByUF(@PathVariable String ibge_id){
        Optional<CidadeInfo> cidadeInfo = cidadeInfoService.findByIbgeId(ibge_id);

        if(cidadeInfo.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(cidadeInfo.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "/findByUF/{uf}") // Requisito #6
    public ResponseEntity<List<CidadeInfo>> findCitiesByUF(@PathVariable String uf){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.findByUF(uf));
    }

    @PostMapping(value = "/addCity") // Requisito #7
    public ResponseEntity<CidadeInfo> addCity(@RequestBody CidadeInfo cidadeInfo){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.addCity(cidadeInfo));
    }

    @DeleteMapping(value = "/deleteCity/{ibge_id}") // Requisito #8
    public ResponseEntity<CidadeInfo> deleteCity(@PathVariable String ibge_id){
        return ResponseEntity.status(HttpStatus.OK).body(cidadeInfoService.deleteCity(ibge_id));
    }

}
