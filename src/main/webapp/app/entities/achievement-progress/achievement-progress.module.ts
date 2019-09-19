import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamifierSharedModule } from 'app/shared/shared.module';
import { AchievementProgressComponent } from './achievement-progress.component';
import { AchievementProgressDetailComponent } from './achievement-progress-detail.component';
import { AchievementProgressUpdateComponent } from './achievement-progress-update.component';
import {
  AchievementProgressDeletePopupComponent,
  AchievementProgressDeleteDialogComponent
} from './achievement-progress-delete-dialog.component';
import { achievementProgressRoute, achievementProgressPopupRoute } from './achievement-progress.route';

const ENTITY_STATES = [...achievementProgressRoute, ...achievementProgressPopupRoute];

@NgModule({
  imports: [GamifierSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AchievementProgressComponent,
    AchievementProgressDetailComponent,
    AchievementProgressUpdateComponent,
    AchievementProgressDeleteDialogComponent,
    AchievementProgressDeletePopupComponent
  ],
  entryComponents: [
    AchievementProgressComponent,
    AchievementProgressUpdateComponent,
    AchievementProgressDeleteDialogComponent,
    AchievementProgressDeletePopupComponent
  ]
})
export class GamifierAchievementProgressModule {}
