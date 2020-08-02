package br.com.everton.bluefood.application.service;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.everton.bluefood.domain.pagamento.DadosCartao;
import br.com.everton.bluefood.domain.pagamento.PagamentoException;
import br.com.everton.bluefood.domain.pagamento.StatusPagamento;
import br.com.everton.bluefood.domain.pedido.Carrinho;
import br.com.everton.bluefood.domain.pedido.ItemPedido;
import br.com.everton.bluefood.domain.pedido.ItemPedidoPK;
import br.com.everton.bluefood.domain.pedido.ItemPedidoRepository;
import br.com.everton.bluefood.domain.pedido.Pedido;
import br.com.everton.bluefood.domain.pedido.PedidoRepository;
import br.com.everton.bluefood.domain.pedido.Status;
import br.com.everton.bluefood.util.SecurityUtils;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Value("${bluefood.sbpay.url}")
    private String sbPayURL;

    @Value("${bluefood.sbpay.token}")
    private String sbPayToken;

    @SuppressWarnings("unchecked")
    @Transactional(rollbackFor = PagamentoException.class)
    public Pedido criarEPagar(Carrinho carrinho, String numCartao) throws PagamentoException {

        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setCliente(SecurityUtils.loggedCliente());
        pedido.setRestaurante(carrinho.getRestaurante());
        pedido.setStatus(Status.Producao);
        pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
        pedido.setSubtotal(carrinho.getPrecoTotal(false));
        pedido.setTotal(carrinho.getPrecoTotal(true));

        pedido = pedidoRepository.save(pedido);

        int ordem = 1;

        for (ItemPedido itemPedido : carrinho.getItens()) {
            itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
            itemPedidoRepository.save(itemPedido);
        }
        
        // Efetua Pagamento
       DadosCartao dadosCartao = new DadosCartao();
       dadosCartao.setNumCartao(numCartao);
       
       MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
       headers.add("Token", sbPayToken);
       
       HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
       
       RestTemplate restTemplate = new RestTemplate();
       
       Map<String, String> response;
       try {
           response = restTemplate.postForObject(sbPayURL, requestEntity, Map.class);

       } catch (Exception e) {
           throw new PagamentoException("Erro no servidor de pagamento");
       }

       StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));

       if (statusPagamento != StatusPagamento.Autorizado) {

           throw new PagamentoException(statusPagamento.getDescricao());
       }

        return pedido;
    }
}
