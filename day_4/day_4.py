import os, sys

folderPath = sys.argv[1]
totalSum = 0

class card():
    def __init__(self):
        self.nums = []
        self.winningNums = []
        self.index = 0

    def countWinning(self):
        total = 0
        for n in self.winningNums:
            if n in self.nums:
                total += 1
        return total
    
    def matchRules(self):
        total = self.countWinning()
        global totalSum
        totalSum += 1

        for i in range(self.index, self.index + total):
            cardClasses[i].matchRules()

cardClasses = []
with open(folderPath) as f:
    data = f.read()

    cards = data.split("\n")
    
    for c in cards:
        ca = card()
        ca.index = int(c.split(":")[0].replace("Card ", ""))
        c = c.replace("  ", " ")
        for i in c.split(":")[1].split("|")[0].strip().split(" "):
            ca.winningNums.append(int(i))
        for i in c.split("|")[1].strip().split(" "):
            ca.nums.append(int(i))
        cardClasses.append(ca)


for c in cardClasses:
    c.matchRules()

print(totalSum)