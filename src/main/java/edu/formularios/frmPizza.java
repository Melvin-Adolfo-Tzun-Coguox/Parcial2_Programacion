package edu.formularios;

import edu.pizza.base.Pizza;
import edu.pizza.base.Topping;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class frmPizza {
    // se definen todos los botones, espacios y listas a utilizar
    private JPanel jPanelPrincipal;
    private JComboBox comboBoxToppings;
    private JTextField txtPizza;
    private JButton btnAddIngrediente;
    private JLabel lblTotal;
    private JList lista1;
    private JRadioButton radiobtnsmall;
    private JRadioButton radiobtnmedium;
    private JRadioButton radiobtnbig;
    private JButton btbPrepararPizza;
    private JComboBox comboBoxTipoPizza;
    private JList ListPrepare;


    private List<Topping> ingredientes = new ArrayList<>();

    private List<String> especialidades = new ArrayList<>();

    private DefaultListModel modeloLista = new DefaultListModel();

    private DefaultListModel modeloPrepare = new DefaultListModel();
    public JPanel getjPanelPrincipal() {
        return jPanelPrincipal;
    }



    public frmPizza() {
        //Se llama al  metodo para cargar los datos de las listas
        iniciarSistema();

       //Al escoger un tamaño de pizza se volvera a realizar el calculo del total
        radiobtnsmall.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
        radiobtnmedium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
        radiobtnbig.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularTotal();
            }
        });
        //Se establece que al escoger un tipo de pizza de la lista automaticamente el nombre sera el mismmo

        comboBoxTipoPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarEspecialidades(String.valueOf(comboBoxTipoPizza.getSelectedItem()));
                txtPizza.setText(String.valueOf(comboBoxTipoPizza.getSelectedItem()));
            }
        });
        // Accion al momento de seleccionar mas ingredientes de la lista
        btnAddIngrediente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();

                modeloLista.addElement(ingrediente);
                lista1.setModel(modeloLista);
                calcularTotal();
            }
        });
       //Acciones de clic en preparar pizza con alertas de falta de datos
        btbPrepararPizza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //refresh every time
                modeloPrepare.clear();

                if (txtPizza.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Seleccione un tipo de pízza, o ingrese el nombre de la misma");
                    return;
                }
                if (modeloLista.isEmpty()){
                    JOptionPane.showMessageDialog(null,"La pizza no lleva ingredientes....");
                    return;
                }
                Pizza pizza = new Pizza(txtPizza.getText());
                Topping topi;
                for (int i= 0; i< lista1.getModel().getSize(); i++){
                    topi = (Topping) lista1.getModel().getElementAt(i);
                    pizza.addTopping(topi);
                }
                modeloPrepare.addElement("Preparando...  Pizza "+pizza.getName());
                ListPrepare.setModel(modeloPrepare);
                modeloPrepare.addElement("Añadiendo Toppings... ");
                ListPrepare.setModel(modeloPrepare);

                for (int i= 0; i< lista1.getModel().getSize(); i++){
                    Topping t = (Topping) lista1.getModel().getElementAt(i);
                    modeloPrepare.addElement(t.getNombre());
                    ListPrepare.setModel(modeloPrepare);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException f) {
                        f.printStackTrace();
                    }

                }
                modeloPrepare.addElement("---PIZZA IS READY----- .");
                ListPrepare.setModel(modeloPrepare);
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null,"Su Pizza "+pizza.getName()+" esta Lista, disfrutela..");

            }
        });
        //Accion de doble clic en los ingredientes, eliminandolos
        lista1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {

                    // Double-click detected
                    int index = list.locationToIndex(evt.getPoint());
                    modeloLista.remove(index);
                    calcularTotal();
                }
            }
        });
    }


    //Sistema principal encargado de llenar la lista de opciones de tipo de pizza y ingredientes

    private void iniciarSistema(){
        ingredientes.add(new Topping("Camarones",15));
        ingredientes.add(new Topping("Champiñones",10));
        ingredientes.add(new Topping("Mozzarella",8));
        ingredientes.add(new Topping("Cebolla",5));
        ingredientes.add(new Topping("Tomate",5));
        ingredientes.add(new Topping("Pepperoni",10));
        ingredientes.add(new Topping("Jamon",10));
        ingredientes.add(new Topping("Salchicha",5));

        DefaultComboBoxModel model = new DefaultComboBoxModel(ingredientes.toArray());
        comboBoxToppings.setModel(model);

        especialidades.add("Al Gusto");
        especialidades.add("Hawaiana");
        especialidades.add("Americana");
        especialidades.add("Extra Queso");
        especialidades.add("Napolitana");
        especialidades.add("5 carnes");

        DefaultComboBoxModel modelTipoPizza = new DefaultComboBoxModel(especialidades.toArray());
        comboBoxTipoPizza.setModel(modelTipoPizza);

    }

   //Metodo para calcular el total recorriendo la lista de topping añadidos sumando
    //sus precios y añadiendo el porcentaje extra dependiedo el tamaño
    private void calcularTotal(){
        double total =0;
        for (int i= 0; i< lista1.getModel().getSize(); i++){
            Topping t = (Topping) lista1.getModel().getElementAt(i);
            total+=t.getPrecio();
        }

        if (radiobtnbig.isSelected()){
            total=total*(1.3);
        } else if (radiobtnmedium.isSelected()) {
            total=total*(1.2);
        }
        lblTotal.setText(String.valueOf(total));
    }

    //Metodo de ingredientes añadidos dependiendo el tipo de pizza seleccionado

    private void cargarEspecialidades(String tipo){
        modeloLista.clear();
        Topping ingrediente = (Topping) comboBoxToppings.getSelectedItem();
        modeloLista.addElement(new Topping("Tomate",5));
        modeloLista.addElement(new Topping("Mozzarella",5));
        switch (tipo) {
            case "Hawaiana":
                modeloLista.addElement(new Topping("Piña",10));
                break;
            case "Americana":
                modeloLista.addElement(new Topping("Salsa Especial",10));
                break;
            case "Extra Queso" :
                modeloLista.addElement(new Topping("Trozos de chorizo",10));
                break;
            case "Napolitana" :
                modeloLista.addElement(new Topping("Aceite de olivo",10));
                break;
            case "5 carnes" :
                modeloLista.addElement(new Topping("Champiñones",10));
                break;

            default:
                modeloLista.clear();
        }
        lista1.setModel(modeloLista);
        this.calcularTotal();

    }


}
