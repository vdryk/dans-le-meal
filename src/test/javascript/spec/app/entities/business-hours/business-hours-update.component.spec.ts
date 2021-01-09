import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { DansLeMealTestModule } from '../../../test.module';
import { BusinessHoursUpdateComponent } from 'app/entities/business-hours/business-hours-update.component';
import { BusinessHoursService } from 'app/entities/business-hours/business-hours.service';
import { BusinessHours } from 'app/shared/model/business-hours.model';

describe('Component Tests', () => {
  describe('BusinessHours Management Update Component', () => {
    let comp: BusinessHoursUpdateComponent;
    let fixture: ComponentFixture<BusinessHoursUpdateComponent>;
    let service: BusinessHoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DansLeMealTestModule],
        declarations: [BusinessHoursUpdateComponent],
        providers: [FormBuilder],
      })
        .overrideTemplate(BusinessHoursUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessHoursUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessHoursService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new BusinessHours(123);
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
        const entity = new BusinessHours();
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
