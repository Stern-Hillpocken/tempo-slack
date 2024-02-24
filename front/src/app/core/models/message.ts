import { PseudoPassword } from "./pseudo-password.model";

export interface Message {
    id?:number;
    user : PseudoPassword,
    content:string,
    date? : Date,
    dateEdit? : Date
} 