import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamifierSharedModule } from 'app/shared/shared.module';
import { AchievementComponent } from './achievement.component';
import { AchievementDetailComponent } from './achievement-detail.component';
import { AchievementUpdateComponent } from './achievement-update.component';
import { AchievementDeletePopupComponent, AchievementDeleteDialogComponent } from './achievement-delete-dialog.component';
import { achievementRoute, achievementPopupRoute } from './achievement.route';

const ENTITY_STATES = [...achievementRoute, ...achievementPopupRoute];

@NgModule({
  imports: [GamifierSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    AchievementComponent,
    AchievementDetailComponent,
    AchievementUpdateComponent,
    AchievementDeleteDialogComponent,
    AchievementDeletePopupComponent
  ],
  entryComponents: [AchievementComponent, AchievementUpdateComponent, AchievementDeleteDialogComponent, AchievementDeletePopupComponent]
})
export class GamifierAchievementModule {}
