package data.database;

import data.portfolio.transaction.TransactionCollection;
import data.portfolio.transaction.TransactionPoint;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/PSMK";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";
    private static DAO instance;
    private static Connection con;
    private final String filePath;

    private DAO(String filePath) {
        this.filePath = filePath;
        this.con = getConnection();
    }

    public static DAO getInstance(String filePath) {
            synchronized (DAO.class) {
                if (instance == null) {
                    instance = new DAO(filePath);
                }
            }
        return instance;
    }
    private static Connection getConnection()  {
        try{
            Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }catch (SQLException e) {
            System.out.println("Connection error: " + e);
            //TODO
        }
        return con;
    }



    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeDataTableCSV(List<String[]> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            for (String[] row : data) {
                StringBuilder sb = new StringBuilder();
                for (String field : row) {
                    sb.append(field).append(",");
                }
                // Eliminar la última coma
                sb.deleteCharAt(sb.length() - 1);
                writer.write(sb.toString());
                writer.newLine();
            }
        }
        System.out.println("Escritura exitosa");
    }

    public void writeDataCSV(String[] data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            StringBuilder sb = new StringBuilder();
            for (String value : data) {
                sb.append(value).append(",");
            }
            // Eliminar la última coma
            sb.deleteCharAt(sb.length() - 1);
            writer.println(sb);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Escritura exitosa");
    }

    public List<String[]> readDataTableCSV() {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    public String[] readDataCSV() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                return line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IOException("Could not read");
    }

    public void createCSV() {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.close();
            System.out.println("Archivo CSV creado exitosamente.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear el archivo CSV.");
        }
    }

    public void deleteCSV() {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo CSV eliminado con éxito.");
            } else {
                System.out.println("No se pudo eliminar el archivo CSV.");
            }
        } else {
            System.out.println("El archivo CSV no existe.");
        }
    }

    public TransactionCollection getAllHistoryFromPorfolio(UUID portfolioUUID) {
        QueriesSQL sql = QueriesSQL.SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_UUID;

    }
    public TransactionCollection getAllHistoryFromPorfolio(UUID portfolioUUID, String ticker) {
        QueriesSQL sql = QueriesSQL.SELECT_TRANSACTION_HISTORY_BY_PORTFOLIO_AND_TICKER;
    }

    public TransactionPoint getTransactionPoint(UUID portfolioUUID, String ticker, Date date){
        QueriesSQL sql = QueriesSQL.SELECT_TRANSACTION_POINT;
    }

}
