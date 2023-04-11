#ifndef BOARD_H
#define BOARD_H
#include <Player.h>

class Board {
    private:
        Player* first;
        Player* second;
        int maxX;
        int maxY;
        char field[6][7];
        int lastX;
        int lastY;
        bool isOver, fieldFull;

    public:
        Board();
        virtual ~Board();
        void setPlayers();
        void choosePlayer(Player** p, char c);
        void printField();
        void round();
        void fall(char c, int y);
        void checkGame();
        bool checkLine(int fromX, int fromY, int toX, int toY);
        bool getIsOver();

    protected:

};

#endif // BOARD_H
