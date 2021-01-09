import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'business-hours',
        loadChildren: () => import('./business-hours/business-hours.module').then(m => m.DansLeMealBusinessHoursModule),
      },
      {
        path: 'restaurant',
        loadChildren: () => import('./restaurant/restaurant.module').then(m => m.DansLeMealRestaurantModule),
      },
      {
        path: 'mysterious-order',
        loadChildren: () => import('./mysterious-order/mysterious-order.module').then(m => m.DansLeMealMysteriousOrderModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class DansLeMealEntityModule {}
