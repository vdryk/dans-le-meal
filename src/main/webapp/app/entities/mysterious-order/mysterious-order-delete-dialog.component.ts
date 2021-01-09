import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMysteriousOrder } from 'app/shared/model/mysterious-order.model';
import { MysteriousOrderService } from './mysterious-order.service';

@Component({
  templateUrl: './mysterious-order-delete-dialog.component.html',
})
export class MysteriousOrderDeleteDialogComponent {
  mysteriousOrder?: IMysteriousOrder;

  constructor(
    protected mysteriousOrderService: MysteriousOrderService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mysteriousOrderService.delete(id).subscribe(() => {
      this.eventManager.broadcast('mysteriousOrderListModification');
      this.activeModal.close();
    });
  }
}
