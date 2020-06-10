package com.example.da;

import com.example.entity.User;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/demo?useSSL=false";
    private String jdbcUsername ="root";
    private String jdbcPassword ="";

    private Connection connection = null;
    private PreparedStatement preparedStatement = null;

    private static final String INSERT_USERS_SQL ="INSERT INTO users"+"(name,email,country)"+"VALUES(?,?,?)";
    private static final String SELECT_USER_BY_ID ="SELECT id,name,email,country FROM users"+"WHERE id = ?";
    private static final String SELECT_ALL_USERS ="SELECT * FROM users";
    private static final String DELETE_USERS_SQL ="DELETE FROM users WHERE id =?";
    private static final String UPDATE_USERS_SQL ="UPDATE users SET name =?, email =?, country =?"+"WHERE id =?";

    public UserDAO() {
    }
    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(jdbcURL,jdbcUsername,jdbcPassword);

        return connection;
    }
    public void insertUser(User user) throws SQLException{
        preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getEmail());
        preparedStatement.setString(3,user.getCountry());
    }
    public List<User> selectAllUsers() throws SQLException{
        List<User> users = new ArrayList<>();
        preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int id  = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String country = resultSet.getString("country");
            users.add(new User(id,name,email,country));
        }
        return users;

    }
    public static  void main(String[] args) throws SQLException, ClassNotFoundException{
        UserDAO userDAO = new UserDAO();
        if (userDAO.getConnection()!=null){
            System.out.println("Success");
        }
    }

}
