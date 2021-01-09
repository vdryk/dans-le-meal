import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBusinessHours } from 'app/shared/model/business-hours.model';
import { BusinessHoursService } from './business-hours.service';

@Component({
  templateUrl: './business-hours-delete-dialog.component.html',
})
export class BusinessHoursDeleteDialogComponent {
  businessHours?: IBusinessHours;

  constructor(
    protected businessHoursService: BusinessHoursService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.businessHoursService.delete(id).subscribe(() => {
      this.eventManager.broadcast('businessHoursListModification');
      this.activeModal.close();
    });
  }
}
