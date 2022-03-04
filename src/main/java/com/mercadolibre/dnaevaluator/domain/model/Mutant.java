package com.mercadolibre.dnaevaluator.domain.model;

import java.io.Serializable;

public class Mutant implements Serializable {

    private String dna;
    private boolean isMutant;


    public Mutant(String dna, boolean isMutant){
        this.dna = dna;
        this.isMutant = isMutant;
    }

    public Mutant(){}

    public boolean isMutant() {
        return isMutant;
    }



}
