package main

import (
	"bufio"
	"bytes"
	"encoding/gob"
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

func titlPlatformNorth(platform [][]rune) [][]rune {
	newPlat := platform
	var isStable bool = true
	for row := 1; row < len(platform); row++ {
		for col := 0; col < len(platform[row]); col++ {
			if platform[row][col] == 'O' && platform[row-1][col] == '.' {
				newPlat[row][col] = '.'
				newPlat[row-1][col] = 'O'
				isStable = false
			}
		}
	}
	if !isStable {
		return titlPlatformNorth(newPlat)
	}
	return newPlat
}

func titlPlatformWest(platform [][]rune) [][]rune {
	newPlat := platform
	var isStable bool = true
	for row := 0; row < len(platform); row++ {
		for col := 1; col < len(platform[row]); col++ {
			if platform[row][col] == 'O' && platform[row][col-1] == '.' {
				newPlat[row][col] = '.'
				newPlat[row][col-1] = 'O'
				isStable = false
			}
		}
	}
	if !isStable {
		return titlPlatformWest(newPlat)
	}
	return newPlat
}

func titlPlatformSouth(platform [][]rune) [][]rune {
	newPlat := platform
	var isStable bool = true
	for row := 0; row < len(platform)-1; row++ {
		for col := 0; col < len(platform[row]); col++ {
			if platform[row][col] == 'O' && platform[row+1][col] == '.' {
				newPlat[row][col] = '.'
				newPlat[row+1][col] = 'O'
				isStable = false
			}
		}
	}
	if !isStable {
		return titlPlatformSouth(newPlat)
	}
	return newPlat
}

func titlPlatformEast(platform [][]rune) [][]rune {
	newPlat := platform
	var isStable bool = true
	for row := 0; row < len(platform); row++ {
		for col := 0; col < len(platform[row])-1; col++ {
			if platform[row][col] == 'O' && platform[row][col+1] == '.' {
				newPlat[row][col] = '.'
				newPlat[row][col+1] = 'O'
				isStable = false
			}
		}
	}
	if !isStable {
		return titlPlatformEast(newPlat)
	}
	return newPlat
}

func getPlatformLoad(platform [][]rune) int {
	var totalNum int = 0
	for row := 0; row < len(platform); row++ {
		for col := 0; col < len(platform[row]); col++ {
			if platform[row][col] == 'O' {
				totalNum += len(platform) - row
			}
		}
	}
	return totalNum
}

func doACycle(platform [][]rune) [][]rune {
	platform = titlPlatformNorth(platform)
	platform = titlPlatformWest(platform)
	platform = titlPlatformSouth(platform)
	platform = titlPlatformEast(platform)
	return platform
}

func Hash[t any](s t) []byte {
	var b bytes.Buffer
	gob.NewEncoder(&b).Encode(s)
	return b.Bytes()
}

func CompareHash(a, b []byte) bool {
	a = append(a, b...)
	c := 0
	for _, x := range a {
		c ^= int(x)
	}
	return c == 0
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
			row = append(row, rune(rowText[char]))
		}
		platform = append(platform, row)
	}

	for i := 0; i < 1000; i++ {
		doACycle(platform)
	}
	fmt.Println(getPlatformLoad(platform))
}
