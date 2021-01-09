import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IBusinessHours } from 'app/shared/model/business-hours.model';

type EntityResponseType = HttpResponse<IBusinessHours>;
type EntityArrayResponseType = HttpResponse<IBusinessHours[]>;

@Injectable({ providedIn: 'root' })
export class BusinessHoursService {
  public resourceUrl = SERVER_API_URL + 'api/business-hours';

  constructor(protected http: HttpClient) {}

  create(businessHours: IBusinessHours): Observable<EntityResponseType> {
    return this.http.post<IBusinessHours>(this.resourceUrl, businessHours, { observe: 'response' });
  }

  update(businessHours: IBusinessHours): Observable<EntityResponseType> {
    return this.http.put<IBusinessHours>(this.resourceUrl, businessHours, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IBusinessHours>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IBusinessHours[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
