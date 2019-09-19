import { Moment } from 'moment';
import { IAchievementProgress } from 'app/shared/model/achievement-progress.model';

export interface IActivity {
  id?: number;
  dateTime?: Moment;
  behaviourName?: string;
  behaviourId?: number;
  employeeName?: string;
  employeeId?: number;
  achievementProgresses?: IAchievementProgress[];
}

export class Activity implements IActivity {
  constructor(
    public id?: number,
    public dateTime?: Moment,
    public behaviourName?: string,
    public behaviourId?: number,
    public employeeName?: string,
    public employeeId?: number,
    public achievementProgresses?: IAchievementProgress[]
  ) {}
}
