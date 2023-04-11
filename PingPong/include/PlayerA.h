#ifndef PLAYERA_H
#define PLAYERA_H

#include <IPlayer.h>


class PlayerA : public IPlayer {
    private:
    public:
        PlayerA(string name);
        PlayerA();
        virtual ~PlayerA();
        bool attack(IPlayer *other);
        double beAttacked();

    protected:

};

#endif // PLAYERA_H
