package com.clover.cloverqr.Model;

public class Planta {
        private String postid;
        private String postimage;
        private String description;
        private String publisher;
        private String nombre;
        private String category;

        public Planta (String postid, String postimage, String description, String publisher, String nombre) {
            this.postid = postid;
            this.postimage = postimage;
            this.description = description;
            this.publisher = publisher;
            this.nombre = nombre;
        }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public Planta () {

    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}