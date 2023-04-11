#include "PlayerA.h"

PlayerA::PlayerA(string name) {
    this->name = name;
    this->attackBonus=0;
    this->attackPoints=50;
    this->defensePoints=50;
    this->points=0;
    this->stamina=100;
}

PlayerA::PlayerA() {
    //ctor
}

PlayerA::~PlayerA() {
    //dtor
}

bool PlayerA::attack(IPlayer *other){
    if(other==nullptr)throw invalid_argument("One Player is NULL");
    double tmpAttackValue=0.0;
    srand(time(NULL));
    this->attackBonus+=rand()%5;
    tmpAttackValue= (this->attackBonus+this->attackPoints)* other->beAttacked();
    if(tmpAttackValue>(double)other->getDefense()){
        this->points++;
        return true;
    }
    return false;
}

double PlayerA::beAttacked(){
    this->stamina-=3;
    if(this->stamina<0){
        this->stamina=0;
        return 0;
    }
    if(this->stamina%7==0)return 5.0;
    if(this->stamina%5==0)return 3.0;
    if(this->stamina%2==0)return 1.0;
    return 0.0;
}
