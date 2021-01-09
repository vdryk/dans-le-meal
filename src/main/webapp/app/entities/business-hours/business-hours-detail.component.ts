import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBusinessHours } from 'app/shared/model/business-hours.model';

@Component({
  selector: 'jhi-business-hours-detail',
  templateUrl: './business-hours-detail.component.html',
})
export class BusinessHoursDetailComponent implements OnInit {
  businessHours: IBusinessHours | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessHours }) => (this.businessHours = businessHours));
  }

  previousState(): void {
    window.history.back();
  }
}
