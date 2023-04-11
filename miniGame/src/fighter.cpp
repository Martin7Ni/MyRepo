#include "fighter.h"
#include <iostream>

using namespace std;

fighter::fighter() {
    this->hp=20;
    this->damage=2;
    this->spezial=0;
}

fighter::~fighter() {

}

void fighter::successHit(fighter& other){
    other.hitTaken(this->damage);
}

void fighter::failHit(){
    this->spezial=0;
}

void fighter::hitTaken(int d){
    this->hp=this->hp-d;
    if(this->hp<0)this->hp=0;
}

void fighter::printStats(string name){
    cout<<name<<endl;
    cout<<"HP: "<<this->hp<<endl<<endl;
}

int fighter::checkHP(){
    if(this->hp <= 0)return 0;
    return 1;
}
