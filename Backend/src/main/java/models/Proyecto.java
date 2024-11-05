package models;

public class Proyecto {
    private Integer idProyecto;
    private String nombre;
    private String inversion;
    private String tiempoVida;
    private String fechaInicio;
    private String fechaFinal;
    private String costo;
    private String electricidadDia;

    public Proyecto() {
    }

    public Proyecto(Integer idProyecto, String nombre, String inversion, String tiempoVIda, String fechaInico,
            String fechaFinal, String costo, String electricidadDia) {
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.inversion = inversion;
        this.tiempoVida = tiempoVIda;
        this.fechaInicio = fechaInico;
        this.fechaFinal = fechaFinal;
        this.costo = costo;
        this.electricidadDia = electricidadDia;
    }

    public Integer getIdProyecto() {
        return this.idProyecto;
    }

    public void setIdProyecto(Integer idProyecto) {
        this.idProyecto = idProyecto;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getInversion() {
        return this.inversion;
    }

    public void setInversion(String inversion) {
        this.inversion = inversion;
    }

    public String getTiempoVida() {
        return this.tiempoVida;
    }

    public void setTiempoVida(String tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFinal() {
        return this.fechaFinal;
    }

    public void setFechaFinal(String fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public String getCosto() {
        return this.costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getElectricidadDia() {
        return this.electricidadDia;
    }

    public void setElectricidadDia(String electricidadDia) {
        this.electricidadDia = electricidadDia;
    }

    public String toString() {
        return "Proyecto [idProyecto=" + idProyecto + ", nombre=" + nombre + ", inversion=" + inversion
                + ", tiempoVida=" + tiempoVida + ", fechaInicio=" + fechaInicio + ", fechaFinal=" + fechaFinal
                + ", costo=" + costo + ", electricidadDia=" + electricidadDia + "]";
    }
}
