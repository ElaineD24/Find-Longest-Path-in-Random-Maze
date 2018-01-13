import javafx.geometry.Point2D;
import javafx.scene.control.Cell;

import java.lang.reflect.Array;
import java.util.*;

public class Maze {
    private static final byte OPEN = 0;
    private static final byte WALL = 1;
    private static final byte VISITED = 2;

    private int rows, columns;
    private byte[][] grid;
    private ArrayList<ArrayList<Point2D>> pathArray = new ArrayList<>();
//    private int maxLength = -1;

    // A constructor that makes a maze of the given size
    public Maze(int r, int c) {
        rows = r;
        columns = c;
        grid = new byte[r][c];
        for (r = 0; r < rows; r++) {
            for (c = 0; c < columns; c++) {
                grid[r][c] = WALL;
            }
        }
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    // Return true if a wall is at the given location, otherwise false
    public boolean wallAt(int r, int c) {
        return grid[r][c] == WALL;
    }

    // Return true if this location has been visited, otherwise false
    public boolean visitedAt(int r, int c) {
        return grid[r][c] == VISITED;
    }

    // Put a visit marker at the given location
    public void placeVisitAt(int r, int c) {
        grid[r][c] = VISITED;
    }

    // Remove a visit marker from the given location
    public void removeVisitAt(int r, int c) {
        grid[r][c] = OPEN;
    }

    // Put a wall at the given location
    public void placeWallAt(int r, int c) {
        grid[r][c] = WALL;
    }

    // Remove a wall from the given location
    public void removeWallAt(int r, int c) {
        grid[r][c] = 0;
    }

    // Carve out a maze
    public void carve() {
        int startRow = (int) (Math.random() * (rows - 2)) + 1;
        int startCol = (int) (Math.random() * (columns - 2)) + 1;
        carve(startRow, startCol);
    }

    // Directly recursive method to carve out the maze
    public void carve(int r, int c) {
        ArrayList<Integer> rowOffsets = new ArrayList<Integer>(Arrays.asList(-1, 1, 0, 0));
        ArrayList<Integer> colOffsets = new ArrayList<Integer>(Arrays.asList(0, 0, -1, 1));
        ArrayList<ArrayList> newArray = new ArrayList<>();
        ArrayList<Integer> array;
        for (int i = 0; i < 4; i++) {
            array = new ArrayList<>();
            array.add(rowOffsets.get(i));
            array.add(colOffsets.get(i));
            newArray.add(array);
        }
        if (r <= 0 || r >= getRows() - 1 || c <= 0 || c >= getColumns() - 1) {

        } else {
            if (grid[r][c] == OPEN) {

            } else {
                int count = 0;
                for (int i = 0; i < 4; i++) {
                    if (grid[r + rowOffsets.get(i)][c + colOffsets.get(i)] == WALL) {
                        count++;
                    }
                }
                Collections.shuffle(newArray);
                if (count >= 3) {
                    removeWallAt(r, c);
                    for (int i = 0; i < 4; i++) {
                        carve(r + (int) newArray.get(i).get(0), c + (int) newArray.get(i).get(1));
                    }
                }
            }
        }
    }

    // Determine the longest path in the maze from the given start location
    public ArrayList<Point2D> longestPath() {
        int max = 0;
        int row = 0;
        int col = 0;
        for(int r = 0;r<getRows();r++){
            for(int c = 0;c<getColumns();c++){
                if(longestPathFrom(r,c).size()>max){
                    max = longestPathFrom(r,c).size();
                    row = r;
                    col = c;
                }
            }
        }
        placeVisitAt(row,col);
        return longestPathFrom(row,col); // Replace this with your code
    }

    // Determine the longest path in the maze from the given start location
    ArrayList<Point2D> path = new ArrayList<Point2D>();
    public ArrayList<Point2D> longestPathFrom(int r, int c) {
        ArrayList<Point2D> path = new ArrayList<Point2D>();
        ArrayList<Point2D> up = new ArrayList<>();
        ArrayList<Point2D> down = new ArrayList<>();
        ArrayList<Point2D> left = new ArrayList<>();
        ArrayList<Point2D> right = new ArrayList<>();
        if (grid[r][c] == 1) {
            return path;
        } else if (r > 0 || r < getRows() - 1 || c > 0 || c < getColumns() - 1) {
            placeVisitAt(r, c);
            Point2D[] nextPoints = new Point2D[4];
            nextPoints[0] = new Point2D(r + 1, c);
            nextPoints[1] = new Point2D(r - 1, c);
            nextPoints[2] = new Point2D(r, c - 1);
            nextPoints[3] = new Point2D(r, c + 1);
//            for (Point2D nextPoint : nextPoints) {
            int x1 = (int) nextPoints[0].getX();
            int y1 = (int) nextPoints[0].getY();
            int x2 = (int) nextPoints[1].getX();
            int y2 = (int) nextPoints[1].getY();
            int x3 = (int) nextPoints[2].getX();
            int y3 = (int) nextPoints[2].getY();
            int x4 = (int) nextPoints[3].getX();
            int y4 = (int) nextPoints[3].getY();
            if (grid[x1][y1] == 0 ) {
//                placeVisitAt(r,c);
                placeVisitAt(x1, y1);
                up.add(nextPoints[0]);
                up.addAll(longestPathFrom(x1, y1));
            }
//            removeVisitAt(x1, y1);
            if (grid[x2][y2] == 0 ) {
//                placeVisitAt(r,c);
                placeVisitAt(x2, y2);
                down.add(nextPoints[1]);
//                path = longestPathFrom(x2, y2);
                down.addAll(longestPathFrom(x2, y2));
            }

            if (grid[x3][y3] == 0 ) {
//                placeVisitAt(r,c);
                placeVisitAt(x3, y3);
                left.add(nextPoints[2]);
                left.addAll(longestPathFrom(x3, y3));
            }

            if (grid[x4][y4] == 0) {
//                placeVisitAt(r,c);
                placeVisitAt(x4, y4);
                right.add(nextPoints[3]);
//                longestPathFrom(x4, y4);
                right.addAll(longestPathFrom(x4, y4));
//
            }

        }
        removeVisitAt(r, c);
        int max1 = 0;
        if(up.size()>down.size()){
            max1 = up.size();
        }else{
            max1 = down.size();
        }
        int max2 = 0;
        if(left.size()>right.size()){
            max2 = left.size();
        }else{max2 = right.size();}
        int max = 0;
        if(max1>max2){
            max = max1;
        }else{max = max2;}

        if(max == up.size()){
            path.addAll(up);
        }else if(max == down.size()){
            path.addAll(down);
        }else if(max == left.size()){
            path.addAll(left);
        }else if(max == right.size()){
            path.addAll(right);
        }
//                        System.out.println(max);
//                        System.out.println(index);
        return path;
                    }
                }
//            }
//        }
