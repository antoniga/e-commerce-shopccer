package com.shopccer.admin.utils;

import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteUtil;

import java.util.List;

public class GeneralUtil extends AjusteUtil {
    public GeneralUtil(List<Ajuste> listaAjustes) {
        super(listaAjustes);
    }

    public void updateSiteLogo(String valor){
        super.update("SITE_LOGO", valor);
    }


}
