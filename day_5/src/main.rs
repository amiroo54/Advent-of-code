use std::env::args;
use std::fs::File;
use std::io::BufReader;
use std::io::prelude::*;
fn main() 
{
    let args: Vec<String> = args().collect();
    let file_location: &String = &args[1];
    let content = open_file(file_location);


    let range: Vec<Vec<(i64, i64, i64)>>  = get_ranges(&content);
    let low = get_seeds(&content, &range);
    println!("lowest loc is : {}", low);
}

fn open_file(path: &String) -> String
{
    let file = File::open(path).expect("failed to open file.");
    let mut buf_reader = BufReader::new(file);
    let mut contents = String::new();
    buf_reader.read_to_string(&mut contents).expect("reader failed to read.");
    contents
}

fn get_seeds(c: &String, ranges: &Vec<Vec<(i64, i64, i64)>>) -> i64
{
    let mut l = c
    .split("\n\n")
    .collect::<Vec<&str>>()[0]
    .split(" ")
    .collect::<Vec<&str>>();
    l.remove(0);
    
    let seed_ranges : Vec<i64> = l
    .iter()
    .map(|&s| s.parse().unwrap())
    .collect();
    let mut min = i64::MAX;
    for i in (0..seed_ranges.len()).step_by(2)
    {
        let start = seed_ranges[i];
        let length = seed_ranges[i + 1];
        for j in start..start+length
        {
            let loc = get_loc(&j, ranges);
            if min > loc {min = loc;}
        }
    }
    min
}

fn get_loc(seed: &i64, ranges: &Vec<Vec<(i64, i64, i64)>>) -> i64
{
    let mut next : i64 = *seed;
    
    for map in ranges
    {
        next = calc_next(&next, map);
    }
    next
}

fn calc_next (seed : &i64, map: &Vec<(i64, i64, i64)>) -> i64
{
    let mut next = *seed;
    for range in map
    {
        let src = range.0;
        let dest = range.1;
        let range_lenght = range.2;
        let diff = dest - src;
        let dist = next - src;
        //println!("next:{}, src:{}, dest:{}, distance:{}, length:{}", next, src, dest, dist, range_lenght);
        if src <= next && next < src + range_lenght
        {
            next = src + dist + diff;
            return next;
        }

    } 
    next
}

fn get_ranges(c: &String) -> Vec<Vec<(i64, i64, i64)>>
{
    let mut maps = Vec::<Vec::<(i64, i64, i64)>>::new();
    let mut l : Vec<&str> = c
    .split("\n\n")
    .collect();
    l.remove(0);
    for map in l
    {
        let mut range = Vec::<(i64, i64, i64)>::new();
        let mut range_list : Vec<&str> = map.split("\n").collect();
        range_list.remove(0);

        for r in range_list
        {
            let nums : Vec<&str> = r.split(" ").collect();
            let diff : i64 = nums[2].parse().unwrap();
            let dest : i64 = nums[0].parse().unwrap();
            let src : i64 = nums[1].parse().unwrap();
            range.push((src, dest, diff));
        }

        maps.push(range);
    }
    maps
}