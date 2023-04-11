#ifndef FIGHTER_H
#define FIGHTER_H

#include <iostream>

using namespace std;

class fighter {
    private:

    public:
        fighter();
        virtual ~fighter();
        virtual void successHit(fighter&);
        void failHit();
        void hitTaken(int d);
        void printStats(string);
        int checkHP();

    protected:
        int hp;
        int damage;
        int spezial;
};

#endif // FIGHTER_H
