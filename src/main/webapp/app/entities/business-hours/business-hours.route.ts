import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IBusinessHours, BusinessHours } from 'app/shared/model/business-hours.model';
import { BusinessHoursService } from './business-hours.service';
import { BusinessHoursComponent } from './business-hours.component';
import { BusinessHoursDetailComponent } from './business-hours-detail.component';
import { BusinessHoursUpdateComponent } from './business-hours-update.component';

@Injectable({ providedIn: 'root' })
export class BusinessHoursResolve implements Resolve<IBusinessHours> {
  constructor(private service: BusinessHoursService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IBusinessHours> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((businessHours: HttpResponse<BusinessHours>) => {
          if (businessHours.body) {
            return of(businessHours.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new BusinessHours());
  }
}

export const businessHoursRoute: Routes = [
  {
    path: '',
    component: BusinessHoursComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessHours',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: BusinessHoursDetailComponent,
    resolve: {
      businessHours: BusinessHoursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessHours',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: BusinessHoursUpdateComponent,
    resolve: {
      businessHours: BusinessHoursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessHours',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: BusinessHoursUpdateComponent,
    resolve: {
      businessHours: BusinessHoursResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'BusinessHours',
    },
    canActivate: [UserRouteAccessService],
  },
];
