import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DansLeMealTestModule } from '../../../test.module';
import { BusinessHoursDetailComponent } from 'app/entities/business-hours/business-hours-detail.component';
import { BusinessHours } from 'app/shared/model/business-hours.model';

describe('Component Tests', () => {
  describe('BusinessHours Management Detail Component', () => {
    let comp: BusinessHoursDetailComponent;
    let fixture: ComponentFixture<BusinessHoursDetailComponent>;
    const route = ({ data: of({ businessHours: new BusinessHours(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DansLeMealTestModule],
        declarations: [BusinessHoursDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(BusinessHoursDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BusinessHoursDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load businessHours on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.businessHours).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
