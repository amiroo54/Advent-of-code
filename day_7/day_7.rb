def compareCards(a, b)
    if a == b then return nil end
    adigit = a.to_i
    bdigit = b.to_i
    
    hash = {"A"=>13, "K"=>12, "Q"=>11, "J"=>0, "T"=>10}

    if adigit == 0 then a = hash[a] end
    if bdigit == 0 then b = hash[b] end

    if a.to_i > b.to_i then return true else return false end
end

def compare(cardA, cardB)
    if cardB == nil then return true end
    a = getHandType(cardA)
    b = getHandType(cardB)
    if a == b then 
        for i in 0..4 do
            result = compareCards(cardA[i], cardB[i])
            if result == nil then next end
            if not result then
                return false
            else
                return true
            end
        end
    end
    if a < b then return true else return false end
end

def getHandType(cards)
    lables = Hash.new(0)
    num_of_j = 0
    cards.each_char do |c|
        if not c == "J" then
            lables[c] = lables[c] + 1
        else num_of_j += 1 end
    end

    if num_of_j == 5 then return 1 end

    max_value = lables.values.max
    lables.each do |key, value|
        if value == max_value
            lables[key] = max_value + num_of_j
            break 
        end
    end

    if lables.length == 1 then
        return 1
    end 
    if lables.length == 2 then
        if lables.values[0] == 1 or lables.values[1] == 1 then
            return 2
        else
            return 3
        end 
    end
    if lables.length == 3 then 
        if lables.values[0] == 3 or lables.values[1] == 3 or lables.values[2] == 3 then
            return 4
        else
            return 5
        end
    end
    if lables.length == 4 then
        return 6
    end 
    if lables.length == 5 then
        return 7
    end 
end

def start()
    file = ARGV[0]

    contents = File.open(file).read

    hands = contents.split("\n")
    hands_unsorted = []

    hands.each do |hand|
        hand = hand.split(" ")
        hands_unsorted.push(hand)
    end
    
    hands_sorted = []
    for i in 0..hands_unsorted.length do
        highest_rank = ["12345", "0"]
        for hand in hands_unsorted do
            if compare(hand[0], highest_rank[0]) then
                highest_rank = hand
            end
        end
        hands_sorted.push(highest_rank)
        hands_unsorted.delete(highest_rank)
    end
    i = hands_sorted.length - 1
    total = 0
    hands_sorted.each do |hand|
        total += hand[1].to_i * i
        i -= 1
    end
    puts total
end

start