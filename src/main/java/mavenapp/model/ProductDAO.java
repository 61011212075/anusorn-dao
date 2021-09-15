package mavenapp.model;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProductDAO {
	Connection connect;
	
	public ProductDAO(Connection connect) {
		this.connect = connect;
	}
	
	public ProductDAO() {
		this.connect = getConnection();
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

	public List<Product> getProduct(){
		List<Product> products = new ArrayList<Product>();
		try {
			String sql = "select * from product";
			PreparedStatement ps = this.connect.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setPdetail(rs.getString("pdetail"));
				product.setPrice(rs.getInt("price"));
				product.setExpire(rs.getDate("expire"));
				product.setImage(rs.getString("image"));
				products.add(product);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		return products;
	}
	
	public List<Product> getProductByName(String input){
		List<Product> products = new ArrayList<Product>();
		try {
			String sql = "select * from product where pname like ?";
			PreparedStatement ps = this.connect.prepareStatement(sql);
			ps.setString(1, "%"+input+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Product product = new Product();
				product.setPid(rs.getInt("pid"));
				product.setPname(rs.getString("pname"));
				product.setPdetail(rs.getString("pdetail"));
				product.setPrice(rs.getInt("price"));
				product.setExpire(rs.getDate("expire"));
				product.setImage(rs.getString("image"));
				products.add(product);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		}
		return products;
	}
	
	public int addNewProduct(Product product) {
		int affected = 0;
		try {
			String sql = "insert into product "
					+"(`pid`, `pname`, `pdetail`, `price`, `expire`,`image`) "
					+"values (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = this.connect.prepareStatement(sql);
			ps.setInt(1, product.getPid());
			ps.setString(2, product.getPname());
			ps.setString(3, product.getPdetail());
			ps.setInt(4, product.getPrice());
			ps.setDate(5, new java.sql.Date(product.getExpire().getTime()));
			ps.setString(6, product.getImage());
			affected = ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}		
		return affected;
	}

}
