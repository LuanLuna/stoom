package com.br.luanluna.stoom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Pagination {
    private Integer page;
    private Integer size;
    private String sortBy;
    private boolean desc;
}
