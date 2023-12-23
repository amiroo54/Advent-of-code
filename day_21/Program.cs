using System.Data;
using System.Numerics;
using System.Security.Cryptography.X509Certificates;

const int steps = 6;

string[] lines = File.ReadAllLines(args[0]);
Plot.garden = new Plot[lines.Length, lines[0].Length];
Plot.currentSteps = new int[lines.Length, lines[0].Length];
for (int row = 0; row < lines.Length; row++)
{
    for (int col = 0; col < lines[row].Length; col++)
    {
        switch (lines[row][col]) 
        {
            case '#': new Plot(row, col, Plot.State.stone); break;
            case '.': new Plot(row, col, Plot.State.empty); break;
            case 'S': Plot.start = new Plot(row, col, Plot.State.empty); 
            Plot.currentSteps[row, col] = 1; break;
        }
    }
}

for (int i = 0; i < steps; i++)
{
    List<(int, int)> poses = [];
    for (int row = 0; row < Plot.currentSteps.GetLength(0); row++)
    {
        for (int col = 0; col < Plot.currentSteps.GetLength(1); col++)
        {
            if (Plot.currentSteps[row, col] == 1)
            {
                poses.Add((row, col));
            }
        }
    }
    //Console.WriteLine(poses.Count);
    Plot.currentSteps = new int[Plot.currentSteps.GetLength(0), Plot.currentSteps.GetLength(1)];

    foreach ((int, int) pos in poses)
    {
        int down = pos.Item1 + 1;
        if (pos.Item1 == Plot.garden.GetLength(0) - 1)
        {
            down = 0;
        }
        int right = pos.Item2 + 1;
        if (pos.Item2 == Plot.garden.GetLength(1) - 1)
        {
            right = 0;
        }
        int up = pos.Item1 - 1;
        if (pos.Item1 == 0)
        {
            up = Plot.garden.GetLength(0) - 1;
        }
        int left = pos.Item2 - 1;
        if (pos.Item2 == 0)
        {
            left = Plot.garden.GetLength(1) - 1;
        }
        if (Plot.garden[down, pos.Item2].state == Plot.State.empty)
        {
            Plot.currentSteps[down, pos.Item2] += 1;
        }
        if (Plot.garden[pos.Item1, right].state == Plot.State.empty)
        {
            Plot.currentSteps[pos.Item1, right] += 1;
        }
        if (Plot.garden[up, pos.Item2].state == Plot.State.empty)
        {
            Plot.currentSteps[up, pos.Item2] += 1;
        }
        if (Plot.garden[pos.Item1, left].state == Plot.State.empty)
        {
            Plot.currentSteps[pos.Item1, left] += 1;
        }
    }


}

int finalPoses = 0;
for (int row = 0; row < Plot.currentSteps.GetLength(0); row++)
{
    for (int col = 0; col < Plot.currentSteps.GetLength(1); col++)
    {
        Console.Write(Plot.currentSteps[row, col]);
        finalPoses += Plot.currentSteps[row, col];
    }
    Console.WriteLine();
}
Console.WriteLine(finalPoses);

public class Plot 
{
    public static Plot? start;
    public static Plot[,] garden = new Plot[0, 0];
    public static int[,] currentSteps = new int[0, 0];
    public int x;
    public int y;
    public State state;
    public Plot(int x, int y, State s)
    {
        this.x = x;
        this.y = y;
        this.state = s;
        garden[x, y] = this;
    }
    public enum State
    {
        empty, stone
    }
}