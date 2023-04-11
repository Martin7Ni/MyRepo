#include "Player.h"
#include <iostream>
#include <time.h>
#include <stdexcept>
using namespace std;

Player::Player() {
    this->hp=100;
    this->wp=0;
    this->strengthP=5;
    this->agilityP=5;
    this->intellectP=5;
    this->strengthX=0;
    this->agilityX=0;
    this->intellectX=0;
}

Player::~Player() {
    //dtor
}

void Player::printStats(){
    cout<<"--------------------"<<endl;
    cout<<"Healthpoints: ";
    if(this->hp<10)cout<<" ";
    cout<<this->hp<<"\tWinningpoints: "<<this->wp<<endl;
    cout<<"Strength: "<<this->strengthP<<"\t\tEXP Strength: "<<this->strengthX<<endl;
    cout<<"Agility: "<<this->agilityP<<"\t\tEXP Agility: "<<this->agilityX<<endl;
    cout<<"Intellect: "<<this->intellectP<<"\t\tEXP Intellect: "<<this->intellectX<<endl;
}

int Player::roll(const attribute& a){
    srand(time(NULL));
    switch(a){
        case strength:
            return rand() % 10 + 1 + this->strengthP;
        case agility:
            return rand() % 10 + 1 + this->agilityP;
        case intellect:
            return rand() % 10 + 1 + this->intellectP;
        default:
            return rand() % 10 + 1;
    }
}

direction Player::movePlayer(){
    cout<<"Which direction to go?"<<endl<<"   <w>\n<a><s><d>"<<endl;
    char c = 'x';
    while(c!='w'&&c!='a'&&c!='s'&&c!='d'){
        try{
            cin>>c;
            if(cin.fail()){
                cin.clear();
                cin.ignore();
                throw invalid_argument("Input failed");
            }
        }
        catch(const invalid_argument& except){
            cout<<except.what();
            c='x';
        }
    }
    switch(c){
        case 'w':
            return up;
        case 'a':
            return direction::left;
        case 's':
            return down;
        case 'd':
            return direction::right;
    }
}

void Player::gainX(attribute a){
    switch(a){
        case strength:
            cout<<"Gain 1 EXP in strength.";
            this->strengthX++;
            if(this->strengthX%5==0){
                this->strengthP++;
                cout<<"Strength LVL up!";
            }
            cout<<endl;
            break;
        case agility:
            cout<<"Gain 1 EXP in agility.";
            this->agilityX++;
            if(this->agilityX%5==0){
                this->agilityP++;
                cout<<"Agility LVL up!";
            }
            cout<<endl;
            break;
        case intellect:
            cout<<"Gain 1 EXP in intellect.";
            this->intellectX++;
            if(this->intellectX%5==0){
                this->intellectP++;
                cout<<"Intellect LVL up!";
            }
            cout<<endl;
            break;
    }
}

bool Player::isHPZero(){
    if(this->hp>0)return false;
    cout<<"GAME OVER"<<endl;
    this->printStats();
    return true;
}

void Player::gainWP(int i){
    this->wp+=i;
}

void Player::gainHP(int i){
    this->hp+=i;
    if(this->hp>100)this->hp=100;
}

void Player::loseHP(int i){
    this->hp-=i;
}

void Player::resetHP(){
    this->hp=100;
}
