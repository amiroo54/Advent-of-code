import java.io.File
import kotlin.math.*
val path = args[0]

var patterns: MutableList<MutableList<MutableList<Char>>> = mutableListOf() //patters, lines, chars

var patternToAdd: MutableList<MutableList<Char>> = mutableListOf()
File(path).forEachLine {
    if (it == "")
    {
        patterns.add(patternToAdd)
        patternToAdd = mutableListOf()
    }
    else
    {
        var lineToAdd: MutableList<Char> = mutableListOf()
        for (i in 0..it.length - 1)
        {
            lineToAdd.add(it[i])
        }
        patternToAdd.add(lineToAdd)
    }
}
patterns.add(patternToAdd)

fun printPattern(pattern: MutableList<MutableList<Char>>): Unit
{
    for (row in pattern)
    {
        for (col in row)
        {
            print(col)
        }
        println()
    }
}


fun rotatePattern(pattern: MutableList<MutableList<Char>>): MutableList<MutableList<Char>>
{
    var rotatedPattern: MutableList<MutableList<Char>> = mutableListOf()
    for (col in 0..pattern[0].size - 1)
    {
        rotatedPattern.add(mutableListOf())
        for (row in pattern)
        {
            rotatedPattern.last().add(row[col])
        }
    } 
    return rotatedPattern
}

fun getMirrorLine(pattern: MutableList<MutableList<Char>>): MutableList<Int>
{
    var out: MutableList<Int> = mutableListOf()
    for (line in 0..pattern.size - 2)
    {
        if (pattern[line] == pattern[line + 1])
        {
            var dis1: Int = line
            var dis2: Int = pattern.size - line
            val distanceToClosestEdge = min(line, pattern.size - line - 2)
            var isThisLineMirror = true
            for (i in 0..distanceToClosestEdge)
            {
                if (pattern[line - i] != pattern[line + i + 1])
                {
                    isThisLineMirror = false
                }
            }
            if (isThisLineMirror){out.add(line + 1)}
        }
    }
    return out //1 -> fix indexing error.
}
var allHor: MutableList<Int> = mutableListOf()
var allVer: MutableList<Int> = mutableListOf()
for (pattern in patterns) 
{
    allHor.addAll(getMirrorLine(pattern))
    allVer.addAll(getMirrorLine(rotatePattern(pattern)))
}

println(allHor.sum() * 100 + allVer.sum())