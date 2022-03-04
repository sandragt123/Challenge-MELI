package com.mercadolibre.dnaevaluator.application;

import com.mercadolibre.dnaevaluator.domain.model.Stats;
import com.mercadolibre.dnaevaluator.domain.service.MutantService;
import com.mercadolibre.dnaevaluator.infrastructure.shared.dto.DNASequenceDto;
import com.mercadolibre.dnaevaluator.infrastructure.shared.dto.StatsDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/**
 * ClassName
 **/
class MutantCtrTest {

    @Mock
    MutantService mutantService;

    @InjectMocks
    MutantCtr mutantCtr;

    @Mock
    ModelMapper mapper;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        ModelMapper mapper = Mockito.mock(ModelMapper.class);
    }

    @Test
    void validatemutantDnaOkAndDnaMutant(){
        //Arrange
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTC"};
        DNASequenceDto dnaSequenceDto = new DNASequenceDto();
        dnaSequenceDto.setDna(dna);

        when(mutantService.isMutant(dna)).thenReturn(true);
        //Act
        ResponseEntity<String> httpResponse = mutantCtr.isMutant(dnaSequenceDto);
        //Assert
        Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.OK);
    }

    @Test
    void validatemutantDnaBadRequest(){
        //Arrange
        String[] dna = {"ATGCGA","CAGTGC1","TTATGT","AGAAGG","CCCCTA","TCACTC"};
        DNASequenceDto dnaSequenceDto = new DNASequenceDto();
        dnaSequenceDto.setDna(dna);

        when(mutantService.isMutant(dna)).thenReturn(true);
        //Act
        ResponseEntity<String> httpResponse = mutantCtr.isMutant(dnaSequenceDto);
        //Assert
        Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void validatemutantDnaOKAndDnaHuman(){
        //Arrange
        String[] dna = {"ATGCGA","CAGTGC","TTATGT","AGAAGG","CCCCTA","TCACTC"};
        DNASequenceDto dnaSequenceDto = new DNASequenceDto();
        dnaSequenceDto.setDna(dna);
        when(mutantService.isMutant(dna)).thenReturn(false);
        //Act
        ResponseEntity<String> httpResponse = mutantCtr.isMutant(dnaSequenceDto);
        //Assert
        Assertions.assertEquals(httpResponse.getStatusCode(), HttpStatus.FORBIDDEN);
    }

    @Test
    void getStatsOk(){
        //Arrange
        Stats stats =  new Stats(10, 5, 2.0);
        StatsDto statsDtoResponse =  new StatsDto();
        statsDtoResponse.setCountMutantDna(10);
        statsDtoResponse.setCountHumanDna(5);
        statsDtoResponse.setRatio(2.0);
        when(mutantService.getStats()).thenReturn(stats);
        when(mapper.map(stats, StatsDto.class)).thenReturn(statsDtoResponse);
        //Act
        ResponseEntity<StatsDto> statsDto = mutantCtr.getStats();
        //Assert
        Assertions.assertEquals(statsDto.getStatusCode(), HttpStatus.OK);
    }




}