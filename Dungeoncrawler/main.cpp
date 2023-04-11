#include <iostream>
#include "Player.h"
#include "Field.h"

using namespace std;


int main() {
    Field f;
    while(!f.isGameOver()){
        f.printPlayer();
        f.printField();
        f.turn();
        f.isFieldFinished();
        cout<<endl<<endl<<endl<<endl<<endl<<endl<<endl<<endl;
    }
    return 0;
}
