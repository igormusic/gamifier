import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';
import { AchievementBehaviourService } from './achievement-behaviour.service';
import { AchievementBehaviourComponent } from './achievement-behaviour.component';
import { AchievementBehaviourDetailComponent } from './achievement-behaviour-detail.component';
import { AchievementBehaviourUpdateComponent } from './achievement-behaviour-update.component';
import { AchievementBehaviourDeletePopupComponent } from './achievement-behaviour-delete-dialog.component';
import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

@Injectable({ providedIn: 'root' })
export class AchievementBehaviourResolve implements Resolve<IAchievementBehaviour> {
  constructor(private service: AchievementBehaviourService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAchievementBehaviour> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<AchievementBehaviour>) => response.ok),
        map((achievementBehaviour: HttpResponse<AchievementBehaviour>) => achievementBehaviour.body)
      );
    }
    return of(new AchievementBehaviour());
  }
}

export const achievementBehaviourRoute: Routes = [
  {
    path: '',
    component: AchievementBehaviourComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementBehaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AchievementBehaviourDetailComponent,
    resolve: {
      achievementBehaviour: AchievementBehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementBehaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AchievementBehaviourUpdateComponent,
    resolve: {
      achievementBehaviour: AchievementBehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementBehaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AchievementBehaviourUpdateComponent,
    resolve: {
      achievementBehaviour: AchievementBehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementBehaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const achievementBehaviourPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AchievementBehaviourDeletePopupComponent,
    resolve: {
      achievementBehaviour: AchievementBehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.achievementBehaviour.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
