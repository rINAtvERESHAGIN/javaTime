import {Type} from "class-transformer";

export class RawEvent {
    id: number;
    @Type(() => Date)
    eventDate: Date;
    comment: string;
    name: string;
    cronPeriod: string;
    userValue: number;

}