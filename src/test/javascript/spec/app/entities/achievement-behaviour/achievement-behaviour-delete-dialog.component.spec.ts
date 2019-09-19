import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GamifierTestModule } from '../../../test.module';
import { AchievementBehaviourDeleteDialogComponent } from 'app/entities/achievement-behaviour/achievement-behaviour-delete-dialog.component';
import { AchievementBehaviourService } from 'app/entities/achievement-behaviour/achievement-behaviour.service';

describe('Component Tests', () => {
  describe('AchievementBehaviour Management Delete Component', () => {
    let comp: AchievementBehaviourDeleteDialogComponent;
    let fixture: ComponentFixture<AchievementBehaviourDeleteDialogComponent>;
    let service: AchievementBehaviourService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementBehaviourDeleteDialogComponent]
      })
        .overrideTemplate(AchievementBehaviourDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AchievementBehaviourDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementBehaviourService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
