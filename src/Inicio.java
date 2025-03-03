import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Inicio extends JFrame{
    private JButton Imagen;
    private JButton InicioSesion;
    private JLabel ObleaDoble;
    private JLabel MerengonPersonal;
    private JLabel FresasCrema;
    private JPanel PagPrincipal;



    public Inicio() {


        Imagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        InicioSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            enlazarInicioSesion();
            dispose();
            }
        });
    }
    public void mostrarPagPrincipal (){
     Inicio en = new Inicio();
     en.setContentPane(en.PagPrincipal);
     en.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     en.setVisible(true);
     en.setSize(1920,1080);
     en.pack();
    }

    public void enlazarInicioSesion(){
        InicioSesion login = new InicioSesion();
        login.setContentPane(login.Mensaje);
        login.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        login.setVisible(true);
        login.pack();
        dispose();
        login.setSize(1920,1080);
    }

    public static void main(String[] args) {
        Inicio is = new Inicio();
        is.setContentPane(is.PagPrincipal);
        is.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        is.setVisible(true);
        is.pack();

        is.setSize(1920,1080);
    }
}
