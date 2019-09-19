import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GamifierTestModule } from '../../../test.module';
import { BehaviourDetailComponent } from 'app/entities/behaviour/behaviour-detail.component';
import { Behaviour } from 'app/shared/model/behaviour.model';

describe('Component Tests', () => {
  describe('Behaviour Management Detail Component', () => {
    let comp: BehaviourDetailComponent;
    let fixture: ComponentFixture<BehaviourDetailComponent>;
    const route = ({ data: of({ behaviour: new Behaviour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [BehaviourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(BehaviourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BehaviourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.behaviour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
