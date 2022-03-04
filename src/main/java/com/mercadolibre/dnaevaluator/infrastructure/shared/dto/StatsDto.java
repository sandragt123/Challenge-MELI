package com.mercadolibre.dnaevaluator.infrastructure.shared.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
@JsonPropertyOrder({ "countMutantDna", "countHumanDna", "ratio"})
public class StatsDto implements Serializable{

    @JsonProperty("count_mutant_dna")
    private int countMutantDna;

    @JsonProperty("count_human_dna")
    private int countHumanDna;

    private double ratio;

}
