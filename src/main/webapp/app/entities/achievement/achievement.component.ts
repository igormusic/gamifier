import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAchievement } from 'app/shared/model/achievement.model';
import { AccountService } from 'app/core/auth/account.service';
import { AchievementService } from './achievement.service';

@Component({
  selector: 'jhi-achievement',
  templateUrl: './achievement.component.html'
})
export class AchievementComponent implements OnInit, OnDestroy {
  achievements: IAchievement[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected achievementService: AchievementService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.achievementService
      .query()
      .pipe(
        filter((res: HttpResponse<IAchievement[]>) => res.ok),
        map((res: HttpResponse<IAchievement[]>) => res.body)
      )
      .subscribe(
        (res: IAchievement[]) => {
          this.achievements = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInAchievements();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IAchievement) {
    return item.id;
  }

  registerChangeInAchievements() {
    this.eventSubscriber = this.eventManager.subscribe('achievementListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
