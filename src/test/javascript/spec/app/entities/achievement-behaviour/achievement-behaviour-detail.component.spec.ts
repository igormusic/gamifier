import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { GamifierTestModule } from '../../../test.module';
import { AchievementBehaviourDetailComponent } from 'app/entities/achievement-behaviour/achievement-behaviour-detail.component';
import { AchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

describe('Component Tests', () => {
  describe('AchievementBehaviour Management Detail Component', () => {
    let comp: AchievementBehaviourDetailComponent;
    let fixture: ComponentFixture<AchievementBehaviourDetailComponent>;
    const route = ({ data: of({ achievementBehaviour: new AchievementBehaviour(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementBehaviourDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(AchievementBehaviourDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AchievementBehaviourDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.achievementBehaviour).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
