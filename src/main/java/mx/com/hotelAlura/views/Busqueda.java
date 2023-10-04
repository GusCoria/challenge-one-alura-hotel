package mx.com.hotelAlura.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import mx.com.hotelAlura.dao.HuespedDao;
import mx.com.hotelAlura.dao.ReservaDao;
import mx.com.hotelAlura.modelos.Huesped;
import mx.com.hotelAlura.modelos.Reserva;
import mx.com.hotelAlura.utilidades.JPAUtils;

@SuppressWarnings("serial")
public class Busqueda extends JFrame {
	
	private DefaultTableModel modeloReservas;
	private DefaultTableModel modeloHuespedes;
	private Map<JTable, DefaultTableModel> modelosDeTabla = new HashMap<>();
	private JTabbedPane tabbedPane;
	private int currentTab = 0; 
	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTable tablaHuesped;
	private JTable tablaReservas;
	private DefaultTableModel modelo;
	private DefaultTableModel modeloHuesped;
	private JLabel labelAtras;
	private JLabel labelExit;
	int xMouse, yMouse;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public Busqueda() {
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 571);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		setUndecorated(true);
		
		txtBuscar = new JTextField();
		txtBuscar.setBounds(536, 127, 193, 31);
		txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		
		
		JLabel lblNewLabel_4 = new JLabel("SISTEMA DE BÚSQUEDA");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Roboto Black", Font.BOLD, 24));
		lblNewLabel_4.setBounds(331, 62, 280, 42);
		contentPane.add(lblNewLabel_4);
		
		JTabbedPane panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBackground(new Color(12, 138, 199));
		panel.setFont(new Font("Roboto", Font.PLAIN, 16));
		panel.setBounds(20, 169, 865, 328);
		contentPane.add(panel);

		
		
		
		tablaReservas = new JTable();
		tablaReservas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaReservas.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloReservas = new DefaultTableModel();
		modeloReservas.addColumn("Numero de Reserva");
		modeloReservas.addColumn("Fecha Check In");
		modeloReservas.addColumn("Fecha Check Out");
		modeloReservas.addColumn("Valor");
		modeloReservas.addColumn("Forma de Pago");
		tablaReservas.setModel(modeloReservas);
		modelosDeTabla.put(tablaReservas, modeloReservas);
		

		JScrollPane scroll_table = new JScrollPane(tablaReservas);
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/reservado.png")), scroll_table, null);
		scroll_table.setVisible(true);

		GetAllDataReservas();

		
		
		tablaHuesped = new JTable();
		tablaHuesped.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablaHuesped.setFont(new Font("Roboto", Font.PLAIN, 16));
		modeloHuespedes = new DefaultTableModel();
		modeloHuespedes.addColumn("Número de Huesped");
		modeloHuespedes.addColumn("Nombre");
		modeloHuespedes.addColumn("Apellido");
		modeloHuespedes.addColumn("Fecha de Nacimiento");
		modeloHuespedes.addColumn("Nacionalidad");
		modeloHuespedes.addColumn("Telefono");
		modeloHuespedes.addColumn("Número de Reserva");
		tablaHuesped.setModel(modeloHuespedes);
		modelosDeTabla.put(tablaHuesped, modeloHuesped);
		
		JScrollPane scroll_tableHuespedes = new JScrollPane(tablaHuesped);
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/pessoas.png")), scroll_tableHuespedes, null);
		scroll_tableHuespedes.setVisible(true);
		

		GetAllDataHuespedes(); 
		
		panel.addChangeListener(e -> {
		    currentTab = panel.getSelectedIndex();
		});
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(56, 51, 104, 107);
		contentPane.add(lblNewLabel_2);
		
		JPanel header = new JPanel();
		header.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				headerMouseDragged(e);
			     
			}
		});
		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				headerMousePressed(e);
			}
		});
		header.setLayout(null);
		header.setBackground(Color.WHITE);
		header.setBounds(0, 0, 910, 36);
		contentPane.add(header);
		
		JPanel btnAtras = new JPanel();
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();				
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAtras.setBackground(new Color(12, 138, 199));
				labelAtras.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) {
				 btnAtras.setBackground(Color.white);
			     labelAtras.setForeground(Color.black);
			}
		});
		btnAtras.setLayout(null);
		btnAtras.setBackground(Color.WHITE);
		btnAtras.setBounds(0, 0, 53, 36);
		header.add(btnAtras);
		
		labelAtras = new JLabel("<");
		labelAtras.setHorizontalAlignment(SwingConstants.CENTER);
		labelAtras.setFont(new Font("Roboto", Font.PLAIN, 23));
		labelAtras.setBounds(0, 0, 53, 36);
		btnAtras.add(labelAtras);
		
		JPanel btnexit = new JPanel();
		btnexit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
			@Override
			public void mouseEntered(MouseEvent e) { 
				btnexit.setBackground(Color.red);
				labelExit.setForeground(Color.white);
			}			
			@Override
			public void mouseExited(MouseEvent e) { 
				 btnexit.setBackground(Color.white);
			     labelExit.setForeground(Color.black);
			}
		});
		btnexit.setLayout(null);
		btnexit.setBackground(Color.WHITE);
		btnexit.setBounds(857, 0, 53, 36);
		header.add(btnexit);
		
		labelExit = new JLabel("X");
		labelExit.setHorizontalAlignment(SwingConstants.CENTER);
		labelExit.setForeground(Color.BLACK);
		labelExit.setFont(new Font("Roboto", Font.PLAIN, 18));
		labelExit.setBounds(0, 0, 53, 36);
		btnexit.add(labelExit);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setForeground(new Color(12, 138, 199));
		separator_1_2.setBackground(new Color(12, 138, 199));
		separator_1_2.setBounds(539, 159, 193, 2);
		contentPane.add(separator_1_2);
		
		JPanel btnbuscar = new JPanel();
		btnbuscar.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        if (currentTab == 0) {
		            filterReservas();
		        } else if (currentTab == 1) {
		            filterHuespedes();
		        }
		    }
		});
		btnbuscar.setLayout(null);
		btnbuscar.setBackground(new Color(12, 138, 199));
		btnbuscar.setBounds(748, 125, 122, 35);
		btnbuscar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnbuscar);
		
		JLabel lblBuscar = new JLabel("BUSCAR");
		lblBuscar.setBounds(0, 0, 122, 35);
		btnbuscar.add(lblBuscar);
		lblBuscar.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuscar.setForeground(Color.WHITE);
		lblBuscar.setFont(new Font("Roboto", Font.PLAIN, 18));
		
		JPanel btnEditar = new JPanel();
		btnEditar.setLayout(null);
		btnEditar.setBackground(new Color(12, 138, 199));
		btnEditar.setBounds(635, 508, 122, 35);
		btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEditar);
		
		JLabel lblEditar = new JLabel("EDITAR");
		lblEditar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentTab == 0) {
					System.out.print("En reservas");
					int selectedRow = tablaReservas.getSelectedRow();
			        if (selectedRow != -1) { 
			        	updateReserva(selectedRow);
			        }
					
		        } else if (currentTab == 1) {
		        	System.out.print("En huespedes");
		        	int selectedRow = tablaHuesped.getSelectedRow();
			        if (selectedRow != -1) {
			            updateHuesped(selectedRow);
			        }
		        }
			}
		});
		lblEditar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEditar.setForeground(Color.WHITE);
		lblEditar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEditar.setBounds(0, 0, 122, 35);
		btnEditar.add(lblEditar);
		
		JPanel btnEliminar = new JPanel();
		btnEliminar.setLayout(null);
		btnEliminar.setBackground(new Color(12, 138, 199));
		btnEliminar.setBounds(767, 508, 122, 35);
		btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
		contentPane.add(btnEliminar);
		
		JLabel lblEliminar = new JLabel("ELIMINAR");
		lblEliminar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentTab == 0) {
					System.out.print("En reservas");
					int selectedRow = tablaReservas.getSelectedRow();
			        if (selectedRow != -1) { 
			        	deleteReserva(selectedRow);
			        }
					
		        } else if (currentTab == 1) {
		        	System.out.print("En huespedes");
		        	int selectedRow = tablaHuesped.getSelectedRow();
			        if (selectedRow != -1) {
			        	deleteHuesped(selectedRow);
			        }
		        }
			}
		});
		lblEliminar.setHorizontalAlignment(SwingConstants.CENTER);
		lblEliminar.setForeground(Color.WHITE);
		lblEliminar.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblEliminar.setBounds(0, 0, 122, 35);
		btnEliminar.add(lblEliminar);
		setResizable(false);
	}

	 private void headerMousePressed(java.awt.event.MouseEvent evt) {
	        xMouse = evt.getX();
	        yMouse = evt.getY();
	    }

	    private void headerMouseDragged(java.awt.event.MouseEvent evt) {
	        int x = evt.getXOnScreen();
	        int y = evt.getYOnScreen();
	        this.setLocation(x - xMouse, y - yMouse);
	    }
	    
	    public void GetAllDataReservas() {
	        EntityManager em = JPAUtils.getEntityManager();
	        
	        em.getTransaction().begin();
	        
	        ReservaDao reservaDao = new ReservaDao(em);
	        
	        List<Reserva> reservas = reservaDao.getAll();
	        
	        for (Reserva reserva : reservas) {
	            Object[] rowData = {
	                reserva.getId(),
	                reserva.getFechaEntrada().toString(), 
	                reserva.getFechaSalida().toString(),  
	                reserva.getValor(),
	                reserva.getFormaPago()
	            };
	            modeloReservas.addRow(rowData); 	        }

	        em.getTransaction().commit();
	        em.close();
	    }
	    
	    public void GetAllDataHuespedes() {
	        EntityManager em = JPAUtils.getEntityManager();
	        
	        em.getTransaction().begin();
	        
	        HuespedDao HuespedDao = new HuespedDao(em);
	        
	        List<Huesped> huespedes = HuespedDao.getAll();
	        
	        for (Huesped huesped : huespedes) {
	            Object[] rowData = {
		            huesped.getId(),
	            	huesped.getNombre(),
	            	huesped.getApellido(),
	            	huesped.getFechaNacimiento().toString(),
	            	huesped.getNacionalidad(),
	            	huesped.getTelefono(),
	            	huesped.getIdReserva(),
	            };
	            modeloHuespedes.addRow(rowData); 
	        }

	        em.getTransaction().commit();
	        em.close();
	    }

	    private void filterHuespedes() {
	        String busqueda = txtBuscar.getText(); 
	        List<Huesped> huespedesFiltrados;
	        
	        EntityManager em = JPAUtils.getEntityManager();
	        em.getTransaction().begin();

	        HuespedDao HuespedDao = new HuespedDao(em);
	        

	        if (busqueda == null || busqueda.isEmpty()) {
	            huespedesFiltrados = HuespedDao.getAll();
	        } else {
	            huespedesFiltrados = HuespedDao.consultaPorColumnas(busqueda);
	        }
	        modeloHuespedes.setRowCount(0);

	        for (Huesped huesped : huespedesFiltrados) {
	            Object[] rowData = {
	                huesped.getId(),
	                huesped.getNombre(),
	                huesped.getApellido(),
	                huesped.getFechaNacimiento(),
	                huesped.getNacionalidad(),
	                huesped.getTelefono(),
	                huesped.getIdReserva()
	            };
	            modeloHuespedes.addRow(rowData);
	        }

	        em.getTransaction().commit();
	        em.close();
	    }
	    
	    private void filterReservas() {
	        String textoBusqueda = txtBuscar.getText(); 

	        EntityManager em = JPAUtils.getEntityManager();
	        em.getTransaction().begin();

	        ReservaDao reservaDao = new ReservaDao(em);
	        List<Reserva> reservasFiltradas;

	        if (textoBusqueda == null || textoBusqueda.isEmpty()) {

	            reservasFiltradas = reservaDao.getAll();
	        } else {

	            reservasFiltradas = reservaDao.consultaPorBusqueda(textoBusqueda);
	        }

	        modeloReservas.setRowCount(0); 

	        for (Reserva reserva : reservasFiltradas) {
	            Object[] rowData = {
	                reserva.getId(),
	                reserva.getFechaEntrada().toString(),
	                reserva.getFechaSalida().toString(),
	                reserva.getValor(),
	                reserva.getFormaPago()
	            };
	            modeloReservas.addRow(rowData); 
	        }

	        em.getTransaction().commit();
	        em.close();
	    }
	    public void updateHuesped(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            HuespedDao huespedDao = new HuespedDao(em);


	            int huespedId = (int) tablaHuesped.getValueAt(rowIndex, 0);
	            String updNombre = (String) tablaHuesped.getValueAt(rowIndex, 1);
	            String updApellido = (String) tablaHuesped.getValueAt(rowIndex, 2);
	            String updNacionalidad = (String) tablaHuesped.getValueAt(rowIndex, 4);
	            String updTelefono = (String) tablaHuesped.getValueAt(rowIndex, 5);

	            Huesped exisHuesped = huespedDao.buscarPorId(huespedId);

	            exisHuesped.setNombre(updNombre);
	            exisHuesped.setApellido(updApellido);
	            exisHuesped.setNacionalidad(updNacionalidad);
	            exisHuesped.setTelefono(updTelefono);


	            tablaHuesped.setValueAt(updNombre, rowIndex, 1);
	            tablaHuesped.setValueAt(updApellido, rowIndex, 2);
	            tablaHuesped.setValueAt(updNacionalidad, rowIndex, 4);
	            tablaHuesped.setValueAt(updTelefono, rowIndex, 5);

	            huespedDao.update(exisHuesped);

	            em.getTransaction().commit();
	            
		        Exito exitoFrame = new Exito();
		        exitoFrame.setVisible(true);
		        
	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            String errorMessage = extractErrorMessage(e);

	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
	    private String extractErrorMessage(Exception e) {
	        String errorMessage = "Algo a Salido Mal \n\n Verfica he intenta de nuevo";

	        if (e instanceof SQLException) {
	            SQLException sqlException = (SQLException) e;
	            errorMessage = sqlException.getMessage();
	        }

	        return errorMessage;
	    }
	    
	    public void updateReserva(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            ReservaDao reservaDao = new ReservaDao(em);


	            int reservaId = (int) tablaReservas.getValueAt(rowIndex, 0);
	            String updFechaEnS = (String) tablaReservas.getValueAt(rowIndex, 1); 
	            String updFechaSalS = (String) tablaReservas.getValueAt(rowIndex, 2);
	            String updValS = (String) tablaReservas.getValueAt(rowIndex, 3); 
	            String updFormaPagoS = (String) tablaReservas.getValueAt(rowIndex, 4); 

	            SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd"); 
	            Date updatedFechaEntrada = null;
	            Date updatedFechaSalida = null;
	            try {
	                updatedFechaEntrada = new Date(fecha.parse(updFechaEnS).getTime());
	                updatedFechaSalida = new Date(fecha.parse(updFechaSalS).getTime());
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }

	            BigDecimal updatedValor = new BigDecimal(updValS);

	            Reserva exisReserva = reservaDao.buscarPorId(reservaId);

	            exisReserva.setFechaEntrada(updatedFechaEntrada);
	            exisReserva.setFechaSalida(updatedFechaSalida);
	            exisReserva.setValor(updatedValor);
	            exisReserva.setFormaPago(updFormaPagoS);

	            tablaReservas.setValueAt(updatedFechaEntrada, rowIndex, 1);
	            tablaReservas.setValueAt(updatedFechaSalida, rowIndex, 2);
	            tablaReservas.setValueAt(updatedValor, rowIndex, 3);
	            tablaReservas.setValueAt(updFormaPagoS, rowIndex, 4);

	            reservaDao.update(exisReserva);

	            em.getTransaction().commit();

	            Exito exitoFrame = new Exito();
	            exitoFrame.setVisible(true);

	        } catch (Exception e) {

	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            String errorMessage = extractErrorMessage(e);

	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
	    public void deleteReserva(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {		        
	            em.getTransaction().begin();
	            ReservaDao reservaDao = new ReservaDao(em);

	            int idReserva = (int) tablaReservas.getValueAt(rowIndex, 0);

	            Reserva exisReserva = reservaDao.buscarPorId(idReserva);

	            int confirma = JOptionPane.showConfirmDialog(
	                null,
	                "Estas Seguro de que quieres eliminar esta reserva?",
	                "Confirmar Eliminacion",
	                JOptionPane.YES_NO_OPTION
	            );

	            if (confirma == JOptionPane.YES_OPTION) {
	                reservaDao.eliminar(exisReserva);
	                em.getTransaction().commit();
	                modeloReservas.removeRow(rowIndex);

	            }

	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }
	            String errorMessage = extractErrorMessage(e);
	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }
	    
	    public void deleteHuesped(int rowIndex) {
	        EntityManager em = JPAUtils.getEntityManager();
	        try {
	            em.getTransaction().begin();
	            HuespedDao huespedDao = new HuespedDao(em);
	            int idHuesped = (int) tablaHuesped.getValueAt(rowIndex, 0);
	            Huesped exisHuesped = huespedDao.buscarPorId(idHuesped);
	            int confirma = JOptionPane.showConfirmDialog(
	                null,
	                "Estas Seguro de que quieres eliminar este huesped?",
	                "Confirmar Eliminacion",
	                JOptionPane.YES_NO_OPTION
	            );

	            if (confirma == JOptionPane.YES_OPTION) {
	            	huespedDao.eliminar(exisHuesped);

	                em.getTransaction().commit();

	                modeloHuespedes.removeRow(rowIndex);

	            }

	        } catch (Exception e) {
	            if (em.getTransaction().isActive()) {
	                em.getTransaction().rollback();
	            }

	            String errorMessage = extractErrorMessage(e);

	            JOptionPane.showMessageDialog(null, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);

	            e.printStackTrace();
	        } finally {
	            em.close();
	        }
	    }

}