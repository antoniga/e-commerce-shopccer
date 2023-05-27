package com.shopccer.site.checkout;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CheckoutInfo {

    private Double costeProducto;
    private Double totalProducto;
    private Double costeEnvio;
    private Double totalPago;
    private int diasEntrega;

    public Double getCosteProducto() {
        return costeProducto;
    }

    public void setCosteProducto(Double costeProducto) {
        this.costeProducto = costeProducto;
    }

    public Double getTotalProducto() {
        return totalProducto;
    }

    public void setTotalProducto(Double totalProducto) {
        this.totalProducto = totalProducto;
    }

    public Double getCosteEnvio() {
        return costeEnvio;
    }

    public void setCosteEnvio(Double costeEnvio) {
        this.costeEnvio = costeEnvio;
    }

    public Double getTotalPago() {
        return totalPago;
    }

    public void setTotalPago(Double totalPago) {
        this.totalPago = totalPago;
    }

    public int getDiasEntrega() {
        return diasEntrega;
    }

    public void setDiasEntrega(int diasEntrega) {
        this.diasEntrega = diasEntrega;
    }

    public Date getFechaEntrega() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, diasEntrega);

        return calendar.getTime();
    }

    public String getPaymentTotal4PayPal() {
        DecimalFormat formatter = new DecimalFormat("##.##", new DecimalFormatSymbols(Locale.ENGLISH));
        return formatter.format(totalPago);
    }
}
