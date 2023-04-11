#ifndef FIGHTERD_H
#define FIGHTERD_H

#include <fighter.h>


class fighterD : public fighter {
    private:

    public:
        fighterD();
        virtual ~fighterD();
        void successHit(fighter&);

    protected:

};

#endif // FIGHTERD_H
