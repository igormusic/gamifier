import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { GamifierTestModule } from '../../../test.module';
import { BehaviourDeleteDialogComponent } from 'app/entities/behaviour/behaviour-delete-dialog.component';
import { BehaviourService } from 'app/entities/behaviour/behaviour.service';

describe('Component Tests', () => {
  describe('Behaviour Management Delete Component', () => {
    let comp: BehaviourDeleteDialogComponent;
    let fixture: ComponentFixture<BehaviourDeleteDialogComponent>;
    let service: BehaviourService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [GamifierTestModule],
        declarations: [BehaviourDeleteDialogComponent]
      })
        .overrideTemplate(BehaviourDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(BehaviourDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(BehaviourService);
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
