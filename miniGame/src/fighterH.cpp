#include "fighterH.h"

fighterH::fighterH() {
    //ctor
}

fighterH::~fighterH() {
    //dtor
}

void fighterH::successHit(fighter& other){
    fighter::successHit(other);
    this->hp=this->hp+this->spezial;
    this->spezial++;
}
