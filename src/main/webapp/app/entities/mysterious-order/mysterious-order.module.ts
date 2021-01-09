import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DansLeMealSharedModule } from 'app/shared/shared.module';
import { MysteriousOrderComponent } from './mysterious-order.component';
import { MysteriousOrderDetailComponent } from './mysterious-order-detail.component';
import { MysteriousOrderUpdateComponent } from './mysterious-order-update.component';
import { MysteriousOrderDeleteDialogComponent } from './mysterious-order-delete-dialog.component';
import { mysteriousOrderRoute } from './mysterious-order.route';

@NgModule({
  imports: [DansLeMealSharedModule, RouterModule.forChild(mysteriousOrderRoute)],
  declarations: [
    MysteriousOrderComponent,
    MysteriousOrderDetailComponent,
    MysteriousOrderUpdateComponent,
    MysteriousOrderDeleteDialogComponent,
  ],
  entryComponents: [MysteriousOrderDeleteDialogComponent],
})
export class DansLeMealMysteriousOrderModule {}
