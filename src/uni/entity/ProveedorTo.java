package uni.entity;
public class ProveedorTo
{
    private String idproveedor;
    private String razonsocial;
    private String direccion;
    private String ruc;
    private String telefono;

    public ProveedorTo() {
    }

    public ProveedorTo(String idproveedor, String razonsocial, String direccion, String ruc, String telefono) {
        this.idproveedor = idproveedor;
        this.razonsocial = razonsocial;
        this.direccion = direccion;
        this.ruc = ruc;
        this.telefono = telefono;
    }

    public String getIdproveedor() {
        return idproveedor;
    }

    public void setIdproveedor(String idproveedor) {
        this.idproveedor = idproveedor;
    }

    public String getRazonsocial() {
        return razonsocial;
    }

    public void setRazonsocial(String razonsocial) {
        this.razonsocial = razonsocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
