package model.database.containers.Portfolio;

import model.database.containers.Description;
import model.database.containers.User.UserDescription;
import model.database.containers.User.UserPK;

/**
 * Describes a {@link Portfolio}, does contain all parameters in table, as it accompanies portfolio class
 * which is a Collection of TransactionMaps.
 * <p>
 * This description is mutable, as this can be edited by the user and then persisted
 */
public class PorfolioDescription implements Description {
    public static final int NUMBER_OF_PARAMETERS = 10;
    private static final int FIELD_TITLE_LENGTH = 10;
    private static final int FIELD_COMMENTARY_LENGTH = 200;
    private static final int FIELD_INDEX_NAME_LENGTH = 10;
    public static final int TITLE_FIELD_LENGTH = 10;
    public static final int COMMENTARY_FIELD_LENGTH = 200;
    public static final int INDEX_NAME_FIELD_LENGTH = 10;
    final UserDescription userDescription;
    final PortfolioPK uuid;
    private String title;
    private String commentary;
    private boolean isAutoGenerated;
    private double budget;
    private double targetEarnings;
    private double targetRisk;
    private boolean isFollowingIndex;
    private String indexName;

    private PorfolioDescription(PortfolioPK uuid, String FK_UUID_User, String title, String commentary,
                                boolean isAutoGenerated, double budget, double targetEarnings, double targetRisk,
                                boolean isFollowingIndex, String indexName) {
        this.userDescription = getUserDescription(FK_UUID_User);
        this.uuid = uuid;
        this.title = title;
        this.commentary = commentary;
        this.isAutoGenerated = isAutoGenerated;
        this.budget = budget;
        this.targetEarnings = targetEarnings;
        this.targetRisk = targetRisk;
        this.isFollowingIndex = isFollowingIndex;
        this.indexName = indexName;
    }

    private PorfolioDescription(PortfolioPK uuid, String FK_UUID_User, String title) {
        this.uuid = uuid;
        this.userDescription = getUserDescription(FK_UUID_User);
        this.title = title;
    }

    private PorfolioDescription(PortfolioPK uuid, String FK_UUID_User) {
        this.uuid = uuid;
        this.userDescription = getUserDescription(FK_UUID_User);
        this.title = "Untitled Portfolio";
    }

    private UserDescription getUserDescription(String FK_UUID_User) {
        //todo
        return null;
    }

    public static PorfolioDescription getInstance(PortfolioPK uuid, UserPK FK_UUID_User, String title, String commentary,
                                                  boolean isAutoGenerated, double budget, double targetEarnings, double targetRisk,
                                                  boolean isFollowingIndex, String indexName) {
        if (title.length() <= TITLE_FIELD_LENGTH && commentary.length() <= COMMENTARY_FIELD_LENGTH
                && indexName.length() <= INDEX_NAME_FIELD_LENGTH) {
            return new PorfolioDescription(uuid, FK_UUID_User.toString(), title, commentary, isAutoGenerated, budget, targetEarnings,
                    targetRisk, isFollowingIndex, indexName);
        }else throw new IllegalArgumentException("One of the arguments is exceeding length");
    }

    public static PorfolioDescription getInstance(PortfolioPK uuid, UserPK FK_UUID_User, String title){
        if (title.length() <= TITLE_FIELD_LENGTH){
            return new PorfolioDescription(uuid, FK_UUID_User.toString(), title);
        }else throw new IllegalArgumentException("Title exceeds length");
    }

    public static PorfolioDescription getInstance(PortfolioPK uuid, UserPK FK_UUID_User){
        return new PorfolioDescription(uuid, FK_UUID_User.toString());
    }

    @Override
    public String describe() {
        return "User: " + userDescription.describe() + "\n" +
                "UUID: " + uuid.getUUID() + "\n" +
                "title: " + title + "\n" +
                "commentary: " + commentary + "\n" +
                "isAutoGenerated: " + isAutoGenerated + "\n" +
                "budget: " + budget + "\n" +
                "targetEarnings: " + targetEarnings + "\n" +
                "targetRisk: " + targetRisk + "\n" +
                "isFollowingIndex: " + isFollowingIndex + "\n" +
                "indexName: " + indexName + "\n";
    }

    @Override
    public String shortDescribe() {
        return "User: " + userDescription.describe() + "\n" +
                "Title: " + title + "\n" +
                "Commentary: " + commentary + "\n";
    }

    @Override
    public String[] toArrayDescription() {
        String[] array = new String[10];

        array[0] = "User: " + userDescription.describe();
        array[1] = "UUID: " + uuid.getUUID();
        array[2] = "Title: " + title;
        array[3] = "Commentary: " + commentary;
        array[4] = "isAutoGenerated: " + isAutoGenerated;
        array[5] = "Budget: " + budget;
        array[6] = "Target Earnings: " + targetEarnings;
        array[7] = "Target Risk: " + targetRisk;
        array[8] = "isFollowingIndex: " + isFollowingIndex;
        array[9] = "Index Name: " + indexName;

        return array;
    }

    public UserDescription getUserDescription() {
        return userDescription;
    }

    public String describeUser() {
        return userDescription.describe();
    }

    public String getUUID() {
        return uuid.getUUID();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws IllegalArgumentException {
        if (title.length() <= FIELD_TITLE_LENGTH) {
            this.title = title;
        }
        throw new IllegalArgumentException("Title too long, max chr: " + FIELD_TITLE_LENGTH);
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) throws IllegalArgumentException {
        if (commentary.length() <= FIELD_COMMENTARY_LENGTH) {
            this.commentary = commentary;
        }
        throw new IllegalArgumentException("Commentary too long, max chr: " + FIELD_COMMENTARY_LENGTH);
    }

    public boolean isAutoGenerated() {
        return isAutoGenerated;
    }

    public void setAutoGenerated(boolean autoGenerated) {
        isAutoGenerated = autoGenerated;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public double getTargetEarnings() {
        return targetEarnings;
    }

    public void setTargetEarnings(double targetEarnings) {
        this.targetEarnings = targetEarnings;
    }

    public double getTargetRisk() {
        return targetRisk;
    }

    public void setTargetRisk(double targetRisk) {
        this.targetRisk = targetRisk;
    }

    public boolean isFollowingIndex() {
        return isFollowingIndex;
    }

    public void setFollowingIndex(boolean followingIndex) {
        isFollowingIndex = followingIndex;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) throws IllegalArgumentException {

        if (indexName.length() <= FIELD_INDEX_NAME_LENGTH) {
            this.indexName = indexName;
        }
        throw new IllegalArgumentException("Index name too long, max chr: " + FIELD_INDEX_NAME_LENGTH);
    }
}
