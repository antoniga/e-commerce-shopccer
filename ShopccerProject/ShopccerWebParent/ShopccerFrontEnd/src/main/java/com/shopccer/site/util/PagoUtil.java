package com.shopccer.site.util;

import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteUtil;

import java.util.List;

public class PagoUtil extends AjusteUtil {
    public PagoUtil(List<Ajuste> listaAjustes) {
        super(listaAjustes);
    }

    public String getURL() {
        return super.getValor("PAYPAL_API_BASE_URL");
    }

    public String getClientID() {
        return super.getValor("PAYPAL_API_CLIENT_ID");
    }

    public String getClientSecret() {
        return super.getValor("PAYPAL_API_CLIENT_SECRET");
    }

}
