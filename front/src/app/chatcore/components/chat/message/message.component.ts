import { Component } from '@angular/core';
import { Subscription } from 'rxjs';
import { MessagesService } from 'src/app/chatcore/services/message.service';
import { Message } from 'src/app/core/models/message';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {
  messagesList: Message[] =[{"user" : "toto", "content" : "hello"},{"user" : "tata", "content" : "salut"}];
  private messageSubscription : Subscription | null = null;
 


  constructor(private messageService : MessagesService){

  }
  ngOnInit() {
    
    this.messageSubscription = this.messageService.getAllMessages().subscribe(v=>console.log(v))
     
      //  {  next: (messages: Message[]) =>{
      //   this.clientsStoreService.clients = clients;// valeur donnée behaviorsubject cf set clients
        
      //   }
      // }) 
      // this.clientsStoreService.clients$.subscribe(clients =>this.clientsList = clients)
    
     };

    //  ngOnDestroy(): void {
    //   //throw new Error('Method not implemented.');
    //   console.log("je détruis le composant listClient")
    //   if(this.clientSubscription)
    //   this.clientSubscription.unsubscribe();
    // }
   


}
