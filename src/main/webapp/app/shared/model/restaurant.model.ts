import { IBusinessHours } from 'app/shared/model/business-hours.model';
import { IMysteriousOrder } from 'app/shared/model/mysterious-order.model';
import { IUser } from 'app/core/user/user.model';

export interface IRestaurant {
  id?: number;
  name?: string;
  latitude?: number;
  longitude?: number;
  delivery?: boolean;
  opened?: boolean;
  price?: number;
  businessHours?: IBusinessHours[];
  mysteriousOrders?: IMysteriousOrder[];
  user?: IUser;
}

export class Restaurant implements IRestaurant {
  constructor(
    public id?: number,
    public name?: string,
    public latitude?: number,
    public longitude?: number,
    public delivery?: boolean,
    public opened?: boolean,
    public price?: number,
    public businessHours?: IBusinessHours[],
    public mysteriousOrders?: IMysteriousOrder[],
    public user?: IUser
  ) {
    this.delivery = this.delivery || false;
    this.opened = this.opened || false;
  }
}
