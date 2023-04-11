#ifndef KI_H
#define KI_H

#include <Player.h>
#include <stdlib.h>
#include <time.h>


class KI : public Player{
    private:
    public:
        KI();
        virtual ~KI();
        int chooseShips();
        int ownShip(const struct liste& ownShips);
        int opponentShip(const struct liste& opponentShips);

    protected:

};

#endif // KI_H
