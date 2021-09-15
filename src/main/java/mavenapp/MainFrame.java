package mavenapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import mavenapp.model.Product;
import mavenapp.model.ProductDAO;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfProduct;
	private JTable table;
	private JTextField tfID;
	private JTextField tfPrice;
	private JTextField tfName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainFrame frame = new MainFrame();
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
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 739, 415);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnTestConnection = new JButton("Test Connection");
		btnTestConnection.setToolTipText("");
		btnTestConnection.setHorizontalAlignment(SwingConstants.LEADING);
		btnTestConnection.setBackground(Color.WHITE);
		btnTestConnection.setIcon(new ImageIcon("D:\\Icon\\16px\\smart-plug.png"));
		btnTestConnection.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Connection conn = getConnection();
				
			}
		});
		btnTestConnection.setBounds(31, 23, 132, 25);
		contentPane.add(btnTestConnection);
		
		tfProduct = new JTextField();
		tfProduct.setBounds(31, 59, 132, 25);
		contentPane.add(tfProduct);
		tfProduct.setColumns(10);
		
		JButton btnProduct = new JButton("  Show Product");
		btnProduct.setHorizontalAlignment(SwingConstants.LEADING);
		btnProduct.setBackground(Color.WHITE);
		btnProduct.setIcon(new ImageIcon("D:\\Icon\\16px\\list.png"));
		btnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = getConnection();
				if(conn!= null) {
					try {
						String input = tfProduct.getText();
						String sql = "select * from product where pname like ?";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setString(1, input+"%");
						ResultSet rs = ps.executeQuery();
						table.setModel(buildTableModel(rs));
					} catch(Exception e1) {}					
					
				}
			}
		});
		btnProduct.setBounds(31, 94, 132, 25);
		contentPane.add(btnProduct);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(31, 128, 666, 237);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		tfID = new JTextField();
		tfID.setColumns(10);
		tfID.setBounds(178, 59, 100, 25);
		contentPane.add(tfID);
		
		tfPrice = new JTextField();
		tfPrice.setColumns(10);
		tfPrice.setBounds(295, 59, 100, 25);
		contentPane.add(tfPrice);
		
		JLabel lblNewLabel = new JLabel("Product ID");
		lblNewLabel.setBounds(198, 28, 67, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblPrice = new JLabel("Product Price");
		lblPrice.setBounds(305, 28, 67, 14);
		contentPane.add(lblPrice);
		
		JButton btnUpdate = new JButton(" Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = getConnection();
					String sql = "update product set price = ? where pid = ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(tfPrice.getText()));
					ps.setInt(2, Integer.parseInt(tfID.getText()));
					int affected = ps.executeUpdate();
					if(affected>0) {
						JOptionPane.showMessageDialog(null, "Updated");
					}else {
						JOptionPane.showMessageDialog(null, "Not Updated");
					}
					
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnUpdate.setHorizontalAlignment(SwingConstants.LEADING);
		btnUpdate.setIcon(new ImageIcon("D:\\Icon\\16px\\floppy-disk.png"));
		btnUpdate.setBounds(295, 94, 100, 24);
		contentPane.add(btnUpdate);
		
		JButton btnShowProductDAO = new JButton("Show Product (DAO)");
		btnShowProductDAO.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pname = tfName.getText();
				try {
					ProductDAO dao = new ProductDAO(getConnection());
					List<Product> products = dao.getProductByName(pname);
					
					DefaultTableModel model = new DefaultTableModel();
					model.addColumn("pid");
					model.addColumn("pname");
					model.addColumn("pdetail");
					model.addColumn("price");
					model.addColumn("expire");
					model.addColumn("image");
					
					for(Product product : products) {
						Object[] obj = {product.getPid(),product.getPname(),product.getPdetail()
								,product.getPrice(),product.getExpire(),product.getImage()};
						model.addRow(obj);
					}
					table.setModel(model);
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnShowProductDAO.setBounds(440, 94, 132, 25);
		contentPane.add(btnShowProductDAO);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(440, 61, 132, 25);
		contentPane.add(tfName);
		
		JLabel lblProductName = new JLabel("Product Name");
		lblProductName.setBounds(463, 28, 67, 14);
		contentPane.add(lblProductName);
		
		JButton btnAddProduct = new JButton("Add Product");
		btnAddProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AddProductFrame frame = new AddProductFrame();
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
				frame.setVisible(true);
			}
		});
		btnAddProduct.setBounds(591, 94, 106, 25);
		contentPane.add(btnAddProduct);
	}
	
	Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
			Properties prop = new Properties();
			prop.load(input);
			String url = prop.getProperty("database.url");
			String username = prop.getProperty("database.username");
			String password = prop.getProperty("database.password");
			Connection connect = DriverManager.getConnection(url+"user="+username+"&password="+password);
			System.out.println("Connect Success");
			return connect;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return null;
	}
	
	
	public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException{
		
		ResultSetMetaData metaData = rs.getMetaData();
		
		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for(int columns = 1;columns <= columnCount;columns++) {
			columnNames.add(metaData.getColumnName(columns));
		}
		
		//data of table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while(rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1;columnIndex <= columnCount;columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}
		
		return new DefaultTableModel(data,columnNames);
	}
}
