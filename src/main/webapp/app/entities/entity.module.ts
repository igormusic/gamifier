import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'employee',
        loadChildren: () => import('./employee/employee.module').then(m => m.GamifierEmployeeModule)
      },
      {
        path: 'team',
        loadChildren: () => import('./team/team.module').then(m => m.GamifierTeamModule)
      },
      {
        path: 'behaviour',
        loadChildren: () => import('./behaviour/behaviour.module').then(m => m.GamifierBehaviourModule)
      },
      {
        path: 'level',
        loadChildren: () => import('./level/level.module').then(m => m.GamifierLevelModule)
      },
      {
        path: 'achievement',
        loadChildren: () => import('./achievement/achievement.module').then(m => m.GamifierAchievementModule)
      },
      {
        path: 'achievement-behaviour',
        loadChildren: () => import('./achievement-behaviour/achievement-behaviour.module').then(m => m.GamifierAchievementBehaviourModule)
      },
      {
        path: 'activity',
        loadChildren: () => import('./activity/activity.module').then(m => m.GamifierActivityModule)
      },
      {
        path: 'achievement-progress',
        loadChildren: () => import('./achievement-progress/achievement-progress.module').then(m => m.GamifierAchievementProgressModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: []
})
export class GamifierEntityModule {}
