#include "Board.h"
#include <iostream>
#include <algorithm>
#include <User.h>
#include <CompEasy.h>
#include <CompDiff.h>

using namespace std;

Board::Board() {
    this->maxX=5;
    this->maxY=6;
    this->lastX=-1;
    this->lastY=-1;
    fill(&field[0][0], &field[5][6]+1, ' ');
    isOver=false;
    fieldFull=false;
}

Board::~Board() {
    delete first;
    delete second;
}

bool Board::getIsOver(){
    return this->isOver;
}

void Board::choosePlayer(Player** p, char c){
    char input = ' ';
    while(input!='u'&&input!='e'&&input!='d'){
        cout<<"'u':for user"<<endl;
        cout<<"'e':for easy computer"<<endl;
        cout<<"'d':for difficult computer"<<endl;
        cin>>input;
    }
    switch(input){
    case 'u':
        *p = new User(this->maxX, this->maxY, c);
        cout<<"You chose to make the moves"<<endl<<endl;
        break;
    case 'e':
        *p = new CompEasy(this->maxX, this->maxY, c);
        cout<<"You chose the Computer to make the moves"<<endl<<endl;
        break;
    case 'd':default:
        *p = new CompDiff(this->maxX, this->maxY, c);
        cout<<"You chose the Computer to make the moves"<<endl<<endl;
        break;
    }
}

void Board::setPlayers(){
    cout<<"first player:"<<endl;
    choosePlayer(&this->first, 'X');
    cout<<"second player:"<<endl;
    choosePlayer(&this->second, 'O');
}

void Board::printField(){
    cout<<endl;
    for_each(&field[0][0], (&field[0][6])+1, [](const char& c){std::cout<<c<<" ";});
    cout<<endl;
    for_each(&field[1][0], (&field[1][6])+1, [](const char& c){std::cout<<c<<" ";});
    cout<<endl;
    for_each(&field[2][0], (&field[2][6])+1, [](const char& c){std::cout<<c<<" ";});
    cout<<endl;
    for_each(&field[3][0], (&field[3][6])+1, [](const char& c){std::cout<<c<<" ";});
    cout<<endl;
    for_each(&field[4][0], (&field[4][6])+1, [](const char& c){std::cout<<c<<" ";});
    cout<<endl;
    for_each(&field[5][0], (&field[5][6])+1, [](const char& c){std::cout<<c<<" ";});
    cout<<endl;
    cout<<"1 2 3 4 5 6 7"<<endl;
}

//sucht das nächste freie feld in der spalte
void Board::fall(char c, int y){
    for(int i=this->maxX;0<=i;i--){
        if(this->field[i][y]==' '){
            this->field[i][y]=c;
            this->lastX=i;
            this->lastY=y;
            break;
        }
    }
}

//überprf. ob vier in einer reihe gleich sind
bool Board::checkLine(int fromX, int fromY, int toX, int toY){
    if(fromX<0||5<fromX)return false;
    if(toX<0||5<toX)return false;
    if(fromY<0||6<fromY)return false;
    if(toY<0||6<toY)return false;

    int dX=(toX-fromX)/3;
    int dY=(toY-fromY)/3;
    if(field[fromX][fromY]==field[fromX+dX][fromY+dY]&&
       field[fromX][fromY]==field[fromX+2*dX][fromY+2*dY]&&
       field[fromX][fromY]==field[fromX+3*dX][fromY+3*dY]){
        isOver=true;
        return true;
    }
    return false;
}

void Board::checkGame(){
    //es wird immer von der letzten pos wo ein symbol gesetzt wurde gesucht
    if(maxY-lastY>2){
        //sucht nach rechts
        if(checkLine(lastX, lastY, lastX, lastY+3))return;
    }
    if(lastY>2){
        //sucht nach links
        if(checkLine(lastX, lastY, lastX, lastY-3))return;
    }
    if(maxX-lastX>2){
        //sucht nach unten
        if(checkLine(lastX, lastY, lastX+3, lastY))return;
        if(maxY-lastY>2){
            //sucht diagonal nach unten rechts
            if(checkLine(lastX, lastY, lastX+3, lastY+3))return;
        }
        if(lastY>2){
            //sucht diagonal nach unten links
            if(checkLine(lastX, lastY, lastX+3, lastY-3))return;
        }
    }

    if(lastX>2){
        if(maxY-lastY>2){
            //sucht diagonal nach oben rechts
            if(checkLine(lastX, lastY, lastX-3, lastY+3))return;
        }
        if(lastY>2){
            //sucht diagonal nach oben links
            if(checkLine(lastX, lastY, lastX-3, lastY-3))return;
        }
    }

    //hier wird wie oben überprf. nur mit einer position in die gegenrichtung zusätzlich
    if(maxY-lastY<1){
        if(field[lastX][lastY]==field[lastX][lastY-1]&&
           field[lastX][lastY]==field[lastX][lastY+1]){
            if(checkLine(lastX, lastY-1, lastX, lastY+2))return;
        }
    }
    if(lastY>1){
        if(field[lastX][lastY]==field[lastX][lastY-1]&&
           field[lastX][lastY]==field[lastX][lastY+1]){
            if(checkLine(lastX, lastY-2, lastX, lastY+1))return;
        }
    }
    if(maxX-lastX>1){
        if(maxY-lastY<1){
            if(field[lastX][lastY]==field[lastX-1][lastY-1]&&
               field[lastX][lastY]==field[lastX+1][lastY+1]){
                if(checkLine(lastX-1, lastY-1, lastX+2, lastY+2))return;
            }
            if(field[lastX][lastY]==field[lastX][lastY-1]&&
               field[lastX][lastY]==field[lastX][lastY+1]){
                if(checkLine(lastX, lastY-1, lastX, lastY+2))return;
            }
        }
        if(lastY>1){
            if(field[lastX][lastY]==field[lastX+1][lastY-1]&&
               field[lastX][lastY]==field[lastX-1][lastY+1]){
                if(checkLine(lastX-1, lastY+1, lastX+2, lastY-2))return;
            }
            if(field[lastX][lastY]==field[lastX][lastY-1]&&
               field[lastX][lastY]==field[lastX][lastY+1]){
                if(checkLine(lastX, lastY-2, lastX, lastY+1))return;
            }
        }
    }
    if(lastX>1){
        if(maxY-lastY>1){
            if(field[lastX][lastY]==field[lastX-1][lastY+1]&&
               field[lastX][lastY]==field[lastX+1][lastY-1]){
                if(checkLine(lastX+1, lastY-1, lastX-2, lastY+2))return;
            }
        }
        if(lastY>1){
            if(field[lastX][lastY]==field[lastX-1][lastY-1]&&
               field[lastX][lastY]==field[lastX+1][lastY+1]){
                if(checkLine(lastX-2, lastY-2, lastX+1, lastY+1))return;
            }
        }
    }
    //find() gibt den pointer zurück von entweder der letzten stelle oder der ersten stelle wo sich ein ' ' befindet
    if(*(find(&field[0][0], (&field[0][6])+1, ' '))!=' '){
        fieldFull=true;
        isOver=true;
    }
}

//eine runde vom spiel
void Board::round(){
    int y=0;
    printField();
    y = this->first->setCoin(&this->field[0][0], this->lastX, this->lastY);
    fall(this->first->getSymbol(), y);
    checkGame();
    if(this->isOver){
        if(this->fieldFull){
            cout<<"It is a draw"<<endl;
        }else{
            cout<<"First player has Won"<<endl;
        }
        printField();
        return;
    }

    printField();
    y = this->second->setCoin(&this->field[0][0], this->lastX, this->lastY);
    fall(this->second->getSymbol(), y);
    checkGame();
    if(this->isOver){
        if(this->fieldFull){
            cout<<"It is a draw"<<endl;
        }else{
            cout<<"Second player has Won"<<endl;
        }
        printField();
    }
}
