import { IActivity } from 'app/shared/model/activity.model';

export interface IEmployee {
  id?: number;
  name?: string;
  email?: string;
  externalReference?: string;
  activities?: IActivity[];
  teamName?: string;
  teamId?: number;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public name?: string,
    public email?: string,
    public externalReference?: string,
    public activities?: IActivity[],
    public teamName?: string,
    public teamId?: number
  ) {}
}
