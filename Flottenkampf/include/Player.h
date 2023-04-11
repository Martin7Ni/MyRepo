#ifndef PLAYER_H
#define PLAYER_H

#include <iostream>
#include <exception>

using namespace std;

struct liste{
    //
    struct liste *next;
};

class Player {
    private:
    public:
        Player();
        virtual ~Player();
        virtual int chooseShips()=0;

    protected:

};

#endif // PLAYER_H
