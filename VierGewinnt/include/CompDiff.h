#ifndef COMPDIFF_H
#define COMPDIFF_H

#include <Player.h>


class CompDiff : public Player {
    private:
    public:
        CompDiff();
        CompDiff(int x, int y, char c);
        virtual ~CompDiff();
        int setCoin(char* , int , int );

    protected:

};

#endif // COMPDIFF_H
