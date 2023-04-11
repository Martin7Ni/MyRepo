#include "KI.h"

KI::KI() {
    //ctor
}

KI::~KI() {
    //dtor
}

int KI::chooseShips(){
    return rand()%3;
}

int KI::ownShip(const struct liste& ownShips){

}

int KI::opponentShip(const struct liste& ownShips){

}
