import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AchievementProgress } from 'app/shared/model/achievement-progress.model';
import { AchievementProgressService } from './achievement-progress.service';
import { AchievementProgressComponent } from './achievement-progress.component';
import { AchievementProgressDetailComponent } from './achievement-progress-detail.component';
import { AchievementProgressUpdateComponent } from './achievement-progress-update.component';
import { AchievementProgressDeletePopupComponent } from './achievement-progress-delete-dialog.component';
import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';

@Injectable({ providedIn: 'root' })
export class AchievementProgressResolve implements Resolve<IAchievementProgress> {
  constructor(private service: AchievementProgressService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAchievementProgress> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AchievementProgress>) => response.ok),
        map((achievementProgress: HttpResponse<AchievementProgress>) => achievementProgress.body)
      );
    }
    return of(new AchievementProgress());
  }
}

export const achievementProgressRoute: Routes = [
  {
    path: '',
    component: AchievementProgressComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementProgress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AchievementProgressDetailComponent,
    resolve: {
      achievementProgress: AchievementProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementProgress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AchievementProgressUpdateComponent,
    resolve: {
      achievementProgress: AchievementProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementProgress.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AchievementProgressUpdateComponent,
    resolve: {
      achievementProgress: AchievementProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementProgress.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const achievementProgressPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AchievementProgressDeletePopupComponent,
    resolve: {
      achievementProgress: AchievementProgressResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementProgress.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
