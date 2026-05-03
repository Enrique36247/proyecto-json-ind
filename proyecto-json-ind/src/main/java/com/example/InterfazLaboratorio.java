package com.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Interfaz gráfica para gestionar el laboratorio de robots
 * Utiliza Swing para la GUI
 * 
 */
public class InterfazLaboratorio extends JFrame {
    
    // Componentes de la interfaz
    private JTextField txtId;
    private JTextField txtNombre;
    private JTextField txtModelo;
    private JTextField txtFuncion;
    private JTextField txtAño;
    private JComboBox<String> cmbEstado;
    private JTextField txtUbicacion;
    private JTextField txtBuscar;
    
    private JTable tablaRobots;
    private DefaultTableModel modeloTabla;
    
    private JButton btnAñadir;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnBuscar;
    private JButton btnLimpiar;
    private JButton btnActualizar;
    
    private JLabel lblMensaje;
    private JLabel lblContador;
    
    // Gestor de datos
    private GestorJSON gestorJSON;
    private List<Robot> listaRobots;
    private String idSeleccionado = null;

    public InterfazLaboratorio() {
        // Inicializar gestor y cargar datos
        gestorJSON = new GestorJSON();
        listaRobots = gestorJSON.cargarDatos();
        
        // Configurar ventana
        setTitle("🧪 Laboratorio de Robots - Gestión de Inventario");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
        
        // Inicializar componentes
        inicializarComponentes();
        
        // Cargar datos en la tabla
        actualizarTabla();
        
        // Mostrar ventana
        setVisible(true);
    }

    /**
     * Inicializa todos los componentes de la interfaz
     */
    private void inicializarComponentes() {
        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // ===== PANEL SUPERIOR: Título =====
        JPanel panelTitulo = new JPanel();
        JLabel lblTitulo = new JLabel("🧪 Laboratorio de Robots - Gestión de Inventario");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblTitulo);
        
        // ===== PANEL IZQUIERDO: Formulario =====
        JPanel panelFormulario = crearPanelFormulario();
        
        // ===== PANEL CENTRO: Tabla =====
        JPanel panelTabla = crearPanelTabla();
        
        // ===== PANEL INFERIOR: Botones y mensajes =====
        JPanel panelInferior = crearPanelInferior();
        
        // Añadir paneles al principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.WEST);
        panelPrincipal.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        
        add(panelPrincipal);
    }

    /**
     * Crea el panel del formulario de entrada de datos
     * 
     */
    private JPanel crearPanelFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            "📝 Datos del Robot",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 102, 204)
        ));
        panel.setPreferredSize(new Dimension(320, 0));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        
        // Etiquetas y campos
        String[] etiquetas = {"ID:", "Nombre:", "Modelo:", "Función:", 
                             "Año Fabricación:", "Estado:", "Ubicación:"};
        JComponent[] campos = new JComponent[7];
        
        // Inicializar campos
        txtId = new JTextField(15);
        txtId.setEditable(false); // El ID se genera automáticamente
        txtId.setBackground(new Color(240, 240, 240));
        
        txtNombre = new JTextField(15);
        txtModelo = new JTextField(15);
        txtFuncion = new JTextField(15);
        txtAño = new JTextField(15);
        
        cmbEstado = new JComboBox<>(new String[]{"ACTIVO", "MANTENIMIENTO", "INACTIVO"});
        
        txtUbicacion = new JTextField(15);
        
        campos[0] = txtId;
        campos[1] = txtNombre;
        campos[2] = txtModelo;
        campos[3] = txtFuncion;
        campos[4] = txtAño;
        campos[5] = cmbEstado; // Usamos el combo como campo
        campos[6] = txtUbicacion;
        
        // Añadir componentes al panel
        int fila = 0;
        
        // ID (solo lectura)
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.weightx = 0.3;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        panel.add(txtId, gbc);
        fila++;
        
        // Resto de campos
        for (int i = 1; i < etiquetas.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = fila;
            gbc.weightx = 0.3;
            panel.add(new JLabel(etiquetas[i]), gbc);
            gbc.gridx = 1;
            gbc.weightx = 0.7;
            
            panel.add(campos[i], gbc);
            fila++;
        }
        
        // Botones de acción
        JPanel panelBotones = new JPanel(new GridLayout(3, 2, 5, 5));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        
        btnAñadir = new JButton("Añadir");
        btnModificar = new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnBuscar = new JButton("Buscar");
        btnLimpiar = new JButton("Limpiar");
        btnActualizar = new JButton("Actualizar");
        
        // Estilos de botones
        estilizarBoton(btnAñadir, Color.GREEN);
        estilizarBoton(btnModificar, Color.BLUE);
        estilizarBoton(btnEliminar, Color.RED);
        estilizarBoton(btnBuscar, new Color(153, 102, 0));
        estilizarBoton(btnLimpiar, new Color(102, 102, 102));
        estilizarBoton(btnActualizar, new Color(102, 51, 153));

        btnAñadir.setForeground(Color.GREEN);
        btnModificar.setForeground(Color.BLUE);
        btnEliminar.setForeground(Color.RED);
        btnBuscar.setForeground(new Color(153, 102, 0));
        btnLimpiar.setForeground(new Color(102, 102, 102));
        btnActualizar.setForeground(new Color(102, 51, 153));
        
        panelBotones.add(btnAñadir);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnActualizar);
        
        gbc.gridx = 0;
        gbc.gridy = fila;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(panelBotones, gbc);
        
        // Asignar acciones a los botones
        btnAñadir.addActionListener(e -> añadirRobot());
        btnModificar.addActionListener(e -> modificarRobot());
        btnEliminar.addActionListener(e -> eliminarRobot());
        btnBuscar.addActionListener(e -> buscarRobot());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnActualizar.addActionListener(e -> {
            listaRobots = gestorJSON.cargarDatos();
            actualizarTabla();
            mostrarMensaje("Datos actualizados desde el archivo", false);
        });
        
        return panel;
    }

    /**
     * Crea el panel de la tabla de datos
     * 
     */
    private JPanel crearPanelTabla() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
            "Inventario de Robots",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            new Color(0, 102, 204)
        ));
        
        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelBusqueda.add(new JLabel("🔎 Buscar:"));
        txtBuscar = new JTextField(30);
        txtBuscar.setToolTipText("Buscar por nombre, modelo o función...");
        panelBusqueda.add(txtBuscar);
        
        JButton btnBuscarTabla = new JButton("Buscar");
        btnBuscarTabla.addActionListener(e -> buscarEnTabla());
        panelBusqueda.add(btnBuscarTabla);
        
        JButton btnMostrarTodos = new JButton("Mostrar Todos");
        btnMostrarTodos.addActionListener(e -> {
            txtBuscar.setText("");
            actualizarTabla();
        });
        panelBusqueda.add(btnMostrarTodos);
        
        // Tabla
        String[] columnas = {"ID", "Nombre", "Modelo", "Función", "Año", "Estado", "Ubicación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer celdas no editables
            }
        };
        
        tablaRobots = new JTable(modeloTabla);
        tablaRobots.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaRobots.setRowHeight(25);
        tablaRobots.getTableHeader().setReorderingAllowed(false);
        
        // Listener para selección de fila
        tablaRobots.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = tablaRobots.getSelectedRow();
                if (fila >= 0) {
                    cargarRobotEnFormulario(fila);
                }
            }
        });
        
        // Scroll para la tabla
        JScrollPane scrollTabla = new JScrollPane(tablaRobots);
        scrollTabla.setPreferredSize(new Dimension(0, 300));
        
        panel.add(panelBusqueda, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);
        
        return panel;
    }

    /**
     * Crea el panel inferior con mensajes y contador
     * 
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        
        lblMensaje = new JLabel("Listo para trabajar");
        lblMensaje.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblMensaje.setForeground(new Color(0, 102, 102));
        
        lblContador = new JLabel("Total: 0 robots");
        lblContador.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblContador.setForeground(new Color(0, 102, 204));
        
        JPanel panelMensaje = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelMensaje.add(lblMensaje);
        
        JPanel panelContador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelContador.add(lblContador);
        
        panel.add(panelMensaje, BorderLayout.WEST);
        panel.add(panelContador, BorderLayout.EAST);
        
        return panel;
    }

    /**
     * Aplica estilo a un botón
     * 
     */
    private void estilizarBoton(JButton boton, Color color) {
        boton.setBackground(color);
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1),
            BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));
    }

    /**
     * Actualiza la tabla con los datos de la lista de robots
     * 
     */
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        
        for (Robot robot : listaRobots) {
            Object[] fila = {
                robot.getId(),
                robot.getNombre(),
                robot.getModelo(),
                robot.getFuncion(),
                robot.getAñoFabricacion(),
                robot.getEstado(),
                robot.getUbicacion()
            };
            modeloTabla.addRow(fila);
        }
        
        lblContador.setText("Total: " + listaRobots.size() + " robots");
    }

    /**
     * Carga los datos de un robot de la tabla al formulario
     * 
     */
    private void cargarRobotEnFormulario(int fila) {
        String id = (String) modeloTabla.getValueAt(fila, 0);
        
        for (Robot robot : listaRobots) {
            if (robot.getId().equals(id)) {
                txtId.setText(robot.getId());
                txtNombre.setText(robot.getNombre());
                txtModelo.setText(robot.getModelo());
                txtFuncion.setText(robot.getFuncion());
                txtAño.setText(String.valueOf(robot.getAñoFabricacion()));
                cmbEstado.setSelectedItem(robot.getEstado());
                txtUbicacion.setText(robot.getUbicacion());
                idSeleccionado = id;
                break;
            }
        }
    }

    /**
     * Añade un nuevo robot
     * 
     */
    private void añadirRobot() {
        try {
            Robot robot = new Robot();
            
            // Generar ID automáticamente
            robot.setId(gestorJSON.generarNuevoId(listaRobots));
            
            // Obtener datos del formulario
            robot.setNombre(txtNombre.getText().trim());
            robot.setModelo(txtModelo.getText().trim());
            robot.setFuncion(txtFuncion.getText().trim());
            robot.setAñoFabricacion(Integer.parseInt(txtAño.getText().trim()));
            robot.setEstado((String) cmbEstado.getSelectedItem());
            robot.setUbicacion(txtUbicacion.getText().trim());
            
            // Validar
            String error = gestorJSON.validarRobot(robot);
            if (error != null) {
                mostrarMensaje("Error: " + error, true);
                return;
            }
            
            // Verificar que no exista otro robot con el mismo ID
            for (Robot r : listaRobots) {
                if (r.getId().equals(robot.getId())) {
                    mostrarMensaje("Error: Ya existe un robot con ese ID", true);
                    return;
                }
            }
            
            // Añadir a la lista
            listaRobots.add(robot);
            
            // Guardar en JSON
            if (gestorJSON.guardarDatos(listaRobots)) {
                actualizarTabla();
                limpiarCampos();
                mostrarMensaje("Robot añadido correctamente", false);
            } else {
                listaRobots.remove(robot);
                mostrarMensaje("Error al guardar en el archivo JSON", true);
            }
            
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: El año debe ser un número válido", true);
        } catch (Exception e) {
            mostrarMensaje("Error al añadir: " + e.getMessage(), true);
        }
    }

    /**
     * Modifica un robot existente
     * 
     */
    private void modificarRobot() {
        if (idSeleccionado == null) {
            mostrarMensaje("Seleccione un robot de la tabla para modificar", true);
            return;
        }
        
        try {
            Robot robotModificado = null;
            
            for (Robot robot : listaRobots) {
                if (robot.getId().equals(idSeleccionado)) {
                    robot.setNombre(txtNombre.getText().trim());
                    robot.setModelo(txtModelo.getText().trim());
                    robot.setFuncion(txtFuncion.getText().trim());
                    robot.setAñoFabricacion(Integer.parseInt(txtAño.getText().trim()));
                    robot.setEstado((String) cmbEstado.getSelectedItem());
                    robot.setUbicacion(txtUbicacion.getText().trim());
                    robotModificado = robot;
                    break;
                }
            }
            
            if (robotModificado == null) {
                mostrarMensaje("Error: Robot no encontrado", true);
                return;
            }
            
            // Validar
            String error = gestorJSON.validarRobot(robotModificado);
            if (error != null) {
                mostrarMensaje("Error: " + error, true);
                return;
            }
            
            // Guardar en JSON
            if (gestorJSON.guardarDatos(listaRobots)) {
                actualizarTabla();
                mostrarMensaje("Robot modificado correctamente", false);
            } else {
                mostrarMensaje("Error al guardar en el archivo JSON", true);
            }
            
        } catch (NumberFormatException e) {
            mostrarMensaje("Error: El año debe ser un número válido", true);
        } catch (Exception e) {
            mostrarMensaje("Error al modificar: " + e.getMessage(), true);
        }
    }

    /**
     * Elimina un robot
     * 
     */
    private void eliminarRobot() {
        if (idSeleccionado == null) {
            mostrarMensaje("Seleccione un robot de la tabla para eliminar", true);
            return;
        }
        
        // Confirmar eliminación
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea eliminar el robot con ID: " + idSeleccionado + "?",
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            Robot robotAEliminar = null;
            
            for (Robot robot : listaRobots) {
                if (robot.getId().equals(idSeleccionado)) {
                    robotAEliminar = robot;
                    break;
                }
            }
            
            if (robotAEliminar != null) {
                listaRobots.remove(robotAEliminar);
                
                if (gestorJSON.guardarDatos(listaRobots)) {
                    actualizarTabla();
                    limpiarCampos();
                    mostrarMensaje("Robot eliminado correctamente", false);
                } else {
                    listaRobots.add(robotAEliminar);
                    mostrarMensaje("Error al guardar en el archivo JSON", true);
                }
            }
        }
    }

    /**
     * Busca robots según criterios
     * 
     */
    private void buscarRobot() {
        String criterio = txtBuscar.getText().trim().toLowerCase();
        
        if (criterio.isEmpty()) {
            mostrarMensaje("Ingrese un criterio de búsqueda", true);
            return;
        }
        
        List<Robot> resultados = listaRobots.stream()
            .filter(r -> r.getNombre().toLowerCase().contains(criterio) ||
                        r.getModelo().toLowerCase().contains(criterio) ||
                        r.getFuncion().toLowerCase().contains(criterio) ||
                        r.getId().toLowerCase().contains(criterio) ||
                        r.getUbicacion().toLowerCase().contains(criterio))
            .collect(Collectors.toList());
        
        if (resultados.isEmpty()) {
            mostrarMensaje("No se encontraron robots con ese criterio", true);
        } else {
            modeloTabla.setRowCount(0);
            for (Robot robot : resultados) {
                Object[] fila = {
                    robot.getId(),
                    robot.getNombre(),
                    robot.getModelo(),
                    robot.getFuncion(),
                    robot.getAñoFabricacion(),
                    robot.getEstado(),
                    robot.getUbicacion()
                };
                modeloTabla.addRow(fila);
            }
            lblContador.setText("Resultados: " + resultados.size() + " robots");
            mostrarMensaje("Se encontraron " + resultados.size() + " resultados", false);
        }
    }

    /**
     * Busca en tiempo real en la tabla
     * 
     */
    private void buscarEnTabla() {
        String criterio = txtBuscar.getText().trim().toLowerCase();
        
        if (criterio.isEmpty()) {
            actualizarTabla();
            return;
        }
        
        modeloTabla.setRowCount(0);
        
        for (Robot robot : listaRobots) {
            if (robot.getNombre().toLowerCase().contains(criterio) ||
                robot.getModelo().toLowerCase().contains(criterio) ||
                robot.getFuncion().toLowerCase().contains(criterio) ||
                robot.getId().toLowerCase().contains(criterio) ||
                robot.getUbicacion().toLowerCase().contains(criterio)) {
                
                Object[] fila = {
                    robot.getId(),
                    robot.getNombre(),
                    robot.getModelo(),
                    robot.getFuncion(),
                    robot.getAñoFabricacion(),
                    robot.getEstado(),
                    robot.getUbicacion()
                };
                modeloTabla.addRow(fila);
            }
        }
        
        lblContador.setText("Resultados: " + modeloTabla.getRowCount() + " robots");
    }

    /**
     * Limpia los campos del formulario
     * 
     */
    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtModelo.setText("");
        txtFuncion.setText("");
        txtAño.setText("");
        cmbEstado.setSelectedIndex(0);
        txtUbicacion.setText("");
        idSeleccionado = null;
        tablaRobots.clearSelection();
        mostrarMensaje("Campos limpiados", false);
    }

    /**
     * Muestra un mensaje en la etiqueta correspondiente
     * 
     */
    private void mostrarMensaje(String mensaje, boolean esError) {
        lblMensaje.setText(mensaje);
        if (esError) {
            lblMensaje.setForeground(new Color(204, 51, 51));
        } else {
            lblMensaje.setForeground(new Color(0, 102, 102));
        }
    }

    /**
     * Método principal
     * 
     */
    public static void main(String[] args) {
        // Configurar aspecto visual
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No se pudo configurar el aspecto: " + e.getMessage());
        }
        
        // Ejecutar en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new InterfazLaboratorio();
        });
    }
}