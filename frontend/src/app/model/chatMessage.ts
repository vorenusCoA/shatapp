import { User } from "./user";

export interface ChatMessage {
    content: string,
    sender: User,
    receiverEmail: string
}