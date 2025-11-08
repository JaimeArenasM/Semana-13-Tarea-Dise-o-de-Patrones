
package uni.entity;

public class EmpleadoTo {
  //atributos
    private String idempleado;
    private String nombre;
    private String apellidos;
    private String email;
    private String usuario;
    private String clave;
    //constructor

    public EmpleadoTo() {
    }

    public EmpleadoTo(String idempleado, String nombre, String apellidos, String email, String usuario, String clave) {
        this.idempleado = idempleado;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.usuario = usuario;
        this.clave = clave;
    }
    //metodos get y set

    public String getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(String idempleado) {
        this.idempleado = idempleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    
}
