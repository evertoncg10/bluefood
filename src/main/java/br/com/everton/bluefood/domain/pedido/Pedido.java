package br.com.everton.bluefood.domain.pedido;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.com.everton.bluefood.domain.cliente.Cliente;
import br.com.everton.bluefood.domain.restaurante.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "pedido")
public class Pedido implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDateTime data;

    @NotNull
    private Status status;

    @NotNull
    @ManyToOne
    private Cliente cliente;

    @NotNull
    @ManyToOne
    private Restaurante restaurante;

    @NotNull
    private BigDecimal subtotal;

    @NotNull
    @Column(name = "taxa_entrega")
    private BigDecimal taxaEntrega;

    @NotNull
    private BigDecimal total;

    @OneToMany(mappedBy = "id.pedido", fetch = FetchType.EAGER)
    private Set<ItemPedido> Itens;
}
