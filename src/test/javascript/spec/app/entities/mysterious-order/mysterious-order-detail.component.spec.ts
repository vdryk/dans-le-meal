import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DansLeMealTestModule } from '../../../test.module';
import { MysteriousOrderDetailComponent } from 'app/entities/mysterious-order/mysterious-order-detail.component';
import { MysteriousOrder } from 'app/shared/model/mysterious-order.model';

describe('Component Tests', () => {
  describe('MysteriousOrder Management Detail Component', () => {
    let comp: MysteriousOrderDetailComponent;
    let fixture: ComponentFixture<MysteriousOrderDetailComponent>;
    const route = ({ data: of({ mysteriousOrder: new MysteriousOrder(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [DansLeMealTestModule],
        declarations: [MysteriousOrderDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(MysteriousOrderDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(MysteriousOrderDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load mysteriousOrder on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.mysteriousOrder).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
