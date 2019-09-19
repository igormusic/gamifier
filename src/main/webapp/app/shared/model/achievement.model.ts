import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';

export interface IAchievement {
  id?: number;
  name?: string;
  behaviours?: IAchievementBehaviour[];
}

export class Achievement implements IAchievement {
  constructor(public id?: number, public name?: string, public behaviours?: IAchievementBehaviour[]) {}
}
