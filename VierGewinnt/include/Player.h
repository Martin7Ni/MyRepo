#ifndef PLAYER_H
#define PLAYER_H

#include <iostream>

class Player {
    private:
    public:
        Player();
        Player(int x, int y, char c);
        virtual ~Player();
        virtual int setCoin(char* , int , int )=0;
        char getSymbol();

    protected:
        int x;
        int y;
        char c;
};

#endif // PLAYER_H
