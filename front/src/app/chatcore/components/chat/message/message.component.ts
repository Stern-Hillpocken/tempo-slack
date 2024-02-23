import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { MessagesService } from 'src/app/chatcore/services/message.service';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { Message } from 'src/app/core/models/message';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {
  messagesList: Message[] =[];
  private messageSubscription : Subscription | null = null;
  idServer!: number;
  idRoom!: number;
 
  selected: any;


  constructor(private messageService : MessagesService,private serverService: ServerService, private activatedRoute: ActivatedRoute){

  }
  ngOnInit() {

    this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    //this.messageSubscription = 
    this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe(room => {
      this.messagesList = room.messageList;
    
      //  {  next: (messages: Message[]) =>{
      //   this.clientsStoreService.clients = clients;// valeur donnée behaviorsubject cf set clients
        
      //   }
      // }) 
      // this.clientsStoreService.clients$.subscribe(clients =>this.clientsList = clients)
    
    })

    //  ngOnDestroy(): void {
    //   //throw new Error('Method not implemented.');
    //   console.log("je détruis le composant listClient")
    //   if(this.clientSubscription)
    //   this.clientSubscription.unsubscribe();
    // }
   
        
   
    }

}
