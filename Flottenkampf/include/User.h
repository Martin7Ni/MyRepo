#ifndef USER_H
#define USER_H

#include <Player.h>


class User : public Player {
    private:
        int getInput(int highestInput);
    public:
        User();
        virtual ~User();
        int chooseShips();
        int ownShip(int remainingShips);
        int opponentShip(int remainingShips);

    protected:

};

#endif // USER_H
