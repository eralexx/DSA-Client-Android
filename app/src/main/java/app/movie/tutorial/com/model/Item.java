package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alex on 16/01/2018.
 */

public class Item {
    @SerializedName("cells")
    private Cell[] cells;

    public Cell[] getCells() {
        return cells;
    }

    public void setCells(Cell[] cells) {
        this.cells = cells;
    }

    public Item(Cell[] cells) {
        this.cells = cells;
    }
}
