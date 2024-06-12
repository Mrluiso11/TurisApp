    package com.lagn.turisapp.Clases;

    import android.graphics.drawable.Drawable;

    public class Sitios {
        private String titulo;
        private String autor;
        private String detalles;
        private String telefono;
        private int megusta;
        private Drawable imagen;
        private Drawable icono;

        public Sitios() {
        }

        public Sitios(String titulo, String autor, String detalles, String telefono, int megusta, Drawable imagen, Drawable icono) {
            this.titulo = titulo;
            this.autor = autor;
            this.detalles = detalles;
            this.telefono = telefono;
            this.megusta = megusta;
            this.imagen = imagen;
            this.icono = icono;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getDetalles() {
            return detalles;
        }

        public void setDetalles(String detalles) {
            this.detalles = detalles;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public int getMegusta() {
            return megusta;
        }

        public void setMegusta(int megusta) {
            this.megusta = megusta;
        }

        public Drawable getImagen() {
            return imagen;
        }

        public void setImagen(Drawable imagen) {
            this.imagen = imagen;
        }

        public Drawable getIcono() {
            return icono;
        }

        public void setIcono(Drawable icono) {
            this.icono = icono;
        }
    }
