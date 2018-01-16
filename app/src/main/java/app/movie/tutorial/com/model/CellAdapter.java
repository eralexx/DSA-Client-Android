package app.movie.tutorial.com.model;


import com.google.gson.annotations.SerializedName;

class CellAdapter {

    @SerializedName("item")
    private Cell[] cells;

    public Cell[] getItems() {
        return cells;
    }

    public void setItems(Cell[] items) {
        this.cells = items;
    }

    public CellAdapter(Cell[] items) {
        this.cells = items;
    }
}
