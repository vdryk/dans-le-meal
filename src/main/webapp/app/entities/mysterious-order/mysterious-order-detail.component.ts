import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMysteriousOrder } from 'app/shared/model/mysterious-order.model';

@Component({
  selector: 'jhi-mysterious-order-detail',
  templateUrl: './mysterious-order-detail.component.html',
})
export class MysteriousOrderDetailComponent implements OnInit {
  mysteriousOrder: IMysteriousOrder | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mysteriousOrder }) => (this.mysteriousOrder = mysteriousOrder));
  }

  previousState(): void {
    window.history.back();
  }
}
