import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { GamifierTestModule } from '../../../test.module';
import { AchievementProgressComponent } from 'app/entities/achievement-progress/achievement-progress.component';
import { AchievementProgressService } from 'app/entities/achievement-progress/achievement-progress.service';
import { AchievementProgress } from 'app/shared/model/achievement-progress.model';

describe('Component Tests', () => {
  describe('AchievementProgress Management Component', () => {
    let comp: AchievementProgressComponent;
    let fixture: ComponentFixture<AchievementProgressComponent>;
    let service: AchievementProgressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementProgressComponent],
        providers: []
      })
        .overrideTemplate(AchievementProgressComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AchievementProgressComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementProgressService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AchievementProgress(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.achievementProgresses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
