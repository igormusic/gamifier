import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';
import { AchievementProgressService } from './achievement-progress.service';

@Component({
  selector: 'jhi-achievement-progress-delete-dialog',
  templateUrl: './achievement-progress-delete-dialog.component.html'
})
export class AchievementProgressDeleteDialogComponent {
  achievementProgress: IAchievementProgress;

  constructor(
    protected achievementProgressService: AchievementProgressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.achievementProgressService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'achievementProgressListModification',
        content: 'Deleted an achievementProgress'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-achievement-progress-delete-popup',
  template: ''
})
export class AchievementProgressDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ achievementProgress }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(AchievementProgressDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.achievementProgress = achievementProgress;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/achievement-progress', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/achievement-progress', { outlets: { popup: null } }]);
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
