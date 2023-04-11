#include "IPlayer.h"
#include <cstdlib>

IPlayer::IPlayer(std::string name) {
    this->name = name;
}

IPlayer::IPlayer() {}

IPlayer::~IPlayer() {}

int IPlayer::getPoints() {
    return points;
}

int IPlayer::getAttack() {
    return attackPoints;
}

int IPlayer::getDefense() {
    return defensePoints;
}

std::string IPlayer::getName() {
    return name;
}

void IPlayer::rest() {
    //Resets the stamina and attackBonus values after each round
    stamina = 100;
    attackBonus = 0;
}

bool IPlayer::operator>(IPlayer *other){
    if(other==nullptr)throw invalid_argument("One Player is NULL");
    if(this->points>other->points)return true;
    return false;
}
