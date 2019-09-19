import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBehaviour } from 'app/shared/model/behaviour.model';
import { BehaviourService } from './behaviour.service';

@Component({
  selector: 'jhi-behaviour-delete-dialog',
  templateUrl: './behaviour-delete-dialog.component.html'
})
export class BehaviourDeleteDialogComponent {
  behaviour: IBehaviour;

  constructor(protected behaviourService: BehaviourService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.behaviourService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'behaviourListModification',
        content: 'Deleted an behaviour'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-behaviour-delete-popup',
  template: ''
})
export class BehaviourDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ behaviour }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(BehaviourDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.behaviour = behaviour;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/behaviour', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/behaviour', { outlets: { popup: null } }]);
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
