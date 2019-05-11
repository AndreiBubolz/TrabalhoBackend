package com.backend.java.trabalho.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@Document(collection = "cidades")
@TypeAlias("cidade")
public class CidadeInfo{

    @Id @NotNull @NotBlank private String id;
    @NotNull @NotBlank private String uf;
    @NotNull @NotBlank private String name;
    private Boolean capital;
    @NotNull @NotBlank private Double lon;
    @NotNull @NotBlank private Double lat;
    @NotNull @NotBlank private String no_accents;
    private String alternative_names;
    private String microregion;
    private String mesoregion;

    public CidadeInfo(String ibge_id, String uf, String name, String capital, String lon, String lat, String no_accents, String alternative_names, String microregion, String mesoregion) {
        this.id = ibge_id;
        this.uf = uf;
        this.name = name;

        if(capital.equals("true"))
            this.capital = true;
        else
            this.capital = false;

        this.lon = Double.parseDouble(lon);
        this.lat = Double.parseDouble(lat);
        this.no_accents = no_accents;
        this.alternative_names = alternative_names;
        this.microregion = microregion;
        this.mesoregion = mesoregion;
    }

}
