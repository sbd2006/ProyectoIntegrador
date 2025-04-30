package Modelo;

public class CrearUsuario {
    private String nombre;
    private String apellido;
    private String telefono;
    private String direccion;
    private String usuario;
    private String contrasena;

    public CrearUsuario(String nombre, String apellido, String telefono, String direccion, String usuario, String contrasena) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuario = usuario;
        this.contrasena = contrasena;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getTelefono() { return telefono; }
    public String getDireccion() { return direccion; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
}
