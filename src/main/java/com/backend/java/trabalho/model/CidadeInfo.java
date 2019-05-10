package com.backend.java.trabalho.model;

import lombok.Getter;

@Getter
public class CidadeInfo {

    private String ibge_id;
    private String uf;
    private String name;
    private Boolean capital;
    private Double lon;
    private Double lat;
    private Boolean no_accents;
    private String alternative_names;
    private String microregion;
    private String mesoregion;

    public CidadeInfo(String ibge_id, String uf, String name, String capital, String lon, String lat, String no_accents, String alternative_names, String microregion, String mesoregion) {
        this.ibge_id = ibge_id;
        this.uf = uf;
        this.name = name;

        if(capital.equals("true"))
            this.capital = true;
        else
            this.capital = false;

        this.lon = Double.parseDouble(lon);
        this.lat = Double.parseDouble(lat);

        if(no_accents.equals("true"))
            this.no_accents = true;
        else
            this.no_accents = false;

        this.alternative_names = alternative_names;
        this.microregion = microregion;
        this.mesoregion = mesoregion;
    }
}
