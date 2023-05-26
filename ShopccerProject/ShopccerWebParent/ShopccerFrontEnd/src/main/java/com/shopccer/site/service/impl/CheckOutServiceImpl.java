package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Pais;
import com.shopccer.site.checkout.CheckoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CheckOutServiceImpl implements com.shopccer.site.service.CheckOutService {

    @Override
    public CheckoutInfo prepareCheckout(List<ItemCarro> itemsCarro) {
        CheckoutInfo checkoutInfo = new CheckoutInfo();

        Double costeProducto = calcularPrecioProducto(itemsCarro);
        Double totalCoste = calcularTotalProducto(itemsCarro);
        Double gastosEnvio = calcularGastosEnvio(itemsCarro);
        Double totalPago = totalCoste + gastosEnvio;

        checkoutInfo.setCosteProducto(costeProducto);
        checkoutInfo.setTotalProducto(totalCoste);
        checkoutInfo.setCosteEnvio(gastosEnvio);
        checkoutInfo.setTotalPago(totalPago);

        checkoutInfo.setDiasEntrega(calcularDiasEntrega(itemsCarro));

        return checkoutInfo;
    }

    private Double calcularGastosEnvio(List<ItemCarro> cartItems){

        Double costeEnvio = 0.0;

        Cliente cliente = cartItems.get(0).getCliente();
        Pais pais = cliente.getPais();

        if("España".equalsIgnoreCase(pais.getNombre())){
            costeEnvio = 7.95;
            String comunidad = cliente.getComunidad();
            if(comunidad.contains("Baleares") || comunidad.contains("Canarias")){
                costeEnvio += 2.50;
            }

        }else{
            costeEnvio = 17.50;
        }

        return costeEnvio;
    }

    private Integer calcularDiasEntrega(List<ItemCarro> cartItems){

        Integer diasEntrega = 0;

        Cliente cliente = cartItems.get(0).getCliente();
        Pais pais = cliente.getPais();

        if("España".equalsIgnoreCase(pais.getNombre())){
            diasEntrega = 3;
            String comunidad = cliente.getComunidad();
            if(comunidad.contains("Baleares") || comunidad.contains("Canarias")){
                diasEntrega += 1;
            }

        }else{
            diasEntrega = 9;
        }

        return diasEntrega;
    }

    private Double calcularTotalProducto(List<ItemCarro> cartItems) {
        Double total = 0.0;

        for (ItemCarro item : cartItems) {
            total += item.getSubtotal();
        }

        total = Math.round(total * 100.0) / 100.0;

        return total;
    }

    private Double calcularPrecioProducto(List<ItemCarro> itemCarros) {
        Double precio = 0.0;

        for (ItemCarro item : itemCarros) {
            precio += item.getCantidad() * item.getProducto().getPrecio();
        }

        precio = Math.round(precio * 100.0) / 100.0;

        return precio;
    }
}
