#include <iostream>
#include <algorithm>
#include <Board.h>

using namespace std;


int main() {
    char c=' ';
    bool play = true;
    while(play){
        Board* b = new Board();
        b->setPlayers();
        while(!b->getIsOver())b->round();
        delete b;
        cout<<"Play again? Y/N"<<endl;
        while(c!='Y'&&c!='N'){
            cin>>c;
        }
        if(c=='N')play=false;
        c=' ';
    }
    return 0;
}
