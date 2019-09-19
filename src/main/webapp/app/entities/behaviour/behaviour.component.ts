import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBehaviour } from 'app/shared/model/behaviour.model';
import { AccountService } from 'app/core/auth/account.service';
import { BehaviourService } from './behaviour.service';

@Component({
  selector: 'jhi-behaviour',
  templateUrl: './behaviour.component.html'
})
export class BehaviourComponent implements OnInit, OnDestroy {
  behaviours: IBehaviour[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected behaviourService: BehaviourService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.behaviourService
      .query()
      .pipe(
        filter((res: HttpResponse<IBehaviour[]>) => res.ok),
        map((res: HttpResponse<IBehaviour[]>) => res.body)
      )
      .subscribe(
        (res: IBehaviour[]) => {
          this.behaviours = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInBehaviours();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IBehaviour) {
    return item.id;
  }

  registerChangeInBehaviours() {
    this.eventSubscriber = this.eventManager.subscribe('behaviourListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
