public class Grid {
    public int rows;
    public int cols;
    public Person[][] array; 

    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.array = new Person[rows][cols];

        for (int i = 0; i < this.array.length; i++) {
            for (int j = 0; j < this.array[0].length; j++) {
                this.array[i][j] = new Person("HEALTHY");
            }
        }
    }

    public void infectPerson(int r, int c) {
        if (r >= 0 && r < this.rows && c >= 0 && c < this.cols) {
            this.array[r][c].setStatus("INFECTED");
        }
    }

    public Person[][] getArray() {
        return this.array;
    }
}
