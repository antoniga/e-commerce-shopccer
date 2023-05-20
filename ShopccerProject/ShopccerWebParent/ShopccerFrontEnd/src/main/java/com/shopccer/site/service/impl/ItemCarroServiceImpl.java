package com.shopccer.site.service.impl;

import com.shopccer.common.entity.Cliente;
import com.shopccer.common.entity.ItemCarro;
import com.shopccer.common.entity.Producto;
import com.shopccer.site.exception.ItemCarroException;
import com.shopccer.site.repository.ItemCarroRepository;
import com.shopccer.site.repository.ProductoRepository;
import com.shopccer.site.service.ItemCarroService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ItemCarroServiceImpl implements ItemCarroService {

    @Autowired private ItemCarroRepository itemCarroRepository;
    @Autowired private ProductoRepository productoRepository;

    @Override
    public Integer addProduct(Integer idProducto, Integer talla, Integer cantidad, Cliente cliente) throws ItemCarroException {

        Integer updatedCantidad = cantidad;
        Producto producto = productoRepository.findById(idProducto).get();

        ItemCarro cartItem = itemCarroRepository.findByClienteAndProductoAndTalla(cliente, producto, talla);
        Integer stockTallaSeleccionada = producto.getTallaStock().get(talla);


        if (cartItem != null) { //el elemento del carrito ya existe
            updatedCantidad = cartItem.getCantidad() + cantidad;

            if (cantidad > stockTallaSeleccionada) { //la cantidad seleccionada es mayor que el stock disponible
                throw new ItemCarroException("No se puede añadir " + cantidad + " item(s)"
                        + " por que ya hay " + cartItem.getCantidad() + " item(s) "
                        + "en tu carro de la compra. No hay tanto stock disponible en estos " +
                        "momentos para el producto.");
            }else{
                //Actualizamos el stock para esa talla
                producto.getTallaStock().put(talla,stockTallaSeleccionada-cantidad);
                productoRepository.save(producto);
            }
        } else {
            //Actualizamos el stock para esa talla
            producto.getTallaStock().put(talla,stockTallaSeleccionada-cantidad);
            productoRepository.save(producto);
            //y creamos un nuevo elemento del carrito
            cartItem = new ItemCarro();
            cartItem.setCliente(cliente);
            cartItem.setProducto(producto);
            cartItem.setTalla(talla);
        }

        cartItem.setCantidad(updatedCantidad);
        itemCarroRepository.save(cartItem);

        return updatedCantidad;
    }
}
