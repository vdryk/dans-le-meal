import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IMysteriousOrder, MysteriousOrder } from 'app/shared/model/mysterious-order.model';
import { MysteriousOrderService } from './mysterious-order.service';
import { IRestaurant } from 'app/shared/model/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/restaurant.service';

@Component({
  selector: 'jhi-mysterious-order-update',
  templateUrl: './mysterious-order-update.component.html',
})
export class MysteriousOrderUpdateComponent implements OnInit {
  isSaving = false;
  restaurants: IRestaurant[] = [];

  editForm = this.fb.group({
    id: [],
    status: [],
    date: [],
    restaurant: [],
  });

  constructor(
    protected mysteriousOrderService: MysteriousOrderService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mysteriousOrder }) => {
      if (!mysteriousOrder.id) {
        const today = moment().startOf('day');
        mysteriousOrder.date = today;
      }

      this.updateForm(mysteriousOrder);

      this.restaurantService.query().subscribe((res: HttpResponse<IRestaurant[]>) => (this.restaurants = res.body || []));
    });
  }

  updateForm(mysteriousOrder: IMysteriousOrder): void {
    this.editForm.patchValue({
      id: mysteriousOrder.id,
      status: mysteriousOrder.status,
      date: mysteriousOrder.date ? mysteriousOrder.date.format(DATE_TIME_FORMAT) : null,
      restaurant: mysteriousOrder.restaurant,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mysteriousOrder = this.createFromForm();
    if (mysteriousOrder.id !== undefined) {
      this.subscribeToSaveResponse(this.mysteriousOrderService.update(mysteriousOrder));
    } else {
      this.subscribeToSaveResponse(this.mysteriousOrderService.create(mysteriousOrder));
    }
  }

  private createFromForm(): IMysteriousOrder {
    return {
      ...new MysteriousOrder(),
      id: this.editForm.get(['id'])!.value,
      status: this.editForm.get(['status'])!.value,
      date: this.editForm.get(['date'])!.value ? moment(this.editForm.get(['date'])!.value, DATE_TIME_FORMAT) : undefined,
      restaurant: this.editForm.get(['restaurant'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMysteriousOrder>>): void {
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
