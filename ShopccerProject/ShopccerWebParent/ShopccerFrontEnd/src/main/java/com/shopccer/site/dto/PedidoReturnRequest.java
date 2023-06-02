package com.shopccer.site.dto;

public class PedidoReturnRequest {

    private Integer idPedido;
    private String razon;
    private String notas;

    public PedidoReturnRequest() {
    }

    public PedidoReturnRequest(Integer idPedido, String razon, String notas) {
        this.idPedido = idPedido;
        this.razon = razon;
        this.notas = notas;
    }

    public Integer getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Integer idPedido) {
        this.idPedido = idPedido;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
