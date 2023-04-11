#include "Field.h"
#include <iostream>
#include <cstdlib>
#include <time.h>

using namespace std;

Field::Field() {
    p = new Player();
    f = new panel[5*5];
    level=0;
    posOfPlayer = f;
    genField();
}

Field::~Field() {
    delete p;
    delete[] f;
}

void Field::genField(){
    panel *temp = this->f;
    srand(time(nullptr));
    for(;temp<(f+(5*5));temp++){
        if(temp==this->posOfPlayer)temp->t=empt;
        else temp->t=typ(rand()%3);
        temp->a=attribute(rand()%3);
        if(temp->t==danger)temp->value=rand()%(11-this->level)+7+this->level;
        else if(temp->t==bonus)temp->value=rand()%10+1;
        else temp->value=1;
    }
}

void Field::isFieldFinished(){
    panel *temp = this->f;
    for(;temp<(f+(5*5));temp++){
        if(temp->t!=empt)return ;
    }
    cout<<"Level "<<this->level+1<<" finished!"<<endl;
    this->level++;
    cout<<"Level "<<this->level+1<<":"<<endl;
    genField();
    return ;
}

void Field::printField(){
    cout<<"--------------------"<<endl;
    panel *temp = this->f;
    for(int i=1;temp<(f+(5*5));temp++){
        cout<<"[";
        if(temp==this->posOfPlayer)cout<<" X ";
        else{
            switch(temp->t){
                case danger:
                    cout<<"G";
                    break;
                case bonus:
                    cout<<"B";
                    break;
                case empt:
                    cout<<" ";
                    break;
            }
            if(temp->value<10)cout<<" ";
            cout<<temp->value;
        }
        cout<<"]";
        if(i%5==0)cout<<endl;
        i++;
    }
}

bool Field::isGameOver(){
    return p->isHPZero();
}

void Field::turn(){
    int from =this->posOfPlayer - this->f;
    int to = from + this->p->movePlayer();
    while(!(-1<=(from%5-to%5) && (from%5-to%5)<=1 &&
           0<=to && to<5*5)){
        cout<<"You cannot go there!"<<endl;
        to = from + this->p->movePlayer();
    }
    this->posOfPlayer = f + to;

    int r = this->p->roll(this->posOfPlayer->a);

    switch(this->posOfPlayer->t){
        case danger:
            cout<<"roll + Points: "<<r<<endl;
            if(this->posOfPlayer->value <= r){
                cout<<"sample success!"<<endl;
                this->p->gainWP(this->posOfPlayer->value);
                this->p->gainX(this->posOfPlayer->a);
                this->posOfPlayer->t=empt;
                this->posOfPlayer->value=1;
            }else{
                cout<<"sample failed!"<<endl;
                this->p->loseHP(this->posOfPlayer->value);
            }
            break;
        case bonus:
            if(this->posOfPlayer->value==10){
                this->p->resetHP();
            }else if(5 < this->posOfPlayer->value){
                this->p->gainHP(5);
            }else{
                this->p->gainX(this->posOfPlayer->a);
            }
            this->posOfPlayer->t=empt;
            this->posOfPlayer->value=1;
            break;
        case empt:
            break;
    }
}

void Field::printPlayer(){
    this->p->printStats();
}
