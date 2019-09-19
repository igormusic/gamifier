import { IActivity } from 'app/shared/model/activity.model';
import { AchievementStatus } from 'app/shared/model/enumerations/achievement-status.model';

export interface IAchievementProgress {
  id?: number;
  status?: AchievementStatus;
  activities?: IActivity[];
}

export class AchievementProgress implements IAchievementProgress {
  constructor(public id?: number, public status?: AchievementStatus, public activities?: IActivity[]) {}
}
