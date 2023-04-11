#ifndef COMPEASY_H
#define COMPEASY_H

#include <Player.h>


class CompEasy : public Player {
    private:
    public:
        CompEasy();
        CompEasy(int x, int y, char c);
        virtual ~CompEasy();
        int setCoin(char* , int , int );

    protected:

};

#endif // COMPEASY_H
