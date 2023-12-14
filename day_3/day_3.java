import java.io.*;
import java.util.Scanner;
import java.lang.*;
public class day_3
{
    public static void main(String[] args) 
    {
        try
        {
            File f = new File(args[0]);
            Scanner reader = new Scanner(f);
            int rows = 0;
            int cols = 0;

            try
            {
                BufferedReader rowCount = new BufferedReader(new FileReader(args[0]));
                while (rowCount.readLine() != null) {rows++;}
                rowCount.close();
            } catch (Exception e){e.printStackTrace();}
            try 
            {
                BufferedReader colCount = new BufferedReader(new FileReader(args[0]));
                cols = colCount.readLine().length();
                colCount.close();
            } catch (Exception e){e.printStackTrace();}

            enginePart.grid = new enginePart[rows][cols];

            int RowNum = 0;
            while (reader.hasNextLine()) 
            {
                String Row = reader.nextLine();
                for (int ColNum = 0; ColNum < Row.length(); ColNum++)
                {
                    char currentCell = Row.charAt(ColNum);
                    if (currentCell == '.'){new enginePart(RowNum, ColNum, 1, 0); continue;}
                    if (Character.isDigit(currentCell))
                    {
                        int value = Character.getNumericValue(currentCell);
                        int size = 1;
                        int nextDigitIndex = ColNum + 1;
                        while (nextDigitIndex < Row.length() &&Character.isDigit(Row.charAt(nextDigitIndex)))
                        {
                            value *= 10;
                            value += Character.getNumericValue(Row.charAt(nextDigitIndex));
                            nextDigitIndex += 1;    
                            size += 1;
                            ColNum += 1;
                        }

                        new enginePart(RowNum, ColNum, size, value);
                        continue;
                    }
                    if (currentCell == '*'){new gear(RowNum, ColNum); continue;}
                    new enginePart(RowNum, ColNum, 1, -1);
                }
                RowNum += 1;
            }
            reader.close();
        } catch (FileNotFoundException e)
        {
            System.err.println("error");
            e.printStackTrace();
        }
        enginePart.PrintGrid();
        enginePart.getSum();
        System.out.println(gear.getSum());
    }
}


class enginePart 
{
    public int xPos;
    public int yPos;
    public int size;
    public int value;
    public enginePart(int x, int y, int s, int v)
    {
        xPos = x;
        yPos = y;
        size = s;
        value = v;
        grid[x][y] = this;
    }

    public static enginePart[][] grid; 

    public static void PrintGrid()
    {
        for (int c = 0; c < grid.length; c++) 
        {
            for (int r = 0; r < grid[c].length; r++) 
            {
                if (grid[c][r] == null) {continue;}
                if (grid[c][r].value == 0) {System.out.print("."); continue;}

                System.out.print(grid[c][r].value);
                
            }    
            System.out.println("");
        }
    }

    public static int getSum()
    {
        int totalSum = 0;
        for (int c = 0; c < grid.length; c++) 
        {
            for (int r = 0; r < grid[c].length; r++) 
            {
                if (grid[r][c] == null) continue;
                if (grid[r][c].value <= 0) continue;
                if (!grid[r][c].isAdjacent()) continue;
                System.out.println(grid[r][c].value);
                totalSum += grid[r][c].value;
            }    
        }
        return totalSum;
    }

    public boolean isAdjacent() //aparrently yPos is the position of last digit.
    {
        boolean is = false;
        for (int r = xPos - 1; r <= xPos + 1; r++) 
        {
            if (r >= grid.length) continue;
            if (r < 0) continue;
            for (int c = yPos - size; c <= yPos + 1; c++) 
            {
                if (c >= grid[r].length) continue;
                if (c < 0) continue;
                if (grid[r][c] == null) continue;
                System.out.println(value + ": " + r + " : " + c + " x: " + xPos + " y: " + yPos);
                if (grid[r][c].value == -1) {is = true;}
                if (grid[r][c].value == -2) 
                {
                    Object obj = grid[r][c];
                    gear g = (gear) obj;
                    if (g.firstPartNum == 0){g.firstPartNum = value; continue;}
                    if (g.secondPartNum == 0){g.secondPartNum = value; continue;}
                    if (g.secondPartNum != 0 && g.firstPartNum != 0){g.isGear = false;}
                }
            }
        }
        return is;
    }
}

class gear extends enginePart 
{
    public int firstPartNum;
    public int secondPartNum;

    public boolean isGear = true;
    
    public gear(int x, int y)
    {
        super(x, y, 1, -2);
    }

    public int getRatio()
    {
        if (!isGear){return 0;}
        return firstPartNum * secondPartNum;
    }
    
    public static int getSum()
    {
        int totalSum = 0;
        for (int c = 0; c < grid.length; c++) 
        {
            for (int r = 0; r < grid[c].length; r++) 
            {
                if (grid[r][c] == null) continue;
                if (grid[r][c].value != -2) continue;
                Object obj = grid[r][c];
                gear g = (gear) obj;
                System.out.println(g.firstPartNum + ": " + g.secondPartNum);
                totalSum += g.getRatio();
            }    
        }
        return totalSum;
    }
}