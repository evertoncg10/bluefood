package br.com.everton.bluefood.domain.pedido;

import java.math.BigDecimal;

import br.com.everton.bluefood.domain.restaurante.ItemCardapio;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ItemPedido {

    @EqualsAndHashCode.Include
    private Integer id;

    private ItemCardapio itemCardapio;

    private Integer quantidade;

    private String observacoes;

    private BigDecimal preco;

}
