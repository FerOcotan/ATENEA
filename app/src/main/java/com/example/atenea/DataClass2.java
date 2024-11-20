package com.example.atenea;

public class DataClass2 {
    private String key, uni, materia, dataImage;

    public DataClass2(String uni, String materia,
                      String dataImage) {

        this.uni = uni;
        this.materia = materia;
        this.dataImage = dataImage;

    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUni() {
        return uni;
    }

    public void setUni(String uni) {
        this.uni = uni;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }




    public int getDataImage() {
        return R.drawable.baseline_file_open_24;
    }

    public DataClass2(){

    }
}
