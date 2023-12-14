#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int gcd(int a, int b) {
    if (b == 0) {
        return a;
    } else {
        return gcd(b, a % b);
    }
}

// Function to calculate the least common multiple (LCM) of two numbers
int lcm(int a, int b) {
    return (a * b) / gcd(a, b);
}

// Function to calculate the least common multiple (LCM) of an array of integers
int lcmArray(int arr[], int n) {
    int result = arr[0];
    for (int i = 1; i < n; i++) {
        result = lcm(result, arr[i]);
    }
    return result;
}

#define BUF_SIZE 65536
#define MAX_LENGTH 300
#define BEGIN_POINT 6
struct map
{
    char Name[4];

    struct map *Right;
    char RightName[4];
    struct map *Left;
    char LeftName[4];

    struct map *next;
};

int goNext(struct map *m, char dir[], int i)
{
    int index = i;
    while(index > strlen(dir))
    {
        index -= strlen(dir);
    }
    if (m->Name[2] == 'Z')
    {
        return i;
    }
    if (dir[index]=='R') 
    {   
        printf("Going from %s to %s\n", m->Name, m->Right->Name);
        return goNext(m->Right, dir, i + 1);
    } 
    else 
    {
        printf("Going from %s to %s\n", m->Name, m->Left->Name);
        return goNext(m->Left, dir, i + 1);
    }
}


struct map* generateMaps(FILE* content)
{
    char line[MAX_LENGTH];

    struct map* firstElement = NULL;
    struct map* lastElement = NULL;

    while(fgets(line, MAX_LENGTH, content) != NULL)
    {  
        struct map* m = (struct map*)malloc(sizeof(struct map));
        
        m->Name[0] = line[0];
        m->Name[1] = line[1];
        m->Name[2] = line[2];
        
        m->LeftName[0] = line[7];
        m->LeftName[1] = line[8];
        m->LeftName[2] = line[9];
        
        m->RightName[0] = line[12];
        m->RightName[1] = line[13];
        m->RightName[2] = line[14];


        

        struct map* ptr = firstElement;
        if (firstElement == NULL)
        {
            firstElement = m;
            lastElement = m;
        }
        else
        {
            lastElement->next = m;
            lastElement = m;
        }
        
    }
    lastElement->next = NULL;
    return firstElement;
}

void assign(struct map* element, struct map* AAA)
{
    struct map* next = element;

    while (next != NULL)
    {
        if (strcmp(element->LeftName, next->Name) == 0) 
        {
            element->Left = next; 
        }
        if (strcmp(element->RightName, next->Name) == 0) 
        {
            element->Right = next; 
        }
        if (strcmp(element->Name, next->LeftName) == 0)
        {
            next->Left = element;
        }
        if (strcmp(element->Name, next->RightName) == 0)
        {
            next->Right = element;
        }
        next = next->next;
    }
    struct map* aaa = AAA;
    if (strcmp(element->Name, "AAA") == 0)
    {
        aaa = element;
    }
    if (element->next == NULL) {return;} //this is the last element; 
    else {assign(element->next, aaa);} 
}

struct map* getAAA(struct map* m)
{
    if (strcmp(m->Name, "AAA") == 0)
    {
        return m;
    }
    else
    {
        return getAAA(m->next);
    }
}

void printTree(struct map* element)
{
    printf("%s = (%s, %s)\n", element->Name, element->Left->Name, element->Right->Name);
    if (element-> next == NULL) {return;}
    printTree(element->next);
}

int main(int argc, char* argv[])
{
    if (argc < 2){printf("Too few arguments\n"); return 1;}
    FILE* content;
    content = fopen(argv[1], "r");
    if (content == NULL){printf("File not opened\n"); return 1;}

    char dir[MAX_LENGTH];
    fgets(dir, MAX_LENGTH, content);
    dir[strlen(dir) - 1] = '\0';
    char t[2];
    fgets(t, 2, content);
    
    struct map* firstElement = generateMaps(content);
    fclose(content);
    assign(firstElement, NULL);

    struct map* begin = getAAA(firstElement);
    printf("The AAA left is %s and right is %s\n", begin->Left->Name, begin->Right->Name);

    int g = goNext(begin, dir, 0);

    printf("total moves : %i\n", g);

    return 0;
}
