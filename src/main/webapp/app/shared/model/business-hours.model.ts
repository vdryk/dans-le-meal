import { IRestaurant } from 'app/shared/model/restaurant.model';

export interface IBusinessHours {
  id?: number;
  day?: number;
  openingTime?: string;
  closingTime?: string;
  restaurant?: IRestaurant;
}

export class BusinessHours implements IBusinessHours {
  constructor(
    public id?: number,
    public day?: number,
    public openingTime?: string,
    public closingTime?: string,
    public restaurant?: IRestaurant
  ) {}
}
