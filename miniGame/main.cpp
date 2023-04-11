#include <iostream>
#include "fighter.h"
#include "fighterD.h"
#include "fighterH.h"
#include "stdlib.h"


using namespace std;

int main() {
    char c=' ';
    fighter* player;
    while(c!='x'&&c!='X'){
        while(c!='D'&&c!='d'&&c!='S'&&c!='s'&&c!='x'&&c!='X'){
            cout << "Choose a fighter!" << endl<<"'D' for more damage or"<<endl<<"'S' for sustainer or"<<endl<<"'X' for end of game"<<endl;
            cin >> c;

        }

        if(c!='x'&&c!='X'){
            if(c=='D'||c=='d'){
                player = new fighterD;
                cout<<endl<<"You chose to do more damage"<<endl;
                cout<<endl<<"for every successful hit, you do 1 more damage next hit"<<endl<<endl;
            }else{
                player = new fighterH;
                cout<<endl<<"You chose to sustain longer"<<endl;
                cout<<endl<<"for every successful hit, you heal 1 more HP next hit"<<endl<<endl;
            }
            fighter cpu;


            cout<<endl<<"--------------------------------------------"<<endl;
            player->printStats("Player");
            cpu.printStats("CPU");
            c='x';
            while(cpu.checkHP()&&player->checkHP()){
                while(c!='A'&&c!='a'&&c!='B'&&c!='b'){
                    cout<<"choose your next action!"<<endl<<"'A' for attack 'B' for block"<<endl<<endl;
                    cin>>c;
                }
                //50%chance für angriff
                //25%chance für block
                //25%chance für nichts
                switch(rand()%4){
                case 0 :case 1:
                    if(c=='A'||c=='a'){
                        cout<<"Player attacks and the CPU attacks"<<endl<<endl;
                        cout<<"both do damage!"<<endl<<endl;
                        player->successHit(cpu);
                        cpu.successHit(*player);
                    }else{
                        cout<<"Player blocks and the CPU attacks"<<endl<<endl;
                        cout<<"CPU got blocked!"<<endl<<endl;
                    }
                    break;
                case 2:
                    if(c=='A'||c=='a'){
                        cout<<"Player attacks and the CPU blocks"<<endl<<endl;
                        cout<<"Player got blocked!"<<endl<<endl;
                        player->failHit();
                    }else{
                        cout<<"Player blocks and the CPU blocks"<<endl<<endl;
                        cout<<"Nothing happened!"<<endl<<endl;
                    }
                    break;
                case 3:
                    if(c=='A'||c=='a'){
                        cout<<"Player attacks and the CPU does nothing"<<endl<<endl;
                        cout<<"Player does damage!"<<endl<<endl;
                        player->successHit(cpu);
                    }else{
                        cout<<"Player blocks and the CPU does nothing"<<endl<<endl;
                        cout<<"Nothing happened!"<<endl<<endl;
                    }
                    break;
                default:
                    break;
                }
                cout<<"--------------------------------------------"<<endl<<endl;
                player->printStats("Player");
                cpu.printStats("CPU");
                c=' ';
            }
            if(!player->checkHP()&&!cpu.checkHP())cout<<"No winner!"<<endl<<"--------------------------------------------"<<endl;
            if(player->checkHP()&&!cpu.checkHP())cout<<"Player is the winner!"<<endl<<"--------------------------------------------"<<endl;
            if(!player->checkHP()&&cpu.checkHP())cout<<"CPU is the winner!!"<<endl<<"--------------------------------------------"<<endl;
            delete player;
        }
    }
    return 0;
}
