#include "fighterD.h"

fighterD::fighterD() {

}

fighterD::~fighterD() {
    //dtor
}

void fighterD::successHit(fighter& other){
    this->damage=this->damage+this->spezial;
    fighter::successHit(other);
    this->damage=this->damage-this->spezial;
    this->spezial++;
}
