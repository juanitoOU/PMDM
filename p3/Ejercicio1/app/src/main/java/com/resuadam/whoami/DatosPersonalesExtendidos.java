package com.resuadam.whoami;

public class DatosPersonalesExtendidos {


        /**
         * Construye un nuevo objeto de datos personales extendidos
         * a partir de los datos individuales
         *
         * @param n El nombre, como texto.
         * @param a La dirección, como texto.
         * @param e El e.mail, como texto.
         */
        public DatosPersonalesExtendidos(String n, String p, String a, String e, String t) {
            this.setNombre( n );
            this.setApellidos( p );
            this.setDireccion( a );
            this.setEmail( e );
            this.setTelefono( t );
        }

        final public String getNombre() {
            return nombre;
        }

        final public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        final public String getApellidos() {
        return apellidos;
    }

        final public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

        final public String getEmail() {
            return email;
        }

        final public void setEmail(String email) {
            this.email = email;
        }

        final public String getDireccion() {
            return direccion;
        }

        final public void setDireccion(String direccion) {
            this.direccion = direccion;
        }

        final public String getTelefono() {
        return telefono;
    }

        final public void setTelefono(String telefono) {this.telefono = telefono;}


        private String nombre;
        private String email;
        private String direccion;
        private String apellidos;
        private String telefono ;


    }

