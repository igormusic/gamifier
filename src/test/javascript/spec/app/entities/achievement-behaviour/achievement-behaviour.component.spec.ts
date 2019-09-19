import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GamifierTestModule } from '../../../test.module';
import { AchievementBehaviourComponent } from 'app/entities/achievement-behaviour/achievement-behaviour.component';
import { AchievementBehaviourService } from 'app/entities/achievement-behaviour/achievement-behaviour.service';
import { AchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

describe('Component Tests', () => {
  describe('AchievementBehaviour Management Component', () => {
    let comp: AchievementBehaviourComponent;
    let fixture: ComponentFixture<AchievementBehaviourComponent>;
    let service: AchievementBehaviourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementBehaviourComponent],
        providers: []
      })
        .overrideTemplate(AchievementBehaviourComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AchievementBehaviourComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementBehaviourService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AchievementBehaviour(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.achievementBehaviours[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
