package br.com.everton.bluefood.domain.restaurante;

import lombok.Data;

@Data
public class SearchFilter {

    public enum SearchType {
            Texto, Categoria
    }

    public String texto;

    private SearchType searchType;

    private Integer categoriaId;

    public void processFilter() {
        if (searchType == SearchType.Texto) {
            categoriaId = null;
        } else if (searchType == SearchType.Categoria) {
            texto = null;
        }
    }
}
