import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { DansLeMealTestModule } from '../../../test.module';
import { MysteriousOrderComponent } from 'app/entities/mysterious-order/mysterious-order.component';
import { MysteriousOrderService } from 'app/entities/mysterious-order/mysterious-order.service';
import { MysteriousOrder } from 'app/shared/model/mysterious-order.model';

describe('Component Tests', () => {
  describe('MysteriousOrder Management Component', () => {
    let comp: MysteriousOrderComponent;
    let fixture: ComponentFixture<MysteriousOrderComponent>;
    let service: MysteriousOrderService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DansLeMealTestModule],
        declarations: [MysteriousOrderComponent],
      })
        .overrideTemplate(MysteriousOrderComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(MysteriousOrderComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(MysteriousOrderService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new MysteriousOrder(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.mysteriousOrders && comp.mysteriousOrders[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
