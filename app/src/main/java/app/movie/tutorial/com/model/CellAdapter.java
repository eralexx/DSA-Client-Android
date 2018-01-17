package app.movie.tutorial.com.model;


import com.google.gson.annotations.SerializedName;

public class CellAdapter {

    @SerializedName("item")
    private Cell[] cells;

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] items) {
        this.cells = items;
    }

    public CellAdapter(Cell[] items) {
        this.cells = items;
    }
}
