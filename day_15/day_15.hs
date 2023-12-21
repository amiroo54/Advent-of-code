import System.Environment
import System.IO
import Data.List.Split
import Data.Char (ord)
import Data.Array
import Debug.Trace

parseStep :: String -> (String, String)
parseStep step = do
    let operation = case reverse step of 
            ('-':_) -> drop (length step - 1) step
            _       -> drop (length step - 2) step

    let lable = case reverse step of
            ('-':_) -> init step
            _       -> init (init step)
        
    (lable, operation)

parseLine :: String -> [(String, String)]
parseLine line = do
    let steps = splitOn "," line
    map (parseStep) steps


removeFirst :: [Char] -> [Char]
removeFirst = \myList ->
    case myList of
        [] -> []
        x:xs -> xs

hash :: [Char] -> Int -> Int
hash [] current = current
hash char current = do
    let c = char!!0
    let ascii = getAscii c
    let newCurrent = (current + ascii) * 17 `rem` 256
    let newChar = removeFirst char
    hash newChar newCurrent

addLens :: Int -> Int -> [Int] -> [Int]
addLens lens index [] = [lens]
addLens lens 0 box = lens:box 
addLens lens i (a:box) = a : addLens lens (i - 1) box

getFocalFromOperator :: String -> Int
getFocalFromOperator operator = do
    let wy = putStrLn operator
    --(read (tail operator) :: Int)
    0

getEmptyBoxes :: [[Int]]
getEmptyBoxes = replicate 256 []

addToBox :: [(Int, String)] -> [[Int]] -> [[Int]]
addToBox step boxes = do
    map (\(hash, operator) -> mapBoxes hash operator boxes) step

mapBoxes :: Int -> String -> [[Int]] -> [Int]
mapBoxes hash operator boxes = do
    trace (show hash)
    let focal = getFocalFromOperator operator
    case length operator of
        2 -> addLens focal hash (boxes!!hash)
        1 -> addLens focal hash (boxes!!hash)

getAscii :: Char -> Int
getAscii c = ord c

main :: IO ()
main = do
    args <- getArgs
    case args of
        (firstArg:_) -> do
            file <- openFile firstArg ReadMode
            line <- hGetContents file
            let steps = parseLine line
            let hashes = map (\(s, _) -> hash s 0) steps
            let hashmap = map (\(h, (_, o)) -> (h, o)) (zip hashes steps)
            let emptyBoxes = getEmptyBoxes
            let boxes = addToBox hashmap [[]]
            print boxes
            hClose file
        _ -> putStrLn "no args"

