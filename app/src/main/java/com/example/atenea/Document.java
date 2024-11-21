package com.example.atenea;

public class Document {
    private String keyLista; // Clave única de la lista en Firebase
    private String materia;
    private String uni;

    public Document(String keyLista, String materia, String uni) {
        this.keyLista = keyLista;
        this.materia = materia;
        this.uni = uni;
    }

    // Getters
    public String getKeyLista() {
        return keyLista;
    }

    public String getMateria() {
        return materia;
    }

    public String getUni() {
        return uni;
    }

    // Setters (opcional, si planeas modificar datos)
    public void setKeyLista(String keyLista) {
        this.keyLista = keyLista;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }
}
