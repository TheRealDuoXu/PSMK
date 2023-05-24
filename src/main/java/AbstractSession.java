import data.Result;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class AbstractSession {
    protected String userName;
    protected LocalDateTime startTime;
    protected LocalDateTime endTime;

    protected ArrayList<Result<ArrayList<String>>> results = new ArrayList<>();

    public ArrayList<Result<ArrayList<String>>> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result<ArrayList<String>>> results) {
        this.results = results;
    }

    public void closeSession() {
        this.endTime = LocalDateTime.now();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
