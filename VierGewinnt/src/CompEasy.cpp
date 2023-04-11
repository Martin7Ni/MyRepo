#include "CompEasy.h"
#include <cstdlib>
#include <ctime>

CompEasy::CompEasy() {
    //ctor
}

CompEasy::CompEasy(int x, int y, char c) : Player(x,y,c) {

}

CompEasy::~CompEasy() {
    //dtor
}

int CompEasy::setCoin(char* a, int enemyX, int enemyY){
    if(enemyX==-1)return 3;
    if(0<enemyX&&enemyX<this->x&&
       *(a+enemyX*this->y+enemyY)==
       *(a+(enemyX+1)*this->y+enemyY)&&
       *(a+enemyY)==' ')return enemyY;
    if(enemyY==0&&
       *(a+enemyX*this->y+enemyY)==
       *(a+enemyX*this->y+enemyY+1)&&
       *(a+enemyY+2)==' ')return enemyY+2;
    if(enemyY==this->y&&
       *(a+enemyX*this->y+enemyY)==
       *(a+enemyX*this->y+enemyY-1)&&
       *(a+enemyY-2)==' ')return enemyY-2;
    if(*(a+enemyX*this->y+enemyY)==
       *(a+enemyX*this->y+enemyY-1)&&
       *(a+enemyX*this->y+enemyY+1)==' ')return enemyY+1;
    if(*(a+enemyX*this->y+enemyY)==
       *(a+enemyX*this->y+enemyY+1)&&
       *(a+enemyX*this->y+enemyY-1)==' ')return enemyY-1;
    int i = -1;
    srand(time(NULL));
    while(*(a+enemyY-1)==' '||
       *(a+enemyY)==' '||
       *(a+enemyY+1)==' '){
        srand(time(NULL));
        i=enemyY-rand()%3+1;
        if(*(a+i)==' ')return i;
    }
    while(0<=i&&*(a+i)!=' '){
        srand(time(NULL));
        i=rand()%(this->y+1);
    }
    return i;
}
