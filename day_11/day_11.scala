import scala.io.Source
import scala.collection.mutable.ListBuffer
object day_11 
{

  def exapnd (sky: ListBuffer[ListBuffer[Char]]) : ListBuffer[ListBuffer[Char]] =
  {
    var exapndedSky: ListBuffer[ListBuffer[Char]] = new ListBuffer[ListBuffer[Char]]()
    for row <- sky do
      exapndedSky += row
      if row.forall(_ == row.head) then
        exapndedSky += row

    return exapndedSky
  }

  def prLongSky (sky: ListBuffer[ListBuffer[Char]]) : Unit = 
  {
    for row <- sky do
      for col <- row.toSeq do
        print(col)
      println()
  }

  def rotateSky (sky: ListBuffer[ListBuffer[Char]]) : ListBuffer[ListBuffer[Char]] =
  {
    var rotatedSky: ListBuffer[ListBuffer[Char]] = new ListBuffer[ListBuffer[Char]]()
    for col <- 0 to sky(0).length - 1 do
      rotatedSky += ListBuffer[Char]()
      for row <- sky do
        rotatedSky.last += row(col)
    return rotatedSky
  }
  
  def getExpanded (sky: ListBuffer[ListBuffer[Char]]) : ListBuffer[Long] = 
  {
    var out: ListBuffer[Long] = new ListBuffer[Long]()
    for row <- 0 to sky.length - 1 do
      if sky(row).forall(_ == sky(row).head) then
        out += row
    
    return out
  } 

  def main(args: Array[String]): Unit = 
  {
    //reading input file
    val fileName = args(0)
    var sky: ListBuffer[ListBuffer[Char]] = new ListBuffer[ListBuffer[Char]]()

    for line <- Source.fromFile(fileName).getLines do
      val listToAdd: ListBuffer[Char] = new ListBuffer[Char]()

      for char <- line.toSeq do 
        listToAdd += char
      sky += listToAdd
  
    /*this is for part 1
    var exapndedSky: ListBuffer[ListBuffer[Char]] = rotateSky(exapnd(rotateSky(exapnd(sky))))
    prLongSky(exapndedSky)
    
    var galaxies: ListBuffer[(Long, Long)] = new ListBuffer[(Long, Long)]()
    for row <- 0 to exapndedSky.length - 1 do
      for col <- 0 to exapndedSky(row).length - 1 do
        if exapndedSky(row)(col) == '#' then
          var index = (row, col)
          galaxies += index
    
    var totalSum: Long = 0
    for first <- galaxies do
      for second <- galaxies do
        totalSum += ((second(0) - first(0)).abs + (second(1) - first(1)).abs)
    prLongln(totalSum/2)
    */
    var expandedRows: ListBuffer[Long] = getExpanded(sky)
    var expandedCols: ListBuffer[Long] = getExpanded(rotateSky(sky))
    println(expandedRows.toString + "   :   " + expandedCols.toString)
    //getting all galaxies
    var galaxies: ListBuffer[(Long, Long)] = new ListBuffer[(Long, Long)]()
    for row <- 0 to sky.length - 1 do
      for col <- 0 to sky(row).length - 1 do
        if sky(row)(col) == '#' then
          var index = (row.toLong, col.toLong)
          galaxies += index

    var totalSum: Long = 0
    for first <- galaxies do
      for second <- galaxies do
        var numberOfCrosses: Long = 0 //how many times we crossed an empty row or col
        for row <- expandedRows do
          if Math.min(first(0), second(0)) < row && row < Math.max(first(0), second(0)) then
            //println(first.toString + " : " + second.toString + " : Row :" + row.toString)
            numberOfCrosses += 1
        for col <- expandedCols do
          if Math.min(first(1), second(1)) < col && col < Math.max(first(1), second(1)) then
            //println(first.toString + " : " + second.toString + " : Col :" + col.toString)
            numberOfCrosses += 1
        println("Crosses: " + numberOfCrosses.toString + ", " + first.toString + " : " + second.toString)
        totalSum += ((second(0) - first(0)).abs + (second(1) - first(1)).abs) + (numberOfCrosses * 1000000l) - numberOfCrosses
    
    println(totalSum/2) // divided by two cause every pair is counted twice.
  } 
}

