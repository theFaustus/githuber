package com.evil.inc.githuber.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class City {
    private String city;
    private String cityASCII;
    private String country;
    private String codeISO2;
    private String codeISO3;
    private double longitude;
    private double latitude;
}
