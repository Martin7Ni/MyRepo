#include "User.h"

User::User() {
    //ctor
}

User::~User() {
    //dtor
}

int User::getInput(int highestInput){
    int x = 0;
    cin>>x;
    if(cin.fail()){
        cin.clear();
        cin.ignore();
        throw invalid_argument("Input failed");
    }
    if(x<1&&highestInput<x)throw invalid_argument("Wrong input");
    return x-1;
}

int User::chooseShips(){
    return getInput(3);
}

int User::ownShip(int remainingShips){
    return getInput(remainingShips);
}

int User::opponentShip(int remainingShips){
    return getInput(remainingShips);
}
