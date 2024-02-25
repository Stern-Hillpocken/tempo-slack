import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { MessageStoreService } from 'src/app/chatcore/services/message-store.service';
import { MessagesService } from 'src/app/chatcore/services/message.service';
import { ServerService } from 'src/app/chatcore/services/server.service';
import { Message } from 'src/app/core/models/message';
import { PseudoPassword } from 'src/app/core/models/pseudo-password.model';
import { Room } from 'src/app/core/models/room.model';
import { LocalStorageService } from 'src/app/shared/local-storage.service';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {
   
  idServer!: number;
  idRoom!: number;
  idMessage!: number;
  user!:PseudoPassword;
  formUpdateMessage!: FormGroup;
  popUp: boolean =false;
  popUpDelete: boolean = false;

 
  selected: any;

  @Input()
  message!: Message;
  room!: Room;

  constructor(private messageService : MessagesService,private serverService: ServerService, private activatedRoute: ActivatedRoute, private messageStoreService : MessageStoreService, private localStorageService : LocalStorageService, private fb: FormBuilder){

    
  }
  ngOnInit() {

    // this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    //  this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    //  this.messageSubscription = 
    //  this.serverService.getRoomInServerById(this.idServer, this.idRoom).subscribe(room => {
    //   this.messagesList = room.messageList;
    
      //    next: (messages: Message[]) =>{
      //    this.messageStoreService.messages = messages;// valeur donnée behaviorsubject cf set clients
     
         
      //  } 
      //  this.messageStoreService.messages$.subscribe((messages) =>this.messagesList = messages)
      
       
    
  //   })

     this.formUpdateMessage = this.fb.group({
       content: [''] // Initialiser avec une chaîne vide
     });
    
  
    };

  //   //  ngOnDestroy(): void {
  //   //   //throw new Error('Method not implemented.');
  //   //   console.log("je détruis le composant listClient")
  //   //   if(this.clientSubscription)
  //   //   this.clientSubscription.unsubscribe();
  //   // }
     openPopup(){
       this.popUp = true;
      
     // Initialiser le formulaire avec le contenu du message
     this.formUpdateMessage = this.fb.group({
       content: [this.message.content], 
    });
    

  }
  openPopupDelete(){
    this.popUpDelete = true;
  }
  closePopupDelete(){
    this.popUpDelete = false;
  }
      closePopup(){
      this.popUp = false;
   }
      
    deleteMessage(){
      this.popUp = false;
    this.idServer = Number(this.activatedRoute.snapshot.paramMap.get("idServer"));
    this.idRoom = Number(this.activatedRoute.snapshot.paramMap.get("idRoom"));
    this.idMessage= Number(this.activatedRoute.snapshot.paramMap.get("idMessage"));
    this.user = this.localStorageService.getPseudoPassword();
    console.log(this.user)
    this.serverService.deleteMessageInRoomInServerById(this.idServer, this.idRoom, this.idMessage, this.user).subscribe(v => console.log(v)
   ) }

    updateMessage(){
      this.user = this.localStorageService.getPseudoPassword();
      this.message = {... this.formUpdateMessage.value, user : this.user}
      console.log(this.message)
      this.idMessage = Number(this.message.id)
      this.serverService.updateMessage( this.idMessage, this.message,).subscribe(v =>{
         console.log("hello");
         this.closePopup();
      }
      
  //       // this.clientsStoreService.addClient(newClient)

   )}
      }
   
      


    

