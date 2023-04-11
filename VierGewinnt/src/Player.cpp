#include "Player.h"

Player::Player() {
    //ctor
}

Player::Player(int x, int y, char c) {
    this->x=x;
    this->y=y;
    this->c=c;
}

Player::~Player() {
    //dtor
}

char Player::getSymbol(){
    return this->c;
}
