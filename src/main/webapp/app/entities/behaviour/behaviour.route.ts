import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Behaviour } from 'app/shared/model/behaviour.model';
import { BehaviourService } from './behaviour.service';
import { BehaviourComponent } from './behaviour.component';
import { BehaviourDetailComponent } from './behaviour-detail.component';
import { BehaviourUpdateComponent } from './behaviour-update.component';
import { BehaviourDeletePopupComponent } from './behaviour-delete-dialog.component';
import { IBehaviour } from 'app/shared/model/behaviour.model';

@Injectable({ providedIn: 'root' })
export class BehaviourResolve implements Resolve<IBehaviour> {
  constructor(private service: BehaviourService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBehaviour> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Behaviour>) => response.ok),
        map((behaviour: HttpResponse<Behaviour>) => behaviour.body)
      );
    }
    return of(new Behaviour());
  }
}

export const behaviourRoute: Routes = [
  {
    path: '',
    component: BehaviourComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.behaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: BehaviourDetailComponent,
    resolve: {
      behaviour: BehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.behaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: BehaviourUpdateComponent,
    resolve: {
      behaviour: BehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.behaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: BehaviourUpdateComponent,
    resolve: {
      behaviour: BehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.behaviour.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const behaviourPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: BehaviourDeletePopupComponent,
    resolve: {
      behaviour: BehaviourResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'gamifierApp.behaviour.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
