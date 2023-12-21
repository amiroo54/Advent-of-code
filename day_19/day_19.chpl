use CyclicDist;
use IO;

config const path = "./example.txt";


var file = open(path, ioMode.r);
var reader = file.reader();
	
var data: string = "";
var hasLine = reader.read(data);

var wIndexes: domain(string);
var workflows: [wIndexes] owned workflow?;

var pIndexes: domain(int);
var parts: [pIndexes] owned part?;
var partI = 0;

var entryPoint: borrowed workflow?;

while (hasLine) {
	if data.startsWith('{') {
		var newData = data.replace('{', '');
		newData = newData.replace('}', '');
		var cats = newData.split(',');
		var p = new owned part();
		
		var x = cats[0].split('=');
		var m = cats[1].split('=');
		var a = cats[2].split('=');
		var s = cats[3].split('=');
		p.x = x(1): int;
		p.m = m(1): int;
		p.a = a(1): int;
		p.s = s(1): int;
		
		pIndexes.add(partI);
		parts[partI] = p;
		partI += 1;
	}
	else {
		if (data == "")
		{
			break;
		}
		var newData = data.replace('}', '');
		var splits = newData.split('{');
		var w = new owned workflow();
		var rules = splits(1);
		var name = splits(0);
		w.rules = rules;
		wIndexes.add[name];
		workflows[name] = w;
		
		if (name == 'in') {
			entryPoint = workflows[name];
		}
	}
	hasLine = reader.read(data);
}	

class workflow {
	var rules: string;
	
	proc primaryMethod() {
	assert(this.type == borrowed workflow);
	}
	
}

class part {

	var x: int;
	var m: int;
	var a: int;
	var s: int;

	proc primaryMethod() {
    assert(this.type == borrowed part);
  	}

	proc getRating(s:string) {
		if s == "x" {
			return this.x;
		}
		if s == "m" {
			return this.m;
		}
		if s == "a" {
			return this.a;
		}
		if s == "s" {
			return this.s;
		}
		return -1;
	}
}

proc goToDest (dest: string, p: borrowed part?)
{
	if dest == "A" {
		return 1;
	}
	if dest == "R" {
		return -1;
	}
	return evaluate(p, workflows[dest]!.rules);
}

proc evaluate(p: borrowed part?, rules: string): int {
	var r = rules.split(',');
	for i in r {

		if i == "A" {
			return 1;
		}
		if i == "R" {
			return -1;
		}
		
		var splits = i.split(":");
		var dest = splits.last;
		if splits.size == 1 {
			return goToDest(dest, p);
		}
		var comp = splits(0)[2..];
		var oper = i[1];
		var value = p!.getRating(i[0]);
		if oper == "<" {
			if value < comp:int {
				return goToDest(dest, p);
			}
		} else if oper == ">" {
			if value > comp:int {
				return goToDest(dest, p);
			}
		} else {

		}
	}
	return -2;
}
var totalSum = 0;
for part in parts {
	if evaluate(part.borrow(), entryPoint!.rules) == 1 {
		totalSum += part!.x + part!.m + part!.a + part!.s;
	};
}
writeln (totalSum);


