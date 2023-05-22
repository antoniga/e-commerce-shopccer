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

import java.util.List;

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
                        "momentos. Puede que haya más gente comprando el mismo producto." +
                        " Recarga la página para ver el stock actualizado.");
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

    @Override
    public List<ItemCarro> listItemsCarro(Cliente cliente) {
        return itemCarroRepository.findByCliente(cliente);
    }

    public Double updateCantidad(Integer idProducto, Integer talla, Integer cantidad, Cliente cliente,  String variacionStock) {
        //actualizamos el carro
        itemCarroRepository.updateCantidad(cantidad, talla, cliente.getIdCliente(), idProducto);

        //recuperamos el producto de bd y actualizamos su stock
        Producto productoInBd = productoRepository.findById(idProducto).get();
        Integer stockTallaSeleccionada = productoInBd.getTallaStock().get(talla);

        //si quitamos un producto del carro, lo sumamos al stock
        if("minus".equals(variacionStock)){
            productoInBd.getTallaStock().put(talla,stockTallaSeleccionada + 1);
        }
        //si añadimos un producto al carro, lo quitamos del stock
        if("plus".equals(variacionStock)){
            productoInBd.getTallaStock().put(talla,stockTallaSeleccionada - 1);
        }

        productoRepository.save(productoInBd);

        Producto producto = productoRepository.findById(idProducto).get();
        Double subtotal = producto.getPrecioConDescuento() * cantidad;
        return subtotal;
    }

    @Override
    public void removeProducto(Cliente cliente, Integer idProducto,String talla) {

        Integer tallaInt = Integer.parseInt(talla);
        Producto producto = productoRepository.findById(idProducto).get();
        Integer stockExistente = producto.getTallaStock().get(tallaInt);
        ItemCarro itemCarro = itemCarroRepository.findByClienteAndProductoAndTalla(cliente,producto, tallaInt);
        //recuperamos la cantidad que habia en ese item para devolverlo al stock del producto
        Integer cantidad = itemCarro.getCantidad();
        //actualizamos stock
        producto.getTallaStock().put(tallaInt,stockExistente + cantidad);

        //eliminamos el item del carro
        itemCarroRepository.deleteByClienteAndProductoAAndTalla(cliente.getIdCliente(), idProducto, talla);
    }
}
