#include<iostream>
#include<string>
#include<fstream>
#include<map>
using namespace std;
string convert_text_to_digit(map<string, string> mp, string s)
{
    string out;
    map<int, string> c;
    
    for (std::map<string, string>::iterator it = mp.begin(); it != mp.end(); ++it) 
    {
        size_t start_pos = 0;
        while((start_pos = s.find(it->first, start_pos)) != std::string::npos) 
        {
            c[start_pos] = it->second;
            start_pos += 1;
        }
    }
    auto i = c.begin();
    auto e = c.rbegin();
    cout << e->second << endl;
    return i->second + e->second;
}
int main (int argc, char* argv[])
{
    map<string, string> letter_to_num = {
        {"one", "1"},
        {"two", "2"},
        {"three", "3"},
        {"four", "4"},
        {"five", "5"},
        {"six", "6"},
        {"seven", "7"},
        {"eight", "8"},
        {"nine", "9"},
        {"1", "1"},
        {"2", "2"},
        {"3", "3"},
        {"4", "4"},
        {"5", "5"},
        {"6", "6"},
        {"7", "7"},
        {"8", "8"},
        {"9", "9"},
    };
    ifstream f;
    f.open(argv[1]);
    if (!f.is_open())
    {
        cout << "file is not open" << endl;
        return 0;
    }
    string l;
    int64_t totalSum = 0;
    while(getline(f, l))
    {
        int16_t first_num = -1;
        int16_t second_num = 0;
        l = convert_text_to_digit(letter_to_num, l);
        for (int i = 0; i < l.length(); i++)
        {
            if (isdigit(l[i]))
            {
                if (first_num == -1){first_num = l[i] - '0';}
                second_num = l[i] - '0';
            }
        }
        totalSum += first_num * 10 + second_num;
    }
    cout << totalSum << endl;
    f.close();
    return 0;
}