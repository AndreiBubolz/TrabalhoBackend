package com.backend.java.trabalho.util;

public class Coordinates {

    final static Double earthsRadius= 6.371e3;

    //Fórmula haversine.
    //a = sin²(Δφ/2) + cos φ1 ⋅ cos φ2 ⋅ sin²(Δλ/2)
    //c = 2 ⋅ atan2( √a, √(1−a) )
    //distance = R ⋅ c
    //where	φ is latitude, λ is longitude, R is earth’s radius (mean radius = 6,371km);
    public static Double calculateDistance(Double lat1, Double lon1, Double lat2, Double lon2) {

        double deltaLat, deltaLon, a, c, distance;

        deltaLat = Math.toRadians(lat2-lat1);
        deltaLon = Math.toRadians(lon2-lon1);
        a = Math.pow(Math.sin(deltaLat/2), 2.0 ) +
            Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(deltaLon/2) * Math.sin(deltaLon/2);

        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        distance = earthsRadius * c;

        return distance;
    }

}
