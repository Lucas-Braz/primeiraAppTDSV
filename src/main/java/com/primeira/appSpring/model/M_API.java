package com.primeira.appSpring.model;

import java.time.LocalDate;

public interface M_API {
    String getProduto();
    Long getQuantidade();
    Integer getMin();
    Integer getMax();
    Double getCusto_medio();
    LocalDate getUltima_compra();
}
