#ifndef FIELD_H
#define FIELD_H
#include <Player.h>

enum typ{danger, bonus, empt};

struct panel{
    typ t;
    attribute a;
    int value;
};

class Field {
    private:
        Player *p;
        panel *f;
        int level;
        panel *posOfPlayer;
    public:
        Field();
        virtual ~Field();
        void genField();
        void isFieldFinished();
        void printField();
        bool isGameOver();
        void turn();
        void printPlayer();

    protected:

};

#endif // FIELD_H
