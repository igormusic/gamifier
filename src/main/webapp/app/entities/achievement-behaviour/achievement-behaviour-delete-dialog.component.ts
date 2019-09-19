import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';
import { AchievementBehaviourService } from './achievement-behaviour.service';

@Component({
  selector: 'jhi-achievement-behaviour-delete-dialog',
  templateUrl: './achievement-behaviour-delete-dialog.component.html'
})
export class AchievementBehaviourDeleteDialogComponent {
  achievementBehaviour: IAchievementBehaviour;

  constructor(
    protected achievementBehaviourService: AchievementBehaviourService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.achievementBehaviourService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'achievementBehaviourListModification',
        content: 'Deleted an achievementBehaviour'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-achievement-behaviour-delete-popup',
  template: ''
})
export class AchievementBehaviourDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ achievementBehaviour }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AchievementBehaviourDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.achievementBehaviour = achievementBehaviour;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/achievement-behaviour', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/achievement-behaviour', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
