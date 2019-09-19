import { IAchievementBehaviour } from 'app/shared/model/achievement-behaviour.model';
import { IActivity } from 'app/shared/model/activity.model';

export interface IBehaviour {
  id?: number;
  name?: string;
  points?: number;
  achievements?: IAchievementBehaviour[];
  activities?: IActivity[];
}

export class Behaviour implements IBehaviour {
  constructor(
    public id?: number,
    public name?: string,
    public points?: number,
    public achievements?: IAchievementBehaviour[],
    public activities?: IActivity[]
  ) {}
}
