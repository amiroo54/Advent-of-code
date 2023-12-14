<?php

$content = file($argv[1], FILE_IGNORE_NEW_LINES);

function reverseDir($dir)
{
    switch ($dir)
    {
        case "n": return "s";
        case "s": return "n";
        case "w": return "e";
        case "e": return "w";
    }
}

class tile
{
    public $x, $y;
    public $distance;
    public $type;

    public function __construct($x, $y, $type = ".")
    {
        $this->x = $x;
        $this->y = $y;
        $this->type = $type;
    }

    public function get_next_pipe(&$entry_point, $map)
    {
        switch ($this->type)
        {
            case "S":
                $t = $entry_point;
                $entry_point = reverseDir($entry_point);
                return $this->get_neighbor($t, $map);
                break;

            case "|" : 
                if ($entry_point == "n")
                {$entry_point = "n"; return $this->get_neighbor("s", $map);} 
                else 
                {$entry_point = "s"; return $this->get_neighbor("n", $map);}
                break;

            case "-" : 
                if ($entry_point == "e")
                {$entry_point = "e"; return $this->get_neighbor("w", $map);} 
                else 
                {$entry_point = "w"; return $this->get_neighbor("e", $map);}
                break;

            case "L" : 
                if ($entry_point == "n")
                {$entry_point = "w"; return $this->get_neighbor("e", $map);} 
                else 
                {$entry_point = "s"; return $this->get_neighbor("n", $map);}
                break;
        
            case "J" : 
                if ($entry_point == "w")
                {$entry_point = "s"; return $this->get_neighbor("n", $map);} 
                else 
                {$entry_point = "e"; return $this->get_neighbor("w", $map);}
                break;

            case "7" : 
                if ($entry_point == "s")
                {$entry_point = "e"; return $this->get_neighbor("w", $map);} 
                else 
                {$entry_point = "n"; return $this->get_neighbor("s", $map);}
                break;

            case "F" : 
                if ($entry_point == "e")
                {$entry_point = "n"; return $this->get_neighbor("s", $map);} 
                else 
                {$entry_point = "w"; return $this->get_neighbor("e", $map);}
                break;

        }
    }
    
    public function get_neighbor($dir, $map)
    {
        switch ($dir)
        {
            case "s":
                return $map[$this->x + 1][$this->y];
                break;
            case "n":
                return $map[$this->x - 1][$this->y];
                break;
            case "e":
                return $map[$this->x][$this->y + 1];
                break;
            case "w":
                return $map[$this->x][$this->y - 1];
                break;
        }
   }
}

function is_inclosed($tile, $map)
{
    
}



$start = null;
$map = array(array());
for ($row = 0; $row < count($content); $row++)
{
    for ($col = 0; $col < strlen($content[$row]); $col++)
    {
        $map[$row][$col] = new tile($row, $col, substr($content[$row], $col, 1));
        if ($map[$row][$col]->type == "S")
        {
            $start = $map[$row][$col];
        }
    }
}


$start->distance = 0;
$dirs = ["s", "n", "e", "w"];
$prohibited_tiles = 
[
    "s" => ["-", "7", "F"],
    "n" => ["-", "L", "J"],
    "e" => ["|", "F", "L"],
    "w" => ["|", "J", "F"]
];

for ($dir = 0; $dir < 4; $dir++)
{
    try
    {
        $first_neighibor_type = $start->get_neighbor($dirs[$dir], $map)->type;
    }
    catch (e)
    {
        continue;
    }
    
    if (in_array($first_neighibor_type, $prohibited_tiles[$dirs[$dir]]))
    {
        continue;
    }
    $current = $start;
    $highest_distance = 0;
    while ($current->type != ".")
    {
        $current = $current->get_next_pipe($dirs[$dir], $map);
        if ($current == NULL){break;}
        if ($highest_distance > 0 && $current->type=="S")
        {
            break;
        }
        $highest_distance += 1;
        $current->distance = $highest_distance;
    }
    echo ($highest_distance / 2);
    echo ("\n");
}

?>