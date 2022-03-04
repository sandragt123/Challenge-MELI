package com.mercadolibre.dnaevaluator.infrastructure.shared.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.Collectors;

@Setter
@Getter
public class DNASequenceDto implements Serializable {

    @NotNull
    private  String[] dna;

    @SneakyThrows
    //Se valida si el dna  ingresado cumple las especificaciones, de lo contrario se retorna una excepción,
    //Se realiza la validación en el dto para que éste tenga la responsabilidad de entregar solo el dato cuando es correcto y procesarlo en la capa de dominio
    public String[] getDna() {

        long count = Arrays.stream(dna)
                .map(Object::toString)
                .collect(Collectors.joining(",")).length() / dna.length;

        String validateDnaPattern = String.format("[ATCG]{%s}", count);

        long validDnasNumber  = Arrays.stream(dna)
                .filter(s -> s.matches(validateDnaPattern))
                .count();
        if(validDnasNumber != count) {
          throw new Exception("The typed sequence is not valid, please validate and try again");
        }
        return dna;
    }




}
