package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Atxy2k.CustomTextField.RestrictedTextField;
import model.DAO;

import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Agenda extends JFrame {

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agenda frame = new Agenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Construtor.
	 */
	public Agenda() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});

		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/img/favicon.png")));
		setTitle("Agenda de Contatos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 518, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtId = new JTextField();
		txtId.setEditable(false);
		txtId.setBounds(392, 28, 86, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);

		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(349, 31, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome");
		lblNewLabel_1.setBounds(21, 31, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setBounds(21, 102, 46, 14);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Email");
		lblNewLabel_3.setBounds(21, 150, 46, 14);
		contentPane.add(lblNewLabel_3);

		txtNome = new JTextField();
		txtNome.setBounds(59, 28, 166, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);

		txtFone = new JTextField();
		txtFone.setBounds(59, 99, 200, 20);
		contentPane.add(txtFone);
		txtFone.setColumns(10);

		txtEmail = new JTextField();
		txtEmail.setBounds(59, 147, 289, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);

		btnCreate = new JButton("");
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarContato();
			}
		});
		btnCreate.setToolTipText("Criar");
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setIcon(new ImageIcon(Agenda.class.getResource("/img/create_icon.png")));
		btnCreate.setBounds(262, 200, 64, 64);
		contentPane.add(btnCreate);

		btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterarContato();
			}
		});
		btnUpdate.setEnabled(false);
		btnUpdate.setToolTipText("atualizar");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setIcon(new ImageIcon(Agenda.class.getResource("/img/update_icon.png")));
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBorderPainted(false);
		btnUpdate.setBounds(340, 200, 64, 64);
		contentPane.add(btnUpdate);

		btnRead = new JButton("");
		btnRead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisarContato();
			}
		});
		btnRead.setToolTipText("pesquisar");
		btnRead.setBorderPainted(false);
		btnRead.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRead.setIcon(new ImageIcon(Agenda.class.getResource("/img/search_icon.png")));
		btnRead.setContentAreaFilled(false);
		btnRead.setBounds(262, 11, 64, 64);
		contentPane.add(btnRead);

		btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirContato();
			}
		});
		btnDelete.setEnabled(false);
		btnDelete.setToolTipText("excluir");
		btnDelete.setIcon(new ImageIcon(Agenda.class.getResource("/img/delete_icon.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(414, 200, 64, 64);
		contentPane.add(btnDelete);

		lblStatus = new JLabel("");
		lblStatus.setIcon(new ImageIcon(Agenda.class.getResource("/img/dboff.png")));
		lblStatus.setBounds(21, 216, 48, 48);
		contentPane.add(lblStatus);

		// Uso da tecla <Enter> junto com um botão
		getRootPane().setDefaultButton(btnRead);

		// uso da biblioteca atx2k para restringir o máximo de caracteres
		// nome
		RestrictedTextField nome = new RestrictedTextField(txtNome);
		nome.setOnlyText(true);
		nome.setLimit(50);
		nome.setAcceptSpace(true);
		// fone
		RestrictedTextField fone = new RestrictedTextField(txtFone);
		fone.setLimit(15);
		// email
		RestrictedTextField email = new RestrictedTextField(txtEmail);
		email.setLimit(50);
		email.setAcceptSpace(true);

	} // Fim do construtor

	// Criar um objeto para acessar o método conectar() da classe DAO
	DAO dao = new DAO();
	private JLabel lblStatus;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JButton btnRead;

	/**
	 * Método responsável por verificar o status com o banco
	 */
	private void status() {
		// System.out.println("Teste Janela Ativada");
		// Uso da classe connection (JDBC) para estabelecer a conexâo
		try {
			Connection con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão ");
				lblStatus.setIcon(new ImageIcon(Agenda.class.getResource("/img/doff.png")));

			} else {
				// System.out.println("Banco Conectado! ");
				lblStatus.setIcon(new ImageIcon(Agenda.class.getResource("/img/dbon.png")));
			}
			// nunca esquecer de encerrar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println("e");
		}

	}// Fim do Status

	/**
	 * Método responsável por pesquisar um contato (select)
	 */
	private void pesquisarContato() {

		// Validação
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Nome do Contato");
			txtNome.requestFocus();

		} else {

			// System.out.println("Teste Pesquisar");
			// Iniciar com a instrução SQL
			// ? É um parâmetro a ser substituído
			String read = "select * from contatos where nome = ?";

			try {
				// Estabelecer a conexão
				Connection con = dao.conectar();
				// Preparar o código SQL para a execução
				PreparedStatement pst = con.prepareStatement(read);
				// A linha abaixo subistitui o ? pelo conteúdo da caixa de texto txtNome, o 1
				// faz referência a interrogação
				pst.setString(1, txtNome.getText());
				// Obter os dados do contato
				ResultSet rs = pst.executeQuery();
				// Verificar se existe um contato cadastrado
				// rs.next() significa ter um contato correspondente ao nome pesquisado
				if (rs.next()) {
					// setar as caixas de texto com o resultado da pesquisa
					txtId.setText(rs.getString(1));
					txtFone.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4));
					//// habilitar botões habilitar e excluir
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Contato inexistente");
					// Setar campos e botões (UX)
					// limpar();
					txtFone.setText(null);
					txtEmail.setText(null);
					txtFone.requestFocus();
					btnCreate.setEnabled(true);
					btnRead.setEnabled(false);

				}
				// fechar a conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);

			}
		}

	}

	/**
	 * Método responsável pelo cadastro
	 */
	void adicionarContato() {
		// Validação
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtFone.requestFocus();

		} else {

			// System.out.println("Teste adicionar contatos");
			String create = "insert into contatos (nome,fone,email) values (?, ?, ?)";
			try {
				// Abrir a conexão
				Connection con = dao.conectar();

				// Preparar a query (substituíção de parâmetros
				PreparedStatement pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				// Executar a query e confirmar a inserção no banco
				int confirma = pst.executeUpdate();
				// System.out.println(confirma);
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Contato Adicionado com Sucesso!");
					limpar();
				}
				// Encerrar a conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	// Método responsável pela edição do contato
	private void alterarContato() {
		// System.out.println("Teste botão update contato");

		// Validação
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome");
			txtFone.requestFocus();

		} else {

			// Lógica principal
			String update = "update contatos set nome = ?, fone = ?, email = ? where id = ?";

			try {
				// Abrir a conexão
				Connection con = dao.conectar();
				// Preparar a query (instrução SQL)
				PreparedStatement pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtId.getText());
				// Executar a query e confirmar as alterações no banco
				int confirma = pst.executeUpdate();
				// System.out.println(confirma);
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Contato Atualizado com Sucesso");
					limpar();
				}
				// Encerrar a conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}

		}

	}

	/**
	 * método usado para excluir
	 */
	private void excluirContato() {
		// System.out.println("Teste exluir contato");
		// validção
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste contato?", "ATENÇÃO!",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from contatos where id = ?";
			try {
				// abrir a conexão
				Connection con = dao.conectar();
				// preparar a query
				PreparedStatement pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				// executar o comando SQL para confirmar a exclusão
				int confirmaExcluir = pst.executeUpdate();
				if (confirmaExcluir == 1) {
					limpar();

					JOptionPane.showMessageDialog(null, "Contato excluído com sucesso!");
					// encerrar a conexão
					con.close();
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Método usado para limpar os campos
	 */
	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		txtNome.requestFocus();
		btnCreate.setEnabled(true);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnRead.setEnabled(true);
	}

}// Fim
