package models;

public class Transaccion {
    private Integer id;
    private String tipo;
    private String fecha;
    private String detalles;
    private String tabla;

    // Constructor, getters y setters
    public Transaccion(Integer id, String tipo, String fecha, String detalles, String tabla) {
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.detalles = detalles;
        this.tabla = tabla;
    }

    public Transaccion() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDetalles() {
        return this.detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public String getTabla() {
        return this.tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }

    @Override
    public String toString() {
        return "Transaccion{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", detalles='" + detalles + '\'' +
                '}';
    }

}
