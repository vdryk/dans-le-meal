import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IBusinessHours } from 'app/shared/model/business-hours.model';
import { BusinessHoursService } from './business-hours.service';
import { BusinessHoursDeleteDialogComponent } from './business-hours-delete-dialog.component';

@Component({
  selector: 'jhi-business-hours',
  templateUrl: './business-hours.component.html',
})
export class BusinessHoursComponent implements OnInit, OnDestroy {
  businessHours?: IBusinessHours[];
  eventSubscriber?: Subscription;

  constructor(
    protected businessHoursService: BusinessHoursService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.businessHoursService.query().subscribe((res: HttpResponse<IBusinessHours[]>) => (this.businessHours = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInBusinessHours();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IBusinessHours): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInBusinessHours(): void {
    this.eventSubscriber = this.eventManager.subscribe('businessHoursListModification', () => this.loadAll());
  }

  delete(businessHours: IBusinessHours): void {
    const modalRef = this.modalService.open(BusinessHoursDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.businessHours = businessHours;
  }
}
