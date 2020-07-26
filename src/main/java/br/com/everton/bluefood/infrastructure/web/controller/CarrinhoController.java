package br.com.everton.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import br.com.everton.bluefood.domain.pedido.Carrinho;
import br.com.everton.bluefood.domain.pedido.ItemPedido;
import br.com.everton.bluefood.domain.pedido.Pedido;
import br.com.everton.bluefood.domain.pedido.PedidoRepository;
import br.com.everton.bluefood.domain.pedido.RestauranteDiferenteException;
import br.com.everton.bluefood.domain.restaurante.ItemCardapio;
import br.com.everton.bluefood.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping(path = "/adicionar")
    public String adicionarItem(@RequestParam("itemId") Integer itemId, //
                                @RequestParam("quantidade") Integer quantidade, //
                                @RequestParam("observacoes") String observacoes, //
                                @ModelAttribute("carrinho") Carrinho carrinho, //
                                Model model) {

        ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
        try {
            carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
        } catch (RestauranteDiferenteException e) {
            model.addAttribute("msg", "Não é possível inserir itens de restaurantes diferentes no carrinho.");
        }

        return "cliente-carrinho";
    }

    @GetMapping(path = "/visualizar")
    public String viewCarrinho() {
        return "cliente-carrinho";
    }

    @GetMapping(path = "/remover")
    public String removerItem(@RequestParam("itemId") Integer itemId, //
                              @ModelAttribute("carrinho") Carrinho carrinho, //
                              SessionStatus sessionStatus, //
                              Model model) {

        ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
        carrinho.removerItem(itemCardapio);

        if (carrinho.vazio()) {
            sessionStatus.setComplete();
        }

        return "cliente-carrinho";
    }

    @GetMapping(path = "/refazerCarrinho")
    public String refazerCarrinho(
                                  @RequestParam("pedidoId") Integer pedidoId, //
                                  @ModelAttribute("carrinho") Carrinho carrinho,
                                  Model model) {

        Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow();
        carrinho.limpar();
        
        for (ItemPedido itemPedido : pedido.getItens()) {
            carrinho.adicionarItem(itemPedido);
        }
        
        return "cliente-carrinho";
    }

    @ModelAttribute("carrinho")
    public Carrinho carrinho() {
        return new Carrinho();
    }
}
