public class Person {
    private String status;
    private int infectionTurns = 0; // Tracks how many turns they have been infected

    public Person(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getInfectionTurns() {
        return infectionTurns;
    }

    public void incrementInfectionTurns() {
        this.infectionTurns++;
    }

    public void resetInfectionTurns() {
        this.infectionTurns = 0;
    }
}
