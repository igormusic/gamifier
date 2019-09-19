import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GamifierTestModule } from '../../../test.module';
import { AchievementProgressDetailComponent } from 'app/entities/achievement-progress/achievement-progress-detail.component';
import { AchievementProgress } from 'app/shared/model/achievement-progress.model';

describe('Component Tests', () => {
  describe('AchievementProgress Management Detail Component', () => {
    let comp: AchievementProgressDetailComponent;
    let fixture: ComponentFixture<AchievementProgressDetailComponent>;
    const route = ({ data: of({ achievementProgress: new AchievementProgress(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementProgressDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AchievementProgressDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AchievementProgressDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.achievementProgress).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
