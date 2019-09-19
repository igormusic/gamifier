import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GamifierTestModule } from '../../../test.module';
import { BehaviourComponent } from 'app/entities/behaviour/behaviour.component';
import { BehaviourService } from 'app/entities/behaviour/behaviour.service';
import { Behaviour } from 'app/shared/model/behaviour.model';

describe('Component Tests', () => {
  describe('Behaviour Management Component', () => {
    let comp: BehaviourComponent;
    let fixture: ComponentFixture<BehaviourComponent>;
    let service: BehaviourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [BehaviourComponent],
        providers: []
      })
        .overrideTemplate(BehaviourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(BehaviourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BehaviourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Behaviour(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.behaviours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
