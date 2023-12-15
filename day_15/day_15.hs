import System.Environment
import System.IO
import Data.List.Split
import Data.Char (ord)
            
parseLine :: String -> [String]
parseLine line = splitOn "," line

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
            let hashes = map (\s -> hash s 0) steps
            let total = sum hashes
            print total
            hClose file
        _ -> putStrLn "no args"

