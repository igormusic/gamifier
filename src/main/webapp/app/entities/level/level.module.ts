import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamifierSharedModule } from 'app/shared/shared.module';
import { LevelComponent } from './level.component';
import { LevelDetailComponent } from './level-detail.component';
import { LevelUpdateComponent } from './level-update.component';
import { LevelDeletePopupComponent, LevelDeleteDialogComponent } from './level-delete-dialog.component';
import { levelRoute, levelPopupRoute } from './level.route';

const ENTITY_STATES = [...levelRoute, ...levelPopupRoute];

@NgModule({
  imports: [GamifierSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [LevelComponent, LevelDetailComponent, LevelUpdateComponent, LevelDeleteDialogComponent, LevelDeletePopupComponent],
  entryComponents: [LevelComponent, LevelUpdateComponent, LevelDeleteDialogComponent, LevelDeletePopupComponent]
})
export class GamifierLevelModule {}
