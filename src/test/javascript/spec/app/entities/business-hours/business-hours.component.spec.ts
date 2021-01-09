import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DansLeMealTestModule } from '../../../test.module';
import { BusinessHoursComponent } from 'app/entities/business-hours/business-hours.component';
import { BusinessHoursService } from 'app/entities/business-hours/business-hours.service';
import { BusinessHours } from 'app/shared/model/business-hours.model';

describe('Component Tests', () => {
  describe('BusinessHours Management Component', () => {
    let comp: BusinessHoursComponent;
    let fixture: ComponentFixture<BusinessHoursComponent>;
    let service: BusinessHoursService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DansLeMealTestModule],
        declarations: [BusinessHoursComponent],
      })
        .overrideTemplate(BusinessHoursComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BusinessHoursComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BusinessHoursService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new BusinessHours(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.businessHours && comp.businessHours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
