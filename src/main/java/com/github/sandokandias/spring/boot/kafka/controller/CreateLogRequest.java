package com.github.sandokandias.spring.boot.kafka.controller;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class CreateLogRequest {

    @Min(1)
    @Max(10)
    private Integer quantity;
}
