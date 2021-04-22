package org.ciberfarma.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ciberfarma.modelo.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class FrmCrudUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtCodigo;
	private JTextField txtNombre;
	private JTextField txtApellido;
	private JTextArea txtS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCrudUsuario frame = new FrmCrudUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmCrudUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 478, 457);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.setBounds(272, 39, 89, 23);
		contentPane.add(btnRegistrar);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnBuscar.setBounds(272, 97, 89, 23);
		contentPane.add(btnBuscar);
		
		txtCodigo = new JTextField();
		txtCodigo.setBounds(135, 40, 86, 20);
		contentPane.add(txtCodigo);
		txtCodigo.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Codigo");
		lblNewLabel.setBounds(50, 43, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(50, 103, 46, 14);
		contentPane.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setColumns(10);
		txtNombre.setBounds(135, 100, 86, 20);
		contentPane.add(txtNombre);
		
		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(50, 160, 46, 14);
		contentPane.add(lblApellido);
		
		txtApellido = new JTextField();
		txtApellido.setColumns(10);
		txtApellido.setBounds(135, 157, 86, 20);
		contentPane.add(txtApellido);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 191, 383, 216);
		contentPane.add(scrollPane);
		
		txtS = new JTextArea();
		scrollPane.setViewportView(txtS);
		
		JButton btnListado = new JButton("Listado");
		btnListado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listado();
			}			
		});
		btnListado.setBounds(272, 156, 89, 23);
		contentPane.add(btnListado);
	}
	void buscar() {
		int codigo = leerCodigo();
		//buscar en la tabla para obtener un usuario
		EntityManagerFactory fabrica =Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		Usuario u = em.find(Usuario.class, codigo);
		em.close();
		if(u==null) {
			aviso("Usuario " + codigo + " no existe") ;
		}else {
			txtNombre.setText(u.getNombre());
			txtApellido.setText(u.getApellido());
		}
		
	}
	void listado() {
		//Obtener un listado de los usuarios de mi tabla
		EntityManagerFactory fabrica =Persistence.createEntityManagerFactory("jpa_sesion01");
		EntityManager em = fabrica.createEntityManager();
		TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAllWithType", Usuario.class);
		consulta.setParameter("xtipo", 1);
		List<Usuario> lstUsuarios = consulta.getResultList();
		em.close();
		for (Usuario u : lstUsuarios) {
			txtS.append(u.getNombre() + "\t"+ u.getApellido()+
		"\t"+u.getApellido()+"\n");
		}
		//pasar el listado al txt, pdf, table
	}

	private void aviso(String msg) {
		// TODO Auto-generated method stub
		 JOptionPane.showMessageDialog(this, msg, "Aviso del Sistema", JOptionPane.WARNING_MESSAGE);
	}

	private int leerCodigo() {
		
		return Integer.parseInt(txtCodigo.getText());
	}
	private int leerNombre() {
		if(!txtNombre.getText().matches("")) {}
		
		return Integer.parseInt(txtNombre.getText());
	}
}
