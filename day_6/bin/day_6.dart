import 'dart:io';
import 'dart:collection';
void main(List<String> arguments) 
{
  String content = File(arguments[0]).readAsStringSync();
  //Map<int, int> data = getDate(content);

  int result = 1;

  //data.forEach((key, value) {result *= calculateMarginOfError(key, value);});

  (int, int) data = getDataForSecondPart(content);
  result = calculateMarginOfError(data.$1, data.$2);

  print(result);
}

(int, int) getDataForSecondPart(String file)
{
  List<String> lines = file.split("\n");

  String stringTime = lines[0].replaceAll(" ", "").replaceAll("Time:", "");
  
  String stringDistance = lines[1].replaceAll(" ", "").replaceAll("Distance:", "");
  
  print(stringDistance);

  return (int.parse(stringTime), int.parse(stringDistance));
}

Map<int, int> getDate(String file)
{
  List<String> lines = file.split("\n");

  List<String> times = lines[0].split(" ");
  times.remove("Time:");
  times.removeWhere((String i) => i.isEmpty);
  List<String> distances = lines[1].split(" ");
  distances.remove("Distance:");
  distances.removeWhere((String i) => i.isEmpty);

  Map<int, int> data = new Map<int, int>();

  for (int i = 0; i < times.length; i++)
  {
    data[int.parse(times[i])] = int.parse(distances[i]);
  }

  return data;
}

int calculateMarginOfError(int time, int distance)
{
  int margin = 0;
  for (int i = 0; i < time; i++)
  {
    if ((time - i) * i > distance)
    {
      margin += 1;
    }
  }
  return margin;
}