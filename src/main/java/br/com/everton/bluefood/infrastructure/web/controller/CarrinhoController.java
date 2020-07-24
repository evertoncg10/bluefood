package br.com.everton.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.everton.bluefood.domain.pedido.Carrinho;
import br.com.everton.bluefood.domain.pedido.RestauranteDiferenteException;
import br.com.everton.bluefood.domain.restaurante.ItemCardapio;
import br.com.everton.bluefood.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    @GetMapping(path = "/adicionar")
    public String adicionarItem(
                                @RequestParam("itemId") Integer itemId, //
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

    @ModelAttribute("carrinho")
    public Carrinho carrinho() {
        return new Carrinho();
    }
}
