package br.com.everton.bluefood.domain.pedido;


public enum Status {

        Producao(1, "Em produção", false), //
        Entrega(2, "Saiu para entrega", false), //
        Concluido(3, "Concluído", true);

    Status(int ordem, String descricao, boolean ultimo) {
        this.ordem = ordem;
        this.descricao = descricao;
        this.ultimo = ultimo;
    }

    int ordem;
    String descricao;
    boolean ultimo;
}
