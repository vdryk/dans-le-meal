import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IMysteriousOrder, MysteriousOrder } from 'app/shared/model/mysterious-order.model';
import { MysteriousOrderService } from './mysterious-order.service';
import { MysteriousOrderComponent } from './mysterious-order.component';
import { MysteriousOrderDetailComponent } from './mysterious-order-detail.component';
import { MysteriousOrderUpdateComponent } from './mysterious-order-update.component';

@Injectable({ providedIn: 'root' })
export class MysteriousOrderResolve implements Resolve<IMysteriousOrder> {
  constructor(private service: MysteriousOrderService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IMysteriousOrder> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((mysteriousOrder: HttpResponse<MysteriousOrder>) => {
          if (mysteriousOrder.body) {
            return of(mysteriousOrder.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new MysteriousOrder());
  }
}

export const mysteriousOrderRoute: Routes = [
  {
    path: '',
    component: MysteriousOrderComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MysteriousOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: MysteriousOrderDetailComponent,
    resolve: {
      mysteriousOrder: MysteriousOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MysteriousOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: MysteriousOrderUpdateComponent,
    resolve: {
      mysteriousOrder: MysteriousOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MysteriousOrders',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: MysteriousOrderUpdateComponent,
    resolve: {
      mysteriousOrder: MysteriousOrderResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'MysteriousOrders',
    },
    canActivate: [UserRouteAccessService],
  },
];
