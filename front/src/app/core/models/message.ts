import { UserPublic } from "./user-public.model";

export interface Message {
    id: number;
    user: UserPublic,
    content: string,
    date: Date,
    dateEdit: Date
} 