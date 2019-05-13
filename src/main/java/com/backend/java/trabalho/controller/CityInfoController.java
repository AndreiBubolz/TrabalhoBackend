package com.backend.java.trabalho.controller;

import com.backend.java.trabalho.model.CityInfo;
import com.backend.java.trabalho.service.CityInfoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class CityInfoController {

    @Autowired
    CityInfoService cityInfoService;

    @GetMapping(value = "/readCSV") //Requisite #1
    public ResponseEntity<List<Void>> readCSVFile(){
        cityInfoService.readCSVFile();

        List<CityInfo> cidades = cityInfoService.persistInfoCities();

        if(cidades == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else if(cidades.size() == 0)
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping(value = "/capitals") // Requisite #2
    public ResponseEntity<List<CityInfo>> findCapitals(){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.getCapitals());
    }

    @GetMapping(value = "/citiesCount") // Requisite #3
    public ResponseEntity<List<String>> getHigherAndLowerCityCounting(){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.getHigherAndLowerCityCount());
    }

    @GetMapping(value = "/citiesByUF") // Requisite #4
    public ResponseEntity<HashMap<String, Long>> getCityCountingByUF(){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.getCountByUF());
    }

    @GetMapping(value = "/findById/{ibge_id}") // Requisite #5
    public ResponseEntity<CityInfo> getCityCountingByUF(@PathVariable String ibge_id){
        Optional<CityInfo> cidadeInfo = cityInfoService.findByIbgeId(ibge_id);

        if(cidadeInfo.isPresent())
            return ResponseEntity.status(HttpStatus.OK).body(cidadeInfo.get());
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @GetMapping(value = "/findByUF/{uf}") // Requisite #6
    public ResponseEntity<Optional<List<CityInfo>>> findCitiesByUF(@PathVariable String uf){
        return ResponseEntity.status(HttpStatus.OK).body(Optional.of(cityInfoService.findByUF(uf)));
    }

    @PostMapping(value = "/addCity") // Requisite #7
    public ResponseEntity<CityInfo> addCity(@RequestBody CityInfo cityInfo){
        return ResponseEntity.status(HttpStatus.CREATED).body(cityInfoService.addCity(cityInfo));
    }

    @DeleteMapping(value = "/deleteCity/{ibge_id}") // Requisite #8
    public ResponseEntity<CityInfo> deleteCity(@PathVariable String ibge_id){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.deleteCity(ibge_id));
    }

    @GetMapping(value = "/findByColumn/{column}/{search}") // Requisite #9
    public ResponseEntity<List<CityInfo>> findByColumn(@PathVariable String column, @PathVariable String search){

        List<CityInfo> results = cityInfoService.findByColumn(column,search);

        if(results == null || results.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body(results);

    }

    @GetMapping(value = "/itemsColumn/{column}") // Requisite #10
    public ResponseEntity<Integer> getNumItemsByColumn(@PathVariable String column){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.findDistinctByColumn(column).size());
    }

    @GetMapping(value = "/itemsColumn/{column}/detail") // Requisite #10.1 (extra) List the unique results.
    public ResponseEntity<List<String>> getItemsByColumn(@PathVariable String column){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.findDistinctByColumn(column));
    }

    @GetMapping(value = "/registerCount") // Requisite #11
    public ResponseEntity<Long> getRegisterCount(){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.getRegisterCount());
    }

    @GetMapping(value = "/farthestCities") // Requisite #12
    public ResponseEntity<Set<CityInfo>> getFarthestCities(){
        return ResponseEntity.status(HttpStatus.OK).body(cityInfoService.findTheTwoFarthestCities());
    }
}