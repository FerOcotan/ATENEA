package com.example.atenea;

public class DataClass {

    private String key, codigo, nombre_materia, participantes,
            seccion, hora_inicio, hora_salida, carnet_creador, dataImage;

    public DataClass(String codigomateria, String nombremateria,
                     String participantes, String seccion, String horainicio, String horasalida, String creador,
                     String dataImage) {

        this.codigo = codigomateria;
        this.nombre_materia = nombremateria;
        this.participantes = participantes;
        this.seccion = seccion;
        this.hora_inicio = horainicio;
        this.hora_salida = horasalida;
        this.carnet_creador = creador;
        this.dataImage = dataImage;

    }
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre_materia() {
        return nombre_materia;
    }

    public void setNombre_materia(String nombre_materia) {
        this.nombre_materia = nombre_materia;
    }

    public String getParticipantes() {
        return participantes;
    }

    public void setParticipantes(String participantes) {
        this.participantes = participantes;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }

    public String getCarnet_creador() {
        return carnet_creador;
    }

    public void setCarnet_creador(String carnet_creador) {
        this.carnet_creador = carnet_creador;
    }
    public int getDataImage() {
        return R.drawable.classs2;
    }

    public DataClass(){

    }



}
