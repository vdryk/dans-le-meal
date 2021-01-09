import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMysteriousOrder } from 'app/shared/model/mysterious-order.model';
import { MysteriousOrderService } from './mysterious-order.service';
import { MysteriousOrderDeleteDialogComponent } from './mysterious-order-delete-dialog.component';

@Component({
  selector: 'jhi-mysterious-order',
  templateUrl: './mysterious-order.component.html',
})
export class MysteriousOrderComponent implements OnInit, OnDestroy {
  mysteriousOrders?: IMysteriousOrder[];
  eventSubscriber?: Subscription;

  constructor(
    protected mysteriousOrderService: MysteriousOrderService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.mysteriousOrderService.query().subscribe((res: HttpResponse<IMysteriousOrder[]>) => (this.mysteriousOrders = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMysteriousOrders();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMysteriousOrder): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMysteriousOrders(): void {
    this.eventSubscriber = this.eventManager.subscribe('mysteriousOrderListModification', () => this.loadAll());
  }

  delete(mysteriousOrder: IMysteriousOrder): void {
    const modalRef = this.modalService.open(MysteriousOrderDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mysteriousOrder = mysteriousOrder;
  }
}
