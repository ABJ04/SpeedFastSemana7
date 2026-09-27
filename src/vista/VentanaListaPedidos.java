package vista;

import dao.PedidoDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {

    private JTable tablaPedidos;
    private DefaultTableModel modeloTabla;
    private JButton btnActualizar;

    public VentanaListaPedidos() {

        setTitle("Lista de pedidos");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Dirección");
        modeloTabla.addColumn("Tipo");
        modeloTabla.addColumn("Estado");

        tablaPedidos = new JTable(modeloTabla);
        btnActualizar = new JButton("Actualizar");

        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
        add(btnActualizar, BorderLayout.SOUTH);

        btnActualizar.addActionListener(e -> cargarPedidos());

        cargarPedidos();

        setVisible(true);
    }

    private void cargarPedidos() {

        modeloTabla.setRowCount(0);

        PedidoDAO pedidoDAO = new PedidoDAO();

        for (Object[] pedido : pedidoDAO.listarTodos()) {
            modeloTabla.addRow(pedido);
        }
    }
}