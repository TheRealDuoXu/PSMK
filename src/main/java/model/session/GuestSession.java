package model.session;

import model.database.DAOFactory;
import model.database.dao.CSVFileDAO;

import java.time.LocalDateTime;
import java.util.ArrayDeque;

public class GuestSession extends AbstractSession {


    private String filePath = "./data.csv";
    private boolean guestPersistenceStatus = false;

    public GuestSession(String userName) {
        this.userName = userName;
        this.startTime = LocalDateTime.now();

        initCSV();
    }

    private void initCSV() {
        DAOFactory.getFileDAO(filePath).createCSV();
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isGuestPersistenceStatus() {
        return guestPersistenceStatus;
    }

    public void setGuestPersistenceStatus(boolean guestPersistenceStatus) {
        this.guestPersistenceStatus = guestPersistenceStatus;
    }

    public void shallPersist(ArrayDeque<String> data) {
        if (guestPersistenceStatus) {
            doPersist(data);
        }
    }

    private void doPersist(ArrayDeque<String> data) {
        CSVFileDAO dao = DAOFactory.getFileDAO(filePath);
        String[] values = data.stream().
                map(String::valueOf).toArray(String[]::new);

        dao.writeDataCSV(values);
    }

    @Override
    public void closeSession() {
        super.closeSession();
        DAOFactory.getFileDAO(filePath).deleteCSV();
    }
}
