import * as fs from 'fs';
import { argv } from 'process';

let content : string  = fs.readFileSync(argv[2], 'utf-8');

let seqs : string[] = content.split("\n");
let totalSum : number = 0;
for (let i in seqs)
{
    let seq : string[] = seqs[i].split(" ");
    seq = seq.reverse();
    let numseq : number[] = [];
    for (let i in seq) {numseq.push(parseInt(seq[i]));}
    console.log(seq);

    let isAllZero : boolean = false;
    let sets : number[][] = [numseq];
    let index : number = 1;
    while (!isAllZero)
    {   
        sets[index] = getSeqDiff(sets[index - 1]);
        isAllZero = true;
        for (let i = 0; i < sets[index].length; i++)
        {   
            if (sets[index][i] != 0){isAllZero = false;}
        }
        index += 1;
    }

    let numToPush = 0;
    for (let i = sets.length - 1; i > 0; i--)
    {
        sets[i].push(numToPush);
        numToPush = sets[i - 1][sets[i - 1].length - 1] + sets[i][sets[i].length - 1];
    }
    sets[0].push(numToPush);
    console.log(sets);
    totalSum += numToPush;

}
console.log(totalSum);
function getSeqDiff(seq : number[]) : number[]
{
    let out : number[] = [];
    for (let i = 0; i < seq.length - 1; i++)
    {
        out.push(seq[i + 1] - seq[i]);
    }

    return out;
}