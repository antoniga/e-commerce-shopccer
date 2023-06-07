package com.shopccer.site.service;

import com.shopccer.common.entity.ItemCarro;
import com.shopccer.site.checkout.CheckoutInfo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface CheckOutService {
    CheckoutInfo prepareCheckout(List<ItemCarro> itemsCarro);
}
