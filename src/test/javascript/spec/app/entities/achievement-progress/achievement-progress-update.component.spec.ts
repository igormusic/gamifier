import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { GamifierTestModule } from '../../../test.module';
import { AchievementProgressUpdateComponent } from 'app/entities/achievement-progress/achievement-progress-update.component';
import { AchievementProgressService } from 'app/entities/achievement-progress/achievement-progress.service';
import { AchievementProgress } from 'app/shared/model/achievement-progress.model';

describe('Component Tests', () => {
  describe('AchievementProgress Management Update Component', () => {
    let comp: AchievementProgressUpdateComponent;
    let fixture: ComponentFixture<AchievementProgressUpdateComponent>;
    let service: AchievementProgressService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementProgressUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AchievementProgressUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AchievementProgressUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementProgressService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new AchievementProgress(123);
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
        const entity = new AchievementProgress();
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
