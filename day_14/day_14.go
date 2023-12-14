package main

import (
	"bufio"
	"fmt"
	"os"
)

func printPlatform(platform [][]rune) {
	for row := 0; row < len(platform); row++ {
		for col := 0; col < len(platform[row]); col++ {
			fmt.Print(string(platform[row][col]))
		}
		println()
	}
}

func titlPlatform(platform [][]rune) {
	var isStable bool = true
	for row := 1; row < len(platform); row++ {
		for col := 0; col < len(platform[row]); col++ {
			if platform[row][col] == 'O' && platform[row-1][col] == '.' {
				platform[row][col] = '.'
				platform[row-1][col] = 'O'
				isStable = false
			}
		}
	}
	if !isStable {
		titlPlatform(platform)
	}
}

func getPlatformLoad(platform [][]rune) int {
	var totalNum int = 0
	for row := 0; row < len(platform); row++ {
		for col := 0; col < len(platform[row]); col++ {
			if platform[row][col] == 'O' {
				println(len(platform) - row)
				totalNum += len(platform) - row
			}
		}
	}
	return totalNum
}

func main() {
	var filePath = os.Args[1]
	file, err := os.Open(filePath)
	if err != nil {
		panic(err)
	}
	defer file.Close()

	scanner := bufio.NewScanner(file)
	platform := make([][]rune, 0)

	for scanner.Scan() {
		var rowText = scanner.Text()
		row := make([]rune, 0)
		for char := 0; char < len(rowText); char++ {
			fmt.Println(rowText[char])
			row = append(row, rune(rowText[char]))
		}
		platform = append(platform, row)
	}
	printPlatform(platform)
	titlPlatform(platform)
	fmt.Println()
	printPlatform(platform)
	println(getPlatformLoad(platform))
}
