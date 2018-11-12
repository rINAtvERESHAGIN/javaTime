import {Type} from "class-transformer";


export class EventView {
    id: number;

    resourceId: number;
    resourceName: string;

    contributionId: number;
    contributionName: string;

    name: string;
    @Type(() => Date)
    eventDate: Date;
    comment: string;


}