import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ILevel } from 'app/shared/model/level.model';
import { AccountService } from 'app/core/auth/account.service';
import { LevelService } from './level.service';

@Component({
  selector: 'jhi-level',
  templateUrl: './level.component.html'
})
export class LevelComponent implements OnInit, OnDestroy {
  levels: ILevel[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected levelService: LevelService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.levelService
      .query()
      .pipe(
        filter((res: HttpResponse<ILevel[]>) => res.ok),
        map((res: HttpResponse<ILevel[]>) => res.body)
      )
      .subscribe(
        (res: ILevel[]) => {
          this.levels = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInLevels();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: ILevel) {
    return item.id;
  }

  registerChangeInLevels() {
    this.eventSubscriber = this.eventManager.subscribe('levelListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
