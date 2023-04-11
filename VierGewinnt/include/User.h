#ifndef USER_H
#define USER_H

#include <Player.h>


class User : public Player {
    private:
    public:
        User();
        User(int x, int y, char c);
        virtual ~User();
        int setCoin(char* , int , int );

    protected:

};

#endif // USER_H
