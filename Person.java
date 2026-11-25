/**
 * Represents an individual person on the grid with a health/infection status.
 */
public class Person {
    private String status;
    private int infectionTurns = 0;

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
