#ifndef IPLAYER_H
#define IPLAYER_H

#include <iostream>
#include <stdlib.h>
#include <stdexcept>
#include <time.h>

using namespace std;

class IPlayer {
    private:
    public:
        IPlayer(std::string name);
        IPlayer();
        virtual ~IPlayer();
        void rest();
        int getPoints();
        int getAttack();
        int getDefense();
        std::string getName();
        virtual bool attack(IPlayer *other)=0;
        virtual double beAttacked()=0;
        bool operator>(IPlayer *other);

    protected:
        int points; //Number of scored points
        int defensePoints; //A value between 0 and 100, the higher, the stronger the object's defense
        int attackPoints; //A value between 0 and 100, the higher, the stronger the object's attacks
        int attackBonus; //A bonus which starts at 0 each round and increases the attacks of the object over time
        int stamina; //A value between 0 and 100, the object is more susceptible to attacks the lower the value
        std::string name; //The object's name


};

#endif // IPLAYER_H
