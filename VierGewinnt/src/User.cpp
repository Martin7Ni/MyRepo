#include "User.h"

using namespace std;

User::User() {
    //ctor
}

User::User(int x, int y, char c) : Player(x,y,c) {

}

User::~User() {
    //dtor
}

int User::setCoin(char* a, int enemyX, int enemyY){
    int i = -1;
    cout<<"---------------your move---------------"<<endl;
    while((i<1)||(7<i)||(*(a+i-1)!=' ')){
        cin>>i;
    }
    return --i;
}
