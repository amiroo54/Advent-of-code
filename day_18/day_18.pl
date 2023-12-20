#!/usr/bin/perl
use strict;
use warnings;
use feature 'switch';
my $path = $ARGV[0];
my @instructions = ();

sub print_2d_array {
    my $array_ref = shift;  # Get the reference to the 2D array
    foreach my $row (@{$array_ref}) {
        print "[" . join(", ", @{$row}) . "]\n";
    }
}

sub calculate_polygon_properties {
    my @coordinates = @_;

    my $num_of_coords = scalar @coordinates;

    # Closed polygon, so the last point is the same as the first
    push @coordinates, $coordinates[0];

    my $area = 0;
    my $circumference = 0;

    for my $i (0..$num_of_coords-1) {
        my ($x1, $y1) = @{$coordinates[$i]};
        my ($x2, $y2) = @{$coordinates[$i + 1]};
        $area += ($x1 * $y2 - $x2 * $y1);
        $circumference += sqrt(($x2 - $x1)**2 + ($y2 - $y1)**2);
    }

    $area = abs($area / 2);
    
    return ($area, $circumference);
}



open(my $file, '<', $path) or die "not opened";
while (my $line = <$file>)
{
    chomp $line;
    my @splitLine = split(/ /, $line);
    my @hexadecimalList = $splitLine[2] =~ /(..)/g;
    shift @hexadecimalList;
    my $fullHexNumb = join("", @hexadecimalList);
    my $dir = substr($fullHexNumb, -1, 1, '');
    my $dis = hex($fullHexNumb);
    if ($dir == 0)
    {$dir = "R"}
    elsif ($dir == 1)
    {$dir = "D"}
    elsif ($dir == 2)
    {$dir = "L"}
    elsif ($dir == 3)
    {$dir = "U"}
    push @instructions, [$dir, $dis];
}
close($file);

my @coords = ([0, 0]);


for my $instruction (0..scalar @instructions - 1)
{
    my $dir = $instructions[$instruction][0];
    my $dis = $instructions[$instruction][1];
    my $prev = $coords[-1];
    if ($dir eq "U")
    {
        push @coords, [$prev->[0] - $dis, $prev->[1]];
    }
    elsif ($dir eq "D")
    {
        push @coords, [$prev->[0] + $dis, $prev->[1]];
    }
    elsif ($dir eq "R")
    {
        push @coords, [$prev->[0], $prev->[1] + $dis];
    }
    elsif ($dir eq "L")
    {   
        push @coords, [$prev->[0], $prev->[1] - $dis];
    }
}
my ($area, $circ) = calculate_polygon_properties(@coords);
print $area."\n";
print $circ."\n";
print ($area + ($circ/2) + 1);