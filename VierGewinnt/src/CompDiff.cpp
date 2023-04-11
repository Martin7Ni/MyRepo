#include "CompDiff.h"
#include <cstdlib>
#include <ctime>

CompDiff::CompDiff() {
    //ctor
}

CompDiff::CompDiff(int x, int y, char c) : Player(x,y,c) {

}

CompDiff::~CompDiff() {
    //dtor
}

int CompDiff::setCoin(char* a, int enemyX, int enemyY){
    //falls der computer das erste symbol setzt
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
    if(0<enemyX&&enemyX<this->x&&
       0<enemyY&&enemyY<this->y){
        if(*(a+enemyX*this->y+enemyY)==
           *(a+(enemyX+1)*this->y+enemyY-1)&&
           *(a+enemyX*this->y+enemyY+1)!=' '&&
           *(a+(enemyX-1)*this->y+enemyY+1)==' ')return enemyY+1;
        if(*(a+enemyX*this->y+enemyY)==
           *(a+(enemyX+1)*this->y+enemyY+1)&&
           *(a+enemyX*this->y+enemyY-1)!=' '&&
           *(a+(enemyX-1)*this->y+enemyY-1)==' ')return enemyY-1;
        if(*(a+enemyX*this->y+enemyY)==
           *(a+(enemyX-1)*this->y+enemyY-1)&&
           *(a+(enemyX+1)*this->y+enemyY+1)==' ')return enemyY+1;
        if(*(a+enemyX*this->y+enemyY)==
           *(a+(enemyX-1)*this->y+enemyY+1)&&
           *(a+(enemyX+1)*this->y+enemyY-1)==' ')return enemyY-1;
       }


    int i = -1;
    srand(time(NULL));
    while(*(a+enemyY-1)==' '||
       *(a+enemyY)==' '||
       *(a+enemyY+1)==' '){
        i=enemyY-rand()%3+1;
        if(*(a+i)==' ')return i;
    }
    i=rand()%(this->y+1);
    while(*(a+i)!=' '){
        i=rand()%(this->y+1);
    }
    return i;
}
