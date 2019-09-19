import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';
import { AccountService } from 'app/core/auth/account.service';
import { AchievementProgressService } from './achievement-progress.service';

@Component({
  selector: 'jhi-achievement-progress',
  templateUrl: './achievement-progress.component.html'
})
export class AchievementProgressComponent implements OnInit, OnDestroy {
  achievementProgresses: IAchievementProgress[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected achievementProgressService: AchievementProgressService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.achievementProgressService
      .query()
      .pipe(
        filter((res: HttpResponse<IAchievementProgress[]>) => res.ok),
        map((res: HttpResponse<IAchievementProgress[]>) => res.body)
      )
      .subscribe(
        (res: IAchievementProgress[]) => {
          this.achievementProgresses = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAchievementProgresses();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAchievementProgress) {
    return item.id;
  }

  registerChangeInAchievementProgresses() {
    this.eventSubscriber = this.eventManager.subscribe('achievementProgressListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
