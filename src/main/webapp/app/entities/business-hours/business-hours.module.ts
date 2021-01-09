import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DansLeMealSharedModule } from 'app/shared/shared.module';
import { BusinessHoursComponent } from './business-hours.component';
import { BusinessHoursDetailComponent } from './business-hours-detail.component';
import { BusinessHoursUpdateComponent } from './business-hours-update.component';
import { BusinessHoursDeleteDialogComponent } from './business-hours-delete-dialog.component';
import { businessHoursRoute } from './business-hours.route';

@NgModule({
  imports: [DansLeMealSharedModule, RouterModule.forChild(businessHoursRoute)],
  declarations: [BusinessHoursComponent, BusinessHoursDetailComponent, BusinessHoursUpdateComponent, BusinessHoursDeleteDialogComponent],
  entryComponents: [BusinessHoursDeleteDialogComponent],
})
export class DansLeMealBusinessHoursModule {}
