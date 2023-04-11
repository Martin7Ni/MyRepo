#ifndef PLAYERB_H
#define PLAYERB_H

#include <IPlayer.h>


class PlayerB : public IPlayer {
    private:
        bool confused;
    public:
        PlayerB(string name);
        PlayerB();
        virtual ~PlayerB();
        bool attack(IPlayer *other);
        double beAttacked();

    protected:

};

#endif // PLAYERB_H
