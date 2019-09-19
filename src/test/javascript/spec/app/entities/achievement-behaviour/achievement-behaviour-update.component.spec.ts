import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GamifierTestModule } from '../../../test.module';
import { AchievementBehaviourUpdateComponent } from 'app/entities/achievement-behaviour/achievement-behaviour-update.component';
import { AchievementBehaviourService } from 'app/entities/achievement-behaviour/achievement-behaviour.service';
import { AchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

describe('Component Tests', () => {
  describe('AchievementBehaviour Management Update Component', () => {
    let comp: AchievementBehaviourUpdateComponent;
    let fixture: ComponentFixture<AchievementBehaviourUpdateComponent>;
    let service: AchievementBehaviourService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementBehaviourUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AchievementBehaviourUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AchievementBehaviourUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementBehaviourService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AchievementBehaviour(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new AchievementBehaviour();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
