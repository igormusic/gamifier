import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GamifierSharedModule } from 'app/shared/shared.module';
import { ActivityComponent } from './activity.component';
import { ActivityDetailComponent } from './activity-detail.component';
import { ActivityUpdateComponent } from './activity-update.component';
import { ActivityDeletePopupComponent, ActivityDeleteDialogComponent } from './activity-delete-dialog.component';
import { activityRoute, activityPopupRoute } from './activity.route';

const ENTITY_STATES = [...activityRoute, ...activityPopupRoute];

@NgModule({
  imports: [GamifierSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ActivityComponent,
    ActivityDetailComponent,
    ActivityUpdateComponent,
    ActivityDeleteDialogComponent,
    ActivityDeletePopupComponent
  ],
  entryComponents: [ActivityComponent, ActivityUpdateComponent, ActivityDeleteDialogComponent, ActivityDeletePopupComponent]
})
export class GamifierActivityModule {}
