import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InicioSesion extends JFrame {
    public JPanel Mensaje;
    private JLabel MensajeBienvenida;
    private JLabel MensajeLog;
    private JLabel MensajeUser;
    private JTextField IngresoUser;
    private JPasswordField IngresoPassword;
    private JLabel MensajePassword;
    private JButton Login;
    private JButton CreateUser;
    private JButton volverButton;
    Connection conexion;
    ResultSet rs;
    Statement st;
    PreparedStatement ps;



    public InicioSesion(){
        Login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Validacion();
            }
        });
        CreateUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCrearUsuario();
                dispose();
            }
        });
        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarPag();
                setSize(1920,1080);
                dispose();
            }
        });
    }

    void conectar(){
        try{
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/PostresMariaJose","root","OH{c<6H1#cQ%F69$i");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }


    void Validacion(){
        conectar();

        String usuario = IngresoUser.getText();
        String pass = String.valueOf(IngresoPassword.getText());
        try {
            st = conexion.createStatement();
            rs = st.executeQuery("select * from usuarios where Usuario = '"+usuario+"' and Pass = '"+pass+"'");
            if (rs.next()){

                if(usuario.contains("AdminPMJ2024")){
                    JOptionPane.showMessageDialog(null,"Bienvenido Administrador");
                    Administrador enlace = new Administrador();
                    enlace.mostrarAdministrador();
                    dispose();


                }else{
                    JOptionPane.showMessageDialog(null,"Bienvenido Cliente");
                    PantallaCliente enlace = new PantallaCliente();
                    enlace.mostrarCliente();
                    dispose();
                }

            }else {
                JOptionPane.showMessageDialog(null,"Las credenciales no son correctas");
            }
        }catch (SQLException e){
            JOptionPane.showMessageDialog(null, "Error"+ e.getMessage());
        }
    }



    public void mostrarCrearUsuario(){
        CrearUsuario enlace = new CrearUsuario();
        enlace.mostrarVentanaEmergente();
    }


    public void mostrarInicioSesion(){
        InicioSesion in = new InicioSesion();
        in.setContentPane(in.Mensaje);
        in.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        in.setVisible(true);
        in.pack();
        in.setSize(1920,1080);
    }

    public void mostrarPag (){
        Inicio enlace = new Inicio();
        enlace.mostrarPagPrincipal();

    }

}