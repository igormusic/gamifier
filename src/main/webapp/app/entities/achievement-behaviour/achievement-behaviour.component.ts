import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';
import { AccountService } from 'app/core/auth/account.service';
import { AchievementBehaviourService } from './achievement-behaviour.service';

@Component({
  selector: 'jhi-achievement-behaviour',
  templateUrl: './achievement-behaviour.component.html'
})
export class AchievementBehaviourComponent implements OnInit, OnDestroy {
  achievementBehaviours: IAchievementBehaviour[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected achievementBehaviourService: AchievementBehaviourService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.achievementBehaviourService
      .query()
      .pipe(
        filter((res: HttpResponse<IAchievementBehaviour[]>) => res.ok),
        map((res: HttpResponse<IAchievementBehaviour[]>) => res.body)
      )
      .subscribe(
        (res: IAchievementBehaviour[]) => {
          this.achievementBehaviours = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAchievementBehaviours();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAchievementBehaviour) {
    return item.id;
  }

  registerChangeInAchievementBehaviours() {
    this.eventSubscriber = this.eventManager.subscribe('achievementBehaviourListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
