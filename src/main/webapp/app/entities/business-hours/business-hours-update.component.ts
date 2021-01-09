import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IBusinessHours, BusinessHours } from 'app/shared/model/business-hours.model';
import { BusinessHoursService } from './business-hours.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

@Component({
  selector: 'jhi-business-hours-update',
  templateUrl: './business-hours-update.component.html',
})
export class BusinessHoursUpdateComponent implements OnInit {
  isSaving = false;
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    day: [],
    openingTime: [],
    closingTime: [],
    restaurant: [],
  });

  constructor(
    protected businessHoursService: BusinessHoursService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ businessHours }) => {
      this.updateForm(businessHours);

      this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => (this.restaurants = res.body || []));
    });
  }

  updateForm(businessHours: IBusinessHours): void {
    this.editForm.patchValue({
      id: businessHours.id,
      day: businessHours.day,
      openingTime: businessHours.openingTime,
      closingTime: businessHours.closingTime,
      restaurant: businessHours.restaurant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const businessHours = this.createFromForm();
    if (businessHours.id !== undefined) {
      this.subscribeToSaveResponse(this.businessHoursService.update(businessHours));
    } else {
      this.subscribeToSaveResponse(this.businessHoursService.create(businessHours));
    }
  }

  private createFromForm(): IBusinessHours {
    return {
      ...new BusinessHours(),
      id: this.editForm.get(['id'])!.value,
      day: this.editForm.get(['day'])!.value,
      openingTime: this.editForm.get(['openingTime'])!.value,
      closingTime: this.editForm.get(['closingTime'])!.value,
      restaurant: this.editForm.get(['restaurant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBusinessHours>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IRestaurant): any {
    return item.id;
  }
}
