package com.shopccer.common.entity;

import java.util.List;

public class AjusteUtil {

    private List<Ajuste> listaAjustes;

    public AjusteUtil(List<Ajuste> listaAjustes) {
        this.listaAjustes = listaAjustes;
    }

    public Ajuste get(String clave){
        Integer index = listaAjustes.indexOf(new Ajuste(clave));

        if(index >= 0 ){
            return listaAjustes.get(index);
        }

        return null;
    }

    public String getValor(String clave){
        Ajuste ajuste = get(clave);

        if(ajuste != null){
            return ajuste.getValor();
        }

        return null;
    }

    public void update(String clave, String valor){
        Ajuste ajuste = get(clave);

        if(ajuste !=null && valor !=null){
            ajuste.setValor(valor);
        }
    }

    public List<Ajuste> list(){
        return listaAjustes;
    }
}
