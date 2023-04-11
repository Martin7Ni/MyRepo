#ifndef PLAYER_H
#define PLAYER_H

enum direction{up=-5, down=5, left=-1, right=1};
enum attribute{strength, agility, intellect};

class Player {
    private:
        int hp, strengthP, agilityP, intellectP,
            wp, strengthX, agilityX, intellectX;
    public:
        Player();
        virtual ~Player();
        void printStats();
        int roll(const attribute& a);
        direction movePlayer();
        void gainX(attribute a);
        void gainWP(int i);
        void gainHP(int i);
        void loseHP(int i);
        void resetHP();
        bool isHPZero();

    protected:

};

#endif // PLAYER_H
