import { Moment } from 'moment';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { Status } from 'app/shared/model/enumerations/status.model';

export interface IMysteriousOrder {
  id?: number;
  status?: Status;
  date?: Moment;
  restaurant?: IRestaurant;
}

export class MysteriousOrder implements IMysteriousOrder {
  constructor(public id?: number, public status?: Status, public date?: Moment, public restaurant?: IRestaurant) {}
}
