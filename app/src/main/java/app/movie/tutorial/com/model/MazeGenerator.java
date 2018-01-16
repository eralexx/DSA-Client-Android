package app.movie.tutorial.com.model;

import java.util.*;

public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;
    private  Cell[][] Cells;

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
    }

    public Cell[][] getCells() {
        return Cells;
    }

    public int[][]getMaze(){
            return this.maze;
    }

    public void display() {
        this.Cells=new Cell[this.x][this.y];
        for (int i = 0; i < y; i++) {

            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
                this.Cells[j][i] = new Cell(0, j, i, maze[j][i]);

                if ((maze[j][i] & 1) != 0) {
                    if (i>0) {
                        if (this.Cells[j][i].Moves ==null){
                            this.Cells[j][i].Moves= new ArrayList<>();
                        }
                        if (this.Cells[j][i - 1].Moves ==null){
                            this.Cells[j][i - 1].Moves= new ArrayList<>();
                        }
                        this.Cells[j][i].Moves.add('N');
                        this.Cells[j][i - 1].Moves.add('S');
                    }
                }
            }
            System.out.println("+");

            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");

                if((maze[j][i] & 8) != 0) {
                    if (j>0) {
                        if (this.Cells[j][i].Moves == null) {
                            this.Cells[j][i].Moves = new ArrayList<>();
                        }
                        if (this.Cells[j - 1][i].Moves == null) {
                            this.Cells[j - 1][i].Moves = new ArrayList<>();
                        }
                        this.Cells[j][i].Moves.add('W');
                        this.Cells[j - 1][i].Moves.add('E');
                    }
                }

            }
            System.out.println("|");
        }

        for (int j = 0; j < x; j++) {
            System.out.print("+---");
        }
        System.out.println("+");
    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y)
                    && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }
        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

    public static void main(String[] args) {
        int x =  20;
        int y =  20;
        List<User> players= new ArrayList<>();
        User p1 = new User("Alex", "Alex@shhdfhs", "dani2");
        User p2 = new User("Dani", "Dani@shhdfhs", "dani2");
        players.add(p1);
        players.add(p2);
        Game newGame =  new Game(players);
        Timer timer = new Timer();
        timer.schedule(new QueueManager(), 0, 30000);
        while(true) {
            System.out.println(newGame.Move('N', p1));
            System.out.println(newGame.Move('W', p1));
            System.out.println(newGame.Move('E', p1));
            System.out.println(newGame.Move('S', p1));
            System.out.println("--------------------");
            System.out.println(newGame.Move('N', p2));
            System.out.println(newGame.Move('W', p2));
            System.out.println(newGame.Move('E', p2));
            System.out.println(newGame.Move('S', p2));
            System.out.println("--------------------");
            System.out.println(newGame.Move('N', p1));
            System.out.println(newGame.Move('W', p1));
            System.out.println(newGame.Move('E', p1));
            System.out.println(newGame.Move('S', p1));
            System.out.println("--------------------");
            System.out.println(newGame.Move('N', p2));
            System.out.println(newGame.Move('W', p2));
            System.out.println(newGame.Move('E', p2));
            System.out.println(newGame.Move('S', p2));
            System.out.println("--------------------");
        }

    }

}
