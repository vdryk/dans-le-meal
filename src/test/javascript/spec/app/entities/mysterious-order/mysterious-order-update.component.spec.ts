import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DansLeMealTestModule } from '../../../test.module';
import { MysteriousOrderUpdateComponent } from 'app/entities/mysterious-order/mysterious-order-update.component';
import { MysteriousOrderService } from 'app/entities/mysterious-order/mysterious-order.service';
import { MysteriousOrder } from 'app/shared/model/mysterious-order.model';

describe('Component Tests', () => {
  describe('MysteriousOrder Management Update Component', () => {
    let comp: MysteriousOrderUpdateComponent;
    let fixture: ComponentFixture<MysteriousOrderUpdateComponent>;
    let service: MysteriousOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DansLeMealTestModule],
        declarations: [MysteriousOrderUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(MysteriousOrderUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MysteriousOrderUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MysteriousOrderService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new MysteriousOrder(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new MysteriousOrder();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
