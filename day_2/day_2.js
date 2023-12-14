
folderPath = process.argv[2];
const maxRed = 12;
const maxBlue = 14;
const maxGreen = 13;
const { count } = require('console');
const fs = require('fs');
try
{
    const filedata = fs.readFileSync(folderPath, 'utf8');
    let totalCount = 0;
    lines = filedata.split("\n");
    for (g = 0; g < lines.length; g++)
    {
        let game = lines[g];
        game = game.split(":")
        let gameid = game[0].replace("Game ", "");
        let rounds = game[1].split(";");
        let minBlue = 0;
        let minRed = 0;
        let minGreen = 0;
        for (r = 0; r < rounds.length; r++)
        {
            let round = rounds[r];
            let cubes = round.split(",");
            for (c = 0; c < cubes.length; c++)
            {
                let cube = cubes[c];
                let data = cube.split(" ");
                let count = parseInt(data[1]);
                let color = data[2];
                console.log(count, color, minBlue, minGreen, minRed);
                if (color == "blue" && count > minBlue)
                {
                    minBlue = count;
                }
                if (color == "red" && count > minRed)
                {
                    minRed = count;
                }
                if (color == "green" && count > minGreen)
                {
                    minGreen = count;
                }
            }
        }
        let Power = parseInt(minBlue) * parseInt(minGreen) * parseInt(minRed)
        //console.log("Game " + gameid + " Red: " + minRed + " Blue: " + minBlue + " Green: " + minGreen + " Power: " + Power);
        totalCount += Power;
    }
    console.log(totalCount);
}
catch (err)
{
    console.log(err);
}