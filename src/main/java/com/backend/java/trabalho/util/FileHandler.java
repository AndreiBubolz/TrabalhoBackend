package com.backend.java.trabalho.util;

import com.backend.java.trabalho.model.CidadeInfo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    public List<String> readCSV(){

        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("cidades.csv"))) {
            String line;
            line = br.readLine(); //Consume first line.
            while ((line = br.readLine()) != null)
                list.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<CidadeInfo> splitInfo(List<String> stringList){

        List<CidadeInfo> list = new ArrayList<>();

        stringList.forEach(line -> {
            String[] splittedLine = line.split(",");
            list.add(new CidadeInfo(splittedLine[0], splittedLine[1],
                                    splittedLine[2],splittedLine[3],
                                    splittedLine[4],splittedLine[5],
                                    splittedLine[6],splittedLine[7],
                                    splittedLine[8],splittedLine[9]));
        });

        return list;
    }

}
