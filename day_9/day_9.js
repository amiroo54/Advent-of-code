"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var fs = require("fs");
var process_1 = require("process");
var content = fs.readFileSync(process_1.argv[2], 'utf-8');
var seqs = content.split("\n");
var totalSum = 0;
for (var i in seqs) {
    var seq = seqs[i].split(" ");
    seq = seq.reverse();
    var numseq = [];
    for (var i_1 in seq) {
        numseq.push(parseInt(seq[i_1]));
    }
    console.log(seq);
    var isAllZero = false;
    var sets = [numseq];
    var index = 1;
    while (!isAllZero) {
        sets[index] = getSeqDiff(sets[index - 1]);
        isAllZero = true;
        for (var i_2 = 0; i_2 < sets[index].length; i_2++) {
            if (sets[index][i_2] != 0) {
                isAllZero = false;
            }
        }
        index += 1;
    }
    var numToPush = 0;
    for (var i_3 = sets.length - 1; i_3 > 0; i_3--) {
        sets[i_3].push(numToPush);
        numToPush = sets[i_3 - 1][sets[i_3 - 1].length - 1] + sets[i_3][sets[i_3].length - 1];
    }
    sets[0].push(numToPush);
    console.log(sets);
    totalSum += numToPush;
}
console.log(totalSum);
function getSeqDiff(seq) {
    var out = [];
    for (var i = 0; i < seq.length - 1; i++) {
        out.push(seq[i + 1] - seq[i]);
    }
    return out;
}
