package model.database.dao;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.database.SQLQuery;

public class CSVFileDAO extends DAO {
    protected static final String DEFAULT_FILEPATH = "./default output.csv";
    //    CSVFileDAO does not compete with other DAO objects, as it targets a File, and not the DDBB
    private static CSVFileDAO instance;
    private String filePath;

    private CSVFileDAO() {
        this.filePath = DEFAULT_FILEPATH;
    }

    private CSVFileDAO(String filePath) {
        this.filePath = filePath;
    }

    public static CSVFileDAO getInstance() {
        synchronized (CSVFileDAO.class) {
            if (instance == null) {
                instance = new CSVFileDAO();
            }
        }
        return instance;
    }

    public static CSVFileDAO getInstance(String filePath) {
        synchronized (CSVFileDAO.class) {
            if (instance == null) {
                instance = new CSVFileDAO(filePath);
            }
        }
        return instance;
    }


    public void writeDataTableCSV(List<String[]> data) {
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
        } catch (IOException e) {
            System.out.println(e.toString());
            //TODO
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
            System.out.println(e.toString());
            //TODO
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
        } catch (IOException e) {
            System.out.println(e.toString());
            //TODO
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
            System.out.println(e.toString());
            //TODO
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
    public List<String> getCSVInDir(String workingDir) {
        List<String> filePaths = new ArrayList<>();

        File directory = new File(workingDir);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".csv")) {
                    filePaths.add(file.getAbsolutePath());
                }
            }
        }
        return filePaths;
    }

    public void insertAllCSVFiles(String absPath, String stockExchange, char assetType) {

    }
}
