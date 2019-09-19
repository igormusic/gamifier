import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamifierSharedModule } from 'app/shared/shared.module';
import { BehaviourComponent } from './behaviour.component';
import { BehaviourDetailComponent } from './behaviour-detail.component';
import { BehaviourUpdateComponent } from './behaviour-update.component';
import { BehaviourDeletePopupComponent, BehaviourDeleteDialogComponent } from './behaviour-delete-dialog.component';
import { behaviourRoute, behaviourPopupRoute } from './behaviour.route';

const ENTITY_STATES = [...behaviourRoute, ...behaviourPopupRoute];

@NgModule({
  imports: [GamifierSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    BehaviourComponent,
    BehaviourDetailComponent,
    BehaviourUpdateComponent,
    BehaviourDeleteDialogComponent,
    BehaviourDeletePopupComponent
  ],
  entryComponents: [BehaviourComponent, BehaviourUpdateComponent, BehaviourDeleteDialogComponent, BehaviourDeletePopupComponent]
})
export class GamifierBehaviourModule {}
