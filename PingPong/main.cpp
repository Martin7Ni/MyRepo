#include <iostream>
#include <stdexcept>
#include <stdlib.h>
#include <time.h>
#include "IPlayer.h"
#include "PlayerA.h"
#include "PlayerB.h"

using namespace std;

int main() {
    //Seeding rand
    srand(time(NULL));

    //Initializing players
    IPlayer* p1 = new PlayerA("Peco");
    IPlayer* p2 = new PlayerB("Smile");

    try {
        //Playing 9 Rounds
        for(int round=1; round<10; ++round) {
            cout << "+++++ ROUND " << round << " +++++\n";

            //Playing until a point is scored
            while(true) {
                if(p1->attack(p2)) { //Player one attacks player two
                    cout << p1->getName() << " scored.\n";
                    break;
                } else //If no point was scored, player two attacks player one
                    if(p2->attack(p1)) {
                        cout << p2->getName() << " scored.\n";
                        break;
                    }
            }

            //Preparing next round by resting and changing players
            p1->rest();
            p2->rest();
            IPlayer* temp = p1;
            p1 = p2;
            p2 = temp;
        }

        //Printing result
        if(*p1>p2)
            cout << p1->getName() << " won with " << p1->getPoints() << " points.\n";
        else
            cout << p2->getName() << " won with " << p2->getPoints() << " points.\n";
    } catch(const invalid_argument& ia) {
        cout << "Invalid argument: " << ia.what() << '\n';
    }

    //Cleaning up
    delete p1;
    delete p2;
    return 0;
}
