package com.shopccer.site.dto;

public class PedidoReturnResponse {

    private Integer idPedido;

    public PedidoReturnResponse() {

    }

    public PedidoReturnResponse(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }
}
