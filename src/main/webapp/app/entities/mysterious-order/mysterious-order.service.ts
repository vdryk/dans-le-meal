import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IMysteriousOrder } from 'app/shared/model/mysterious-order.model';

type EntityResponseType = HttpResponse<IMysteriousOrder>;
type EntityArrayResponseType = HttpResponse<IMysteriousOrder[]>;

@Injectable({ providedIn: 'root' })
export class MysteriousOrderService {
  public resourceUrl = SERVER_API_URL + 'api/mysterious-orders';

  constructor(protected http: HttpClient) {}

  create(mysteriousOrder: IMysteriousOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mysteriousOrder);
    return this.http
      .post<IMysteriousOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(mysteriousOrder: IMysteriousOrder): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(mysteriousOrder);
    return this.http
      .put<IMysteriousOrder>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IMysteriousOrder>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IMysteriousOrder[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(mysteriousOrder: IMysteriousOrder): IMysteriousOrder {
    const copy: IMysteriousOrder = Object.assign({}, mysteriousOrder, {
      date: mysteriousOrder.date && mysteriousOrder.date.isValid() ? mysteriousOrder.date.toJSON() : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date = res.body.date ? moment(res.body.date) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((mysteriousOrder: IMysteriousOrder) => {
        mysteriousOrder.date = mysteriousOrder.date ? moment(mysteriousOrder.date) : undefined;
      });
    }
    return res;
  }
}
