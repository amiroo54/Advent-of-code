local filePath = arg[1]

function dump(o)
    local s = "{"
    for key, value in pairs(o) do
        s = s..value
    end
    print(s.."}")
end
 
Springs = {}
for line in io.lines(filePath) do

    local mainData = string.match(line, "(.-) ")
    local numbers = string.match(line, "(%d.*)")
    local numList = {}
    local outData = ""
    for i=1,5 do
        for num in string.gmatch(numbers, "%d+") do
            table.insert(numList, tonumber(num))
        end
        outData = outData..mainData
    end
    table.insert(Springs, {outData, numList})
    
end

function CheckSpring(spring, data)
    local index = 1
    for g in string.gmatch(spring, "(#+)") do
        if #g ~= data[index] then
            return false
        end
        index = index + 1
    end
    if index - 1 ~= #data then return false end
    return true
end

Count = 0
local function generateSpring(spring, data)
    local string1 = string.gsub(spring, "?", ".", 1)
    if string1 == spring then
        if CheckSpring(string1, data) then
            Count = Count + 1
        end    
        return 
    else
        generateSpring(string1, data)
    end

    local string2 = string.gsub(spring, "?", "#", 1)
    if string2 == spring then
        if CheckSpring(string2, data) then
            Count = Count + 1
        end
        return
    else
        generateSpring(string2, data)
    end
end
for index, entry in pairs(Springs) do
    local spring = entry[1]
    local data = entry[2]
    generateSpring(spring, data)

end
print(CheckSpring(".###..##....", {3, 2, 1}))
print(Count)