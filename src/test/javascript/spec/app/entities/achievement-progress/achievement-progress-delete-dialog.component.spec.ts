import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GamifierTestModule } from '../../../test.module';
import { AchievementProgressDeleteDialogComponent } from 'app/entities/achievement-progress/achievement-progress-delete-dialog.component';
import { AchievementProgressService } from 'app/entities/achievement-progress/achievement-progress.service';

describe('Component Tests', () => {
  describe('AchievementProgress Management Delete Component', () => {
    let comp: AchievementProgressDeleteDialogComponent;
    let fixture: ComponentFixture<AchievementProgressDeleteDialogComponent>;
    let service: AchievementProgressService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [AchievementProgressDeleteDialogComponent]
      })
        .overrideTemplate(AchievementProgressDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AchievementProgressDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AchievementProgressService);
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
