import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamifierSharedModule } from 'app/shared/shared.module';
import { AchievementBehaviourComponent } from './achievement-behaviour.component';
import { AchievementBehaviourDetailComponent } from './achievement-behaviour-detail.component';
import { AchievementBehaviourUpdateComponent } from './achievement-behaviour-update.component';
import {
  AchievementBehaviourDeletePopupComponent,
  AchievementBehaviourDeleteDialogComponent
} from './achievement-behaviour-delete-dialog.component';
import { achievementBehaviourRoute, achievementBehaviourPopupRoute } from './achievement-behaviour.route';

const ENTITY_STATES = [...achievementBehaviourRoute, ...achievementBehaviourPopupRoute];

@NgModule({
  imports: [GamifierSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AchievementBehaviourComponent,
    AchievementBehaviourDetailComponent,
    AchievementBehaviourUpdateComponent,
    AchievementBehaviourDeleteDialogComponent,
    AchievementBehaviourDeletePopupComponent
  ],
  entryComponents: [
    AchievementBehaviourComponent,
    AchievementBehaviourUpdateComponent,
    AchievementBehaviourDeleteDialogComponent,
    AchievementBehaviourDeletePopupComponent
  ]
})
export class GamifierAchievementBehaviourModule {}
