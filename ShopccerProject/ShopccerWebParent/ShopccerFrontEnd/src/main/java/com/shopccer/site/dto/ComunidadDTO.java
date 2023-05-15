package com.shopccer.site.dto;

public class ComunidadDTO {

    private Integer idComunidadDto;
    private String nombre;

    public ComunidadDTO() {

    }

    public ComunidadDTO(Integer idComunidadDto, String nombre) {
        this.idComunidadDto = idComunidadDto;
        this.nombre = nombre;
    }

    public Integer getIdComunidadDto() {
        return idComunidadDto;
    }

    public void setIdComunidadDto(Integer idComunidadDto) {
        this.idComunidadDto = idComunidadDto;
    }

    public String getNOmbre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
