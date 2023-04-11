#include "PlayerB.h"

PlayerB::PlayerB(string name){
    this->name = name;
    this->attackBonus=0;
    this->attackPoints=45;
    this->defensePoints=55;
    this->points=0;
    this->stamina=100;
    this->confused=false;
}

PlayerB::PlayerB() {
    //ctor
}

PlayerB::~PlayerB() {
    //dtor
}

bool PlayerB::attack(IPlayer *other){
    if(other==nullptr)throw invalid_argument("One Player is NULL");
    double tmpAttackValue;
    this->confused=!this->confused;
    if(!this->confused)this->attackBonus=(int)2*other->beAttacked();
    tmpAttackValue=this->attackBonus+this->attackPoints;
    if(tmpAttackValue>(double)other->getDefense()){
        this->points++;
        return true;
    }
    return false;
}

double PlayerB::beAttacked(){
    if(this->confused)return 1;
    this->stamina= (this->stamina/10)*9;
    if(this->stamina>30)return 0.0;
    return 1.0;
}
