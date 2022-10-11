package top.org.vehiclecatalogue.dao;

import top.org.vehiclecatalogue.model.Car;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAO {
    private String url = "jdbc:mysql://localhost:3306/cars_db";
    private String dbUser = "root";
    private String dbPassword = "1010898900";

    private static final String INSERT_CARS_SQL = "INSERT INTO cars_t" + "  (name_f, model_f,year_f,price_f) VALUES "
            + " (?, ?, ?, ?);";

    private static final String SELECT_CAR_BY_ID = "select id,name_f,model_f,year_f,price_f from cars_t where id =?";
    private static final String SELECT_ALL_CARS = "select * from cars_t";
    private static final String DELETE_CARS_SQL = "delete from cars_t where id = ?;";
    private static final String UPDATE_CARS_SQL = "update cars_t set name_f = ?,model_f= ?, year_f =?, price_f =? where id = ?;";

    public CarDAO() {
    }

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, dbUser, dbPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    //CREATE
    public void insertCar(Car car) throws SQLException {
        System.out.println(INSERT_CARS_SQL);
        // try-with-resource statement will auto close the connection.
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CARS_SQL)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setInt(3, car.getYear());
            preparedStatement.setDouble(4, car.getPrice());
            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public Car selectCar(int id) {
        Car car = null;
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();
             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CAR_BY_ID);) {
            preparedStatement.setInt(1, id);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {

                String nameCar = rs.getString(2);
                String model = rs.getString(3);
                int year = rs.getInt(4);
                double price = rs.getDouble(5);
                car = new Car(id, nameCar, model, year, price);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return car;
    }

    //READ
    public List<Car> selectAllCars() {

        // using try-with-resources to avoid closing resources
        List<Car> cars = new ArrayList<>();
        // Step 1: Establishing a Connection
        try (Connection connection = getConnection();

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARS);) {
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt(1);
                String nameCar = rs.getString(2);
                String model = rs.getString(3);
                int year = rs.getInt(4);
                double price = rs.getDouble(5);
                cars.add(new Car(id, nameCar, model, year, price));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return cars;
    }

    //UPDATE
    public boolean updateCar(Car car) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CARS_SQL);) {

            statement.setString(1, car.getName());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.setInt(5, car.getId());

            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }

    //DELETE
    public boolean deleteCar(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CARS_SQL);) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}
