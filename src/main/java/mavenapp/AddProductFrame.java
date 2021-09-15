package mavenapp;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import mavenapp.model.Product;
import mavenapp.model.ProductDAO;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.awt.event.ActionEvent;

public class AddProductFrame extends JFrame {

	private JPanel contentPane;
	private JTextField tfID;
	private JTextField tfName;
	private JTextField tfPrice;
	private JTextField tfImage;
	private JTextArea taDetail;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AddProductFrame frame = new AddProductFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public AddProductFrame() {
		setTitle("Add Product");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 292, 328);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		tfID = new JTextField();
		tfID.setBounds(84, 43, 97, 20);
		contentPane.add(tfID);
		tfID.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ID");
		lblNewLabel.setBounds(48, 46, 26, 14);
		contentPane.add(lblNewLabel);
		
		tfName = new JTextField();
		tfName.setColumns(10);
		tfName.setBounds(84, 75, 97, 20);
		contentPane.add(tfName);
		
		tfPrice = new JTextField();
		tfPrice.setColumns(10);
		tfPrice.setBounds(84, 167, 97, 20);
		contentPane.add(tfPrice);
		
		tfImage = new JTextField();
		tfImage.setColumns(10);
		tfImage.setBounds(84, 198, 97, 20);
		contentPane.add(tfImage);
		
		taDetail = new JTextArea();
		taDetail.setBounds(84, 106, 148, 50);
		contentPane.add(taDetail);
		
		JLabel lblProductName = new JLabel("Name");
		lblProductName.setBounds(39, 78, 35, 14);
		contentPane.add(lblProductName);
		
		JLabel lblProductDetail = new JLabel("Detail");
		lblProductDetail.setBounds(39, 111, 35, 14);
		contentPane.add(lblProductDetail);
		
		JLabel lblProductPrice = new JLabel("Price");
		lblProductPrice.setBounds(39, 170, 35, 14);
		contentPane.add(lblProductPrice);
		
		JLabel lblImage = new JLabel("Image");
		lblImage.setBounds(39, 201, 35, 14);
		contentPane.add(lblImage);
		
		JButton btnAdd = new JButton("Add Product");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Product product = new Product();
				product.setPid(Integer.parseInt(tfID.getText()));
				product.setPname(tfName.getText());
				product.setPdetail(taDetail.getText());
				product.setPrice(Integer.parseInt(tfPrice.getText()));
				
				Calendar cal = Calendar.getInstance();
				Date today = cal.getTime();
				cal.add(Calendar.YEAR, 1);
				Date nextYear = cal.getTime();
				product.setExpire(nextYear);
				
				product.setImage(tfImage.getText());
				ProductDAO dao = new ProductDAO();
				int affected = dao.addNewProduct(product);
				if(affected > 0) {
					JOptionPane.showMessageDialog(null, "Success");
				}else {
					JOptionPane.showMessageDialog(null, "Failed");
				}
			}
		});
		btnAdd.setBounds(26, 248, 104, 23);
		contentPane.add(btnAdd);
		
		JButton btnCencel = new JButton("Cencel");
		btnCencel.setBounds(156, 248, 89, 23);
		contentPane.add(btnCencel);
	}	
	void closeFrame() {
		this.dispose();
	}
}
