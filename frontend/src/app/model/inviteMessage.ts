import { InviteStatus } from "./inviteStatus";

export interface InviteMessage {

    status: InviteStatus,
    from: string,
    fromPicture: string,
    toPicture: string,
    to: string,
    sentDatetime: Date

}