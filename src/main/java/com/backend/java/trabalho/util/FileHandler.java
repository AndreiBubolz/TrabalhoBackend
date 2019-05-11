package com.backend.java.trabalho.util;

import com.backend.java.trabalho.model.CidadeInfo;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandler {

    @Getter
    private static List<String> columns;

    public static List<String> readCSV(){

        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("cidades.csv"))) {
            String line;
            line = br.readLine();
            columns = Arrays.asList(line.split(","));
            while ((line = br.readLine()) != null)
                list.add(line);
        } catch (IOException e) {
            return null;
        }

        return list;
    }

    public static List<CidadeInfo> splitInfo(List<String> stringList){

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

    public static void loadColumns(){

        try (BufferedReader br = new BufferedReader(new FileReader("cidades.csv"))) {
            columns = Arrays.asList(br.readLine().split(","));
        } catch (IOException e){}

    }
}
