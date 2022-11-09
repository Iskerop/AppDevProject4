package edu.uga.cs.statecapitalsquiz;

public class QuizHistory {
    private long id;
    private String date;
    private String firstQuest;
    private String secondQuest;
    private String thirdQuest;
    private String fourthQuest;
    private String fifthQuest;
    private String sixthQuest;
    private String result;
    private String numAnswered;

    // default constructor
    public QuizHistory() {
        this.id = -1;
        this.date = null;
        this.firstQuest = null;
        this.secondQuest = null;
        this.thirdQuest = null;
        this.fourthQuest = null;
        this.fifthQuest = null;
        this.sixthQuest = null;
        this.result = null;
        this.numAnswered = null;
    }

    public QuizHistory(String date, String firstQuest, String secondQuest, String thirdQuest, String fourthQuest, String fifthQuest, String sixthQuest, String result, String numAnswered) {
        this.id = -1;
        this.date = date;
        this.firstQuest = firstQuest;
        this.secondQuest = secondQuest;
        this.thirdQuest = thirdQuest;
        this.fourthQuest = fourthQuest;
        this.fifthQuest = fifthQuest;
        this.sixthQuest = sixthQuest;
        this.result = result;
        this.numAnswered = numAnswered;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFirstQuest() {
        return firstQuest;
    }

    public void setFirstQuest(String firstQuest) {
        this.firstQuest = firstQuest;
    }

    public String getSecondQuest() {
        return secondQuest;
    }

    public void setSecondQuest(String secondQuest) {
        this.secondQuest = secondQuest;
    }

    public String getThirdQuest() {
        return thirdQuest;
    }

    public void setThirdQuest(String thirdQuest) {
        this.thirdQuest = thirdQuest;
    }

    public String getFourthQuest() {
        return fourthQuest;
    }

    public void setFourthQuest(String fourthQuest) {
        this.fourthQuest = fourthQuest;
    }

    public String getFifthQuest() {
        return fifthQuest;
    }

    public void setFifthQuest(String fifthQuest) {
        this.fifthQuest = fifthQuest;
    }

    public String getSixthQuest() {
        return sixthQuest;
    }

    public void setSixthQuest(String sixthQuest) {
        this.sixthQuest = sixthQuest;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNumAnswered() {
        return numAnswered;
    }

    public void setNumAnswered(String numAnswered) {
        this.numAnswered = numAnswered;
    }

    @Override
    public String toString() {
        return "QuizHistory{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", firstQuest='" + firstQuest + '\'' +
                ", secondQuest='" + secondQuest + '\'' +
                ", thirdQuest='" + thirdQuest + '\'' +
                ", fourthQuest='" + fourthQuest + '\'' +
                ", fifthQuest='" + fifthQuest + '\'' +
                ", sixthQuest='" + sixthQuest + '\'' +
                ", result='" + result + '\'' +
                ", numAnswered='" + numAnswered + '\'' +
                '}';
    }
}
